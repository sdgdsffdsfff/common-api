package org.rency.common.sequence.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.MapUtils;
import org.rency.common.sequence.beans.Sequence;
import org.rency.common.sequence.dao.SequenceDao;
import org.rency.common.sequence.service.SequenceRepository;
import org.rency.common.utils.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class SequenceRepositoryImpl implements SequenceRepository,InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(SequenceRepositoryImpl.class);
	
	@Autowired
	@Qualifier("sequenceDao")
	private SequenceDao sequenceDao;
	
	/**
     * 事务模板
     */
	@Autowired
	@Qualifier("sequenceTransactionTemplate")
    private TransactionTemplate transactionTemplate;
    
    /**
	 * 异步刷新线程池
	 */
	@Autowired
	@Qualifier("sequenceFlushTreadPoolTaskExecutor")
	private ThreadPoolTaskExecutor ThreadPoolTaskExecutor;
	
	/**
	 * 缓存队列
	 */
	private ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> sequenceQueue = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>>();
	
	/**
	 * 刷新锁
	 */
	private ConcurrentHashMap<String, ReentrantLock> locks = new ConcurrentHashMap<String, ReentrantLock>();
	
	/**
	 * 计数器
	 */
	private ConcurrentHashMap<String, AtomicInteger> counts = new ConcurrentHashMap<String, AtomicInteger>();
	
	/**
	 * 阀值
	 */
	private Map<String, Integer> thresholds = new HashMap<String, Integer>();
	
	/**
	 * 总数
	 */
	private Map<String, Integer> totals = new HashMap<String, Integer>();

	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("init sequence generator ...");
		sequenceDao.createTable();
		if(MapUtils.isEmpty(sequenceQueue)){
			List<Sequence> sequenceList = sequenceDao.loadAll();
			for(Sequence seq : sequenceList){
				thresholds.put(seq.getName(), seq.getThreshold());
				totals.put(seq.getName(), seq.getTotal());
				//初始化锁队列
				locks.put(seq.getName(), new ReentrantLock());
				//更新缓冲区
				flushBuffer(seq.getName());
			}
		}
		logger.info("init sequence generator finish ...");
	}

	/**
	 * 刷新缓冲区
	 * @param sequenceName
	 */
	private void flushBuffer(final String sequenceName) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Sequence seq = sequenceDao.lock(sequenceName);
				//计算偏移量
				Integer offset = seq.getIncrement() * totals.get(sequenceName);
				//计算更新值
				Long beforeValue = seq.getCurrentValue();
				Long afterValue = beforeValue + offset;
				//更新
				try{
					sequenceDao.update(sequenceName, beforeValue, afterValue);
				}catch(DataAccessException e){
					logger.error("刷新缓冲区更新序列号时出错，sequenceName:{},beforeValue:{},afterValue:{}",sequenceName,beforeValue,afterValue,e);
					throw e;
				}
				seq.setAfterValue(afterValue);
				//清空数据，初始化
				sequenceQueue.put(sequenceName, new ConcurrentLinkedQueue<Long>());
				
				//设值
				for(long seqValue = beforeValue;seqValue < afterValue;seqValue = seqValue + seq.getIncrement().longValue()){
					sequenceQueue.get(sequenceName).add(seqValue);
				}
				//设置计数器
				counts.put(sequenceName, new AtomicInteger(0));
			}
		});
	}

	@Override
	public void flush(String sequenceName) {
		//检查阀值
		if(overThreshold(sequenceName)){
			//尝试获取锁
			//若返回false则已被锁定，当前线程不做操作;如果返回true则可以拿到当前线程锁
			if(locks.get(sequenceName).tryLock()){
				try{
					//锁定后再次检测能否获取锁
					if(overThreshold(sequenceName)){
						try{
							flushBuffer(sequenceName);
						}catch(DataAccessException e){
							//更新异常则通过线程池异步更新
							asyncFlush(sequenceName);
						}
					}
				}finally{
					//释放锁
					locks.get(sequenceName).unlock();
				}
			}
		}
	}

	/**
	 * 获取序列号
	 */
	@Override
	public Long next(String sequenceName) {
		Long sequence = sequenceQueue.get(sequenceName).poll();
		if(sequence != null){
			//计数增长
			counts.get(sequenceName).incrementAndGet();
			//若达到刷新阀值,异步刷新
			if(overThreshold(sequenceName)){
				asyncFlush(sequenceName);
			}
			return sequence;
		}
		//若队列取空
		try{
			//立即刷新
			flush(sequenceName);
			//重新抓取
			return next(sequenceName);
		}catch(Exception e){
			logger.error("获取序列号失败{}",sequenceName,e);
			return next(sequenceName);
		}
	}

	/**
	 * 是否越过刷新阀值
	 * @param sequenceName
	 * @return
	 */
	private boolean overThreshold(String sequenceName) {
		return counts.get(sequenceName).intValue() >= totals.get(sequenceName) - thresholds.get(sequenceName);
	}
	
	/**
	 * 异步刷新
	 * @param sequenceName
	 */
	private void asyncFlush(final String sequenceName) {
		try{
			ThreadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					flush(sequenceName);
				}
			});
		}catch(TaskRejectedException e){
			
		}
	}
	
	public boolean save(Sequence sequence) throws DBException{
		return sequenceDao.save(sequence) > 1;
	}
	
	public boolean delete(String sequenceName) throws DBException{
		return sequenceDao.delete(sequenceName) > 1;
	}

	@Override
	public void createTable(String tableName,String createSQL) throws DBException {
		sequenceDao.createTable(tableName,createSQL);
	}
	
	public Sequence find(String sequenceName) throws DBException{
		return sequenceDao.find(sequenceName);
	}

}
