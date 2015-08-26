package org.rency.common.sequence.service;

import java.sql.SQLException;

import org.rency.common.sequence.beans.Sequence;
import org.rency.common.utils.exception.DBException;

/**
 * 生产序列号接口
 * @author rencaiyu
 *
 */
public interface SequenceRepository {


    /**
     * 刷新指定序列队列
     * @param sequenceName
     */
    public void flush(String sequenceName);

    /**
     * 获取序列值
     * @param sequenceName
     * @return
     */
    public Long next(String sequenceName);
    
    public boolean save(Sequence sequence) throws DBException;
	
	public boolean delete(String sequenceName) throws DBException;
	
	/**
	 * 创建数据表
	 * @param tableName 表名
	 * @param createSQL 创建脚本
	 * @throws SQLException
	 */
	public void createTable(String tableName,String createSQL) throws DBException;
	
	public Sequence find(String sequenceName) throws DBException;
	
}