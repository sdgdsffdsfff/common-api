package org.rency.common.cxf.restful.template;

import org.apache.commons.lang.StringUtils;
import org.rency.common.cxf.restful.request.FacadeRequest;
import org.rency.common.cxf.restful.response.FacadeResponse;
import org.rency.common.utils.enums.ErrorKind;
import org.rency.common.utils.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FacadeTemplate<Request extends FacadeRequest, Response extends FacadeResponse, Entity> {

	private static final Logger logger = LoggerFactory.getLogger(FacadeTemplate.class);
	
	public Response execute(Request request){
		
		Response response = null;
		
		logger.info("[APP]->[MQ].接收[{}]请求:[{}].",interfaceName(),request);
		
		try{
			
			/**
			 * 参数校验
			 */
			Entity entity = validateAndConvert(request);
			
			/**
			 * 执行业务逻辑
			 */
			Entity result = doService(entity);
			
			/**
			 * 组装返回报文
			 */
			response = inrichWith(result);
			
		}catch(Exception e){
			
			response = exception(request,e);
			
		}
		
		logger.info("[MQ]->[APP]. 返回[{}]响应:[{}], 原始请求[{}].",interfaceName(),response,request);
		
		return response;
	}
	
	/**
	 * 请求交易名称
	 * @return
	 */
	public abstract String interfaceName();
	
	/**
	 * 参数验证
	 * @param request
	 * @return
	 */
	public abstract Entity validateAndConvert(Request request);
	
	/**
	 * 执行业务逻辑
	 * @param entity
	 * @return
	 */
	public abstract Entity doService(Entity entity);
	
	/**
	 * 组装返回报文
	 * @param result
	 * @return
	 */
	public Response inrichWith(Entity result){
		Response response = createSuccess();
		inrich(response, result);
		return response;
	}
	
	/**
	 * 创建返回响应体
	 * @return
	 */
	public abstract Response createResponse();
	
	protected Response createSuccess(){
		Response response = createResponse();
        response.setSuccess(true);
        response.setReturnMessage("执行成功");
        return response;
	}
	
	/**
	 * 创建返回响应体
	 * @param errorCode
	 * @param errorMessage
	 * @return
	 */
	protected Response createFailed(String errorCode,String errorMessage){
		Response response = createResponse();
        response.setSuccess(false);
        response.setReturnCode(errorCode);
        if(StringUtils.isNotBlank(errorMessage)){
        	response.setReturnMessage(errorMessage);
        }else{
        	response.setReturnMessage("执行失败");
        }
        return response;
	}
	
	/**
	 * 设置返回参数
	 * @param response
	 * @param result
	 */
	public abstract void inrich(Response response,Entity result);
	
	/**
	 * 交易异常
	 * @param request
	 * @param t
	 * @return
	 */
	public Response exception(Request request,Throwable t){
		logger.error("执行[{}]请求[{}]发生异常[{}].",interfaceName(),request,t);
		Response response = createResponse();
		response.setSuccess(false);
		if(t instanceof CoreException){
			response.setReturnCode(ErrorKind.SYS_ERR.getCode());
		}else if(t instanceof Exception){
			response.setReturnCode(ErrorKind.SYS_ERR.getCode());
		}else if(t instanceof IllegalArgumentException){
			response.setReturnCode(ErrorKind.SYS_ERR.getCode());
		}else{
			response.setReturnCode(ErrorKind.SYS_ERR.getCode());
		}
		response.setReturnMessage(t.getMessage());
		return response;
	}
}
