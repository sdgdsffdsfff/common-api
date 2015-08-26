package org.rency.common.cxf.restful.response;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Facade响应基类
 * @author rencaiyu
 *
 */
public abstract class FacadeResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2087621132739809681L;
	private boolean isSuccess = false;
	private String returnCode;
	private String returnMessage;
	
	public FacadeResponse() {
        super();
    }
	
	public FacadeResponse(String returnCode, String returnMessage) {
		super();
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }
    
    public FacadeResponse(boolean isSuccess, String returnCode, String returnMessage) {
    	super();
        this.isSuccess = isSuccess;
        this.returnCode = returnCode;
        this.returnMessage = returnMessage;
    }
    
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
		
}