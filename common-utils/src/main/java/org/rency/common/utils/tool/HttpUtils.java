package org.rency.common.utils.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.rency.common.utils.exception.CoreException;
import org.rency.common.utils.exception.NotModifiedException;
import org.rency.common.utils.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * 发送HTTP POST请求
	 * @param uri
	 * @param params
	 * @return
	 */
	public String sendPost(String uri,String params){
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try{
			URL url = new URL(uri);
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			out = new PrintWriter(connection.getOutputStream());
			out.print(params);
			out.flush();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = "";
			while( (line = in.readLine() ) != null){
				result += line;
			}
		}catch(Exception e){
			logger.error("send http post request error.uri:{},params:{}.",uri,params,e);
		}finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
		return result;
	}
	
	/**
	 * @desc 读取页面
	 * @author user_rcy@163.com
	 * @date 2014年11月2日 下午6:00:01
	 * @param taskQueue
	 * @param data 请求参数
	 * @param taskQueueService 如果不为null，则更新URL请求状态
	 * @return
	 * @throws Exception
	 */
 	public static Response getResponse(String url) throws Exception{
		try{
			Connection conn = Jsoup.connect(url).timeout(5000).method(Method.GET);
			//配置模拟浏览器
			conn.userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.102 Safari/537.36");
			Response response = conn.execute();
			int statusCode = response.statusCode();
			//判断服务器返回状态
			if(httpResponseStatus(statusCode)){
				return response;
			}else{
				return null;
			}
		}catch(NotModifiedException e){
			logger.debug("jsoup connection["+url+"] error."+e);
			return null;
		}catch (UnknownHostException e) {
			logger.debug("jsoup connection["+url+"] error."+e);
			throw new SocketTimeoutException("jsoup connection["+url+"] error."+e);
		}catch(SocketTimeoutException e){
			logger.debug("jsoup connection["+url+"] error."+e);
			throw e;
		}catch(RuntimeException e){
			logger.error("jsoup connection["+url+"] error."+e);
			return null;
		}catch(Exception e){
			logger.error("jsoup connection["+url+"] error."+e);
			throw new CoreException("jsoup connection["+url+"] error."+e);
		}
	}

	/**
	 * @desc 根据Http相应状态码判断请求返回状态
	 * @date 2014年10月29日 下午1:43:59
	 * @param statusCode Http相应状态码
	 * @return
	 * @throws Exception
	 */
	public static boolean httpResponseStatus(int statusCode) throws Exception{
		switch (statusCode) {
			case 100:
				return true;
			case 101:
				return true;
			case 102:
				return true;
			case 200:
				return true;
			case 201:
				return true;
			case 202:
				return true;
			case 203:
				return true;
			case 204:
				return true;
			case 205:
				return true;
			case 206:
				return true;
			case 207:
				return true;
			case 300:
				return true;
			case 301:
				return true;
			case 302:
				return true;
			case 303:
				return true;
			case 304:
				throw new NotModifiedException("The request content not modify, and status code["+statusCode+"]");
			case 305:
				return true;
			case 306:
				return true;
			case 307:
				return true;
			case 400:
				throw new RuntimeException("The request cannot be understand on server, and status code["+statusCode+"]");
			case 401:
				throw new RuntimeException("The request should user authorized, and status code["+statusCode+"]");
			case 402:
				return true;
			case 403:
				throw new ServerException("status code["+statusCode+"]");
			case 404:
				throw new SocketTimeoutException("The request not found on server, and status code["+statusCode+"]");
			case 405:
				return false;
			case 406:
				return false;
			case 407:
				throw new RuntimeException("The request must use proxy on client, and status code["+statusCode+"]");
			case 408:
				throw new SocketTimeoutException("The request time out, and status code["+statusCode+"]");
			case 409:
				throw new RuntimeException("The request status is conflict, and cannot finish request.And status code["+statusCode+"]");
			case 410:
				throw new RuntimeException("The request is not avaliable, and status code["+statusCode+"]");
			case 411:
				return true;
			case 412:
				return true;
			case 413:
				throw new RuntimeException("The request entity too large, and status code["+statusCode+"]");
			case 414:
				throw new RuntimeException("The request is too long, and status code["+statusCode+"]");
			case 415:
				return true;
			case 416:
				return true;
			case 417:
				return true;
			case 421:
				throw new SocketTimeoutException("The request beyond connect max count, and status code is "+statusCode);
			case 422:
				return true;
			case 423:
				throw new SocketTimeoutException("The request locked, and status code is "+statusCode);
			case 424:
				throw new SocketTimeoutException("The request failed dependency, and status code is "+statusCode);
			case 425:
				return true;
			case 426:
				return true;
			case 449:
				return true;
			case 500:
				throw new SocketTimeoutException("Internal server error, and status code is "+statusCode);
			case 501:
				throw new SocketTimeoutException("Not Implemented, and status code is "+statusCode);
			case 502:
				throw new SocketTimeoutException("Bad Gateway, and status code is "+statusCode);
			case 503:
				throw new SocketTimeoutException("Service Unavailable, and status code is "+statusCode);
			case 504:
				throw new SocketTimeoutException("Gateway timeout, and status code is "+statusCode);
			case 505:
				throw new SocketTimeoutException("Http Version Not Supported, and status code is "+statusCode);
			case 506:
				throw new SocketTimeoutException("Variant Also Negotiates, and status code is "+statusCode);
			case 507:
				throw new SocketTimeoutException("Insufficient Storage, and status code is "+statusCode);
			case 508:
				throw new SocketTimeoutException("Loop Detected, and status code is "+statusCode);
			case 509:
				throw new SocketTimeoutException("Bandwidth Limit Exceeded, and status code is "+statusCode);
			case 510:
				throw new SocketTimeoutException("Not Extended, and status code is "+statusCode);
			case 600:
				return true;
			default:
				return false;
		}
	}

}