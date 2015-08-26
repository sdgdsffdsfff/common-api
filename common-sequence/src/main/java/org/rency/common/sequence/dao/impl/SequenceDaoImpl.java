package org.rency.common.sequence.dao.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.rency.common.sequence.beans.Sequence;
import org.rency.common.sequence.dao.SequenceDao;
import org.rency.common.utils.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("sequenceDao")
public class SequenceDaoImpl extends JdbcDaoSupport implements SequenceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SequenceDaoImpl.class);

	private RowMapper<Sequence> rowMapper = new RowMapper<Sequence>() {
		@Override
		public Sequence mapRow(ResultSet rs, int param) throws SQLException {
			Sequence seq = new Sequence();
			seq.setName(rs.getString("NAME"));
			seq.setCurrentValue(rs.getLong("CURRENT_VALUE"));
			seq.setIncrement(rs.getInt("INCREMENT"));
			seq.setTotal(rs.getInt("TOTAL"));
			seq.setThreshold(rs.getInt("THRESHOLD"));
			return seq;
		}
	};
	
	private static final String TABLE_NAME_SEQUENCE = "T_SEQUENCE";
	
	private static final String CREATE_SQL_MYSQL = "create table T_SEQUENCE(NAME varchar(50) NOT NULL COMMENT '名称',CURRENT_VALUE bigint(20) DEFAULT NULL,INCREMENT smallint(6) NOT NULL DEFAULT '1' COMMENT '增量',TOTAL smallint(6) NOT NULL DEFAULT '10000' COMMENT '单次取值总量，更新总量需重启应用',THRESHOLD smallint(6) NOT NULL DEFAULT '10000' COMMENT '刷新阀值，更新阀值需重启应用',PRIMARY KEY (NAME))COMMENT='序列'";
	
	private static final String INSERT_SQL = "insert into T_SEQUENCE(NAME,CURRENT_VALUE,INCREMENT,TOTAL,THRESHOLD) value(?,?,?,?,?) ";
	
	private static final String DELETE_SQL = "delete from T_SEQUENCE where NAME=?";
	
    private static final String QUERY_ALL = " select * from T_SEQUENCE ";

    private static final String LOCK_BY   = " select * from T_SEQUENCE where NAME = ? for update ";
    
    private static final String FIND_BY   = " select * from T_SEQUENCE where NAME = ?";

    private static final String UPDATE    = " update T_SEQUENCE set CURRENT_VALUE = ? where CURRENT_VALUE = ? and NAME = ? ";
	
    @Override
	public void createTable() throws DBException {
		try {
			DatabaseMetaData metaData = super.getDataSource().getConnection().getMetaData();
			String proName = metaData.getDatabaseProductName();
			String createSQL = "";
			if(proName.toUpperCase().indexOf("MYSQL") != -1){
				createSQL = CREATE_SQL_MYSQL;
			}else if(proName.toUpperCase().indexOf("ORACLE") != -1){
				createSQL = CREATE_SQL_MYSQL;
			}else if(proName.toUpperCase().indexOf("MSSQL") != -1){
				createSQL = CREATE_SQL_MYSQL;
			}else{
				createSQL = CREATE_SQL_MYSQL;
			}
			createTable(TABLE_NAME_SEQUENCE,createSQL);
		} catch (SQLException e) {
			logger.error("创建序列表时发生异常.",e);
			throw new DBException(e);
		}
	}
    
    @Override
    public void createTable(String tableName,String createSQL) throws DBException{
    	try {
			DatabaseMetaData metaData = super.getDataSource().getConnection().getMetaData();
			if(isExists(tableName,metaData)){
				return;
			}
			super.getJdbcTemplate().execute(createSQL);
		} catch (SQLException e) {
			logger.error("创建[{}]时发生异常,建表语句[{}]",tableName,createSQL,e);
			throw new DBException(e);
		}
    }
    
	@Override
	public List<Sequence> loadAll() {
		return super.getJdbcTemplate().query(QUERY_ALL, rowMapper);
	}

	@Override
	public Sequence lock(String sequenceName) {
		return super.getJdbcTemplate().queryForObject(LOCK_BY,new Object[]{sequenceName} ,rowMapper);
	}

	@Override
	public void update(String sequenceName, Long beforeValue, Long afterValue) {
		super.getJdbcTemplate().update(UPDATE,afterValue, beforeValue, sequenceName);
	}
	
	/**
	 * 判断数据表是否存在
	 * @param tableName
	 * @param metaData
	 * @return
	 * @throws SQLException
	 */
	private boolean isExists(String tableName,DatabaseMetaData metaData) throws SQLException{
		ResultSet rs = null;
		try {
			rs = metaData.getTables(null, null, tableName, new String[]{"TABLE"});
			if(rs.next()){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	@Override
	public Sequence find(String sequenceName) throws DBException{
		List<Sequence> sequences = super.getJdbcTemplate().query(FIND_BY,new Object[]{sequenceName},rowMapper);
		if(sequences != null && sequences.size() > 0){
			return sequences.get(0);
		}else{
			return null;
		}
	}

	@Override
	public int save(Sequence sequence) throws DBException {
		return super.getJdbcTemplate().update(INSERT_SQL, new Object[]{sequence.getName(),sequence.getCurrentValue(),sequence.getIncrement(),sequence.getTotal(),sequence.getThreshold()});
	}

	@Override
	public int delete(String sequenceName) throws DBException {
		return super.getJdbcTemplate().update(DELETE_SQL, new Object[]{sequenceName});
	}

}
