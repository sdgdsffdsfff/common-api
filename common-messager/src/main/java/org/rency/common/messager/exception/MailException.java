package org.rency.common.messager.exception;


public class MailException extends MessageException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8221027551268694038L;
	
	public MailException(){
		super();
	}
	
	public MailException(Throwable t){
		super(t);
	}
	
	public MailException(String message){
		super(message);
	}
	
	public MailException(String errorCode,String message){
		super(errorCode,message);
	}
	
	public String toString(){
		return super.toString();
	}

}
