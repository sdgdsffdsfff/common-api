package org.rency.common.utils.tool;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.rency.common.utils.domain.SYSDICT;
import org.rency.common.utils.domain.SpringContextHolder;
import org.rency.common.utils.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Producer;

/**
 * 验证码生成工具
 * @author rencaiyu
 *
 */
public class KaptchaUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(KaptchaUtils.class);

	/**
	 * 生成验证码
	 * @param request
	 * @param response
	 * @throws CoreException
	 */
	public static void generator(HttpServletRequest request,HttpServletResponse response) throws CoreException{
		try{
			Producer kaptchaProducer = (Producer) SpringContextHolder.getBean("kaptchaProducer");
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");  
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
	        response.setContentType("image/jpeg");
	        String kapTxt = kaptchaProducer.createText();
	        logger.debug("create kaptcha image."+kapTxt);
	        request.getSession().setAttribute(SYSDICT.SESSION_KAPTCHA_KEY, kapTxt);
	        BufferedImage bi = kaptchaProducer.createImage(kapTxt);
	        ServletOutputStream sos = response.getOutputStream();
	        ImageIO.write(bi, "jpg", sos);
	        try {
	        	sos.flush();
			} finally {
				sos.close();
			}
		}catch(IOException e){
			logger.error("生成验证码异常.",e);
			throw new CoreException(e);
		}
	}
	
	/**
	 * 校验验证码是否正确
	 * @param request
	 * @param randCode
	 * @return
	 * @throws Exception
	 */
	public static boolean validateRandCode(HttpServletRequest request,String randCode){
		logger.info("kaptcha validate:" + request.getSession().getAttribute(SYSDICT.SESSION_KAPTCHA_KEY));
		if (StringUtils.isBlank(randCode) || !randCode.equals(request.getSession().getAttribute(SYSDICT.SESSION_KAPTCHA_KEY))) {
			return false;
		}
		return true;
	}
	
}