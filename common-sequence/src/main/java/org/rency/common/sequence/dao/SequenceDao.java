package org.rency.common.sequence.dao;

import java.sql.SQLException;
import java.util.List;

import org.rency.common.sequence.beans.Sequence;
import org.rency.common.utils.exception.DBException;

public interface SequenceDao {
	
	public void createTable() throws DBException;
	
	/**
	 * 创建数据表
	 * @param tableName 表名
	 * @param createSQL 创建表脚本
	 * @throws SQLException
	 */
	public void createTable(String tableName,String createSQL) throws DBException;
	
	public Sequence find(String sequenceName) throws DBException;
	
	public int save(Sequence sequence) throws DBException;
	
	public int delete(String sequenceName) throws DBException;
	
    public List<Sequence> loadAll();

    public Sequence lock(String sequenceName);

    public void update(String sequenceName, Long beforeValue, Long afterValue);

}