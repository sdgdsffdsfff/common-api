package org.rency.common.sequence.beans;

import java.io.Serializable;

public class Sequence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 976673466316857422L;
	
	/**
	 * 序列名
	 */
	private String name;
	
	/**
	 * 当前值
	 */
	private Long currentValue;
	
	/**
	 * 增量(前后序列号之差)
	 */
	private Integer increment;

	/**
	 * 单次取值数量(从数据库中取出的数据数量)
	 */
	private Integer total;
	
	/**
	 * 刷新阀值(当缓存中当前序列号与totals之差，当序列号大于等于totals减去阀值时更新数据库，并从新获取数据到缓存中)
	 */
	private Integer threshold;
	
	/**
	 * 刷新后值
	 */
	private Long afterValue;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(Long currentValue) {
		this.currentValue = currentValue;
	}

	public Integer getIncrement() {
		return increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getThreshold() {
		return threshold;
	}

	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}

	public Long getAfterValue() {
		return afterValue;
	}

	public void setAfterValue(Long afterValue) {
		this.afterValue = afterValue;
	}
}
