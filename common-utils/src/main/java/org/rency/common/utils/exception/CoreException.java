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
 * @desc 异常 
 * @author user_rcy@163.com
 * @date 2014年5月29日 下午9:33:20
 * @version 1.0.0
 */
public class CoreException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120481997093521661L;

	private String errorCode;	
	private String errorMessage;
	
	public CoreException(){
		super();
	}
	
	public CoreException(String message){
		super(message);
	}
	
	public CoreException(Throwable t){
		super(t);
	}
	
	public CoreException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public CoreException(String errorCode, String errorMessage, Throwable t) {
        super(errorMessage,t);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String toString(){
		return "{errorCode:"+errorCode+", errorMessage:"+errorMessage+"}";
	}
	
}