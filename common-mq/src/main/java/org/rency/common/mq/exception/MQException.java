package org.rency.common.mq.exception;

import org.rency.common.utils.exception.CoreException;

/**
 * 消息队列异常
 * @author rencaiyu
 *
 */
public class MQException extends CoreException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120481997093521661L;

	public MQException(){
		super();
	}
	
	public MQException(Throwable t){
		super(t);
	}
	
	public MQException(String message){
		super(message);
	}
	
	public MQException(String errorCode,String message){
		super(errorCode,message);
	}
	
	public String toString(){
		return super.toString();
	}
	
}
