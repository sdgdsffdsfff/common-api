/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package org.rency.common.utils.exception;


/**
 * @desc 内容未变化 
 * @author user_rcy@163.com
 * @date 2014年5月29日 下午9:33:20
 * @version 1.0.0
 */
public class NotModifiedException extends CoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120481997093521661L;
	
	public NotModifiedException(){
		super();
	}
	
	public NotModifiedException(Throwable t){
		super(t);
	}
	
	public NotModifiedException(String message){
		super(message);
	}
		
	
	public NotModifiedException(String errorCode,String message){
		super(errorCode,message);
	}
	
	public String toString(){
		return super.toString();
	}
	
}