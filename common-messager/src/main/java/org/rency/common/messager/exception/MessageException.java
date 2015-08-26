package org.rency.common.messager.exception;

import org.rency.common.utils.exception.CoreException;

public class MessageException extends CoreException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8221027551268694038L;
	
	public MessageException(){
		super();
	}
	
	public MessageException(Throwable t){
		super(t);
	}
	
	public MessageException(String message){
		super(message);
	}
	
	public MessageException(String errorCode,String message){
		super(errorCode,message);
	}
	
	public String toString(){
		return super.toString();
	}

}
