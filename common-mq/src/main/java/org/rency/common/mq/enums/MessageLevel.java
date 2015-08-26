package org.rency.common.mq.enums;

/**
 * 消息级别
 * @author rencaiyu
 *
 */
public enum MessageLevel {

	INFO("INFO","提示"),
	WARN("WARN","警告"),
	ERROR("ERROR","错误"),
	;
	
	private String code;
	private String msg;
	
	MessageLevel(String code,String msg){
		this.code =code;
		this.msg = msg;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public static MessageLevel get(String code){
		for(MessageLevel nt : MessageLevel.values()){
			if(nt.getCode().toUpperCase().equals(code.toUpperCase())){
				return nt;
			}
		}
		return null;
	}

	
}