package org.rency.common.utils.enums;

public enum ErrorKind {
	SYS_ERR("E0001","系统错误"),
	
	USER_ERROR("U0000","用户异常"),
	USER_NOT_EXISTS("U0001","用户不存在"),
	AUTH_FAILED("U0002","权限不足"),
	
	DATA_ERROR("D0000","数据操作异常"),
	DATA_NOT_EXISTS("D0010","查询无数据"),
	DATA_SAVE_ERROR("D0011","保存失败"),
	DATA_UPDATE_ERROR("D0012","更新失败"),
	DATA_DELETE_ERROR("D0013","删除失败"),
	
	NET_ERROR("N0000","网络异常"),
	NET_NOT_FOUND("N0001","网络主机不存在"),
	SERVER_REFUSED("N0002","服务器拒绝访问"),
	SERVER_NOT_MIDIFIED("N0003","访问内容无变更"),
	
	MQ_ERROR("M0000","消息队列异常"),
	;
	
	private String code;
	private String message;
	
	ErrorKind(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public String getCode(){
		return this.code;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public static ErrorKind getErrorKind(String code){
		for(ErrorKind error : ErrorKind.values()){
			if(code.toUpperCase().equals(error.getCode().toUpperCase())){
				return error;
			}
		}
		return null;
	}

	public boolean equals(String code) {
        return getCode().equals(code);
    }
	
}