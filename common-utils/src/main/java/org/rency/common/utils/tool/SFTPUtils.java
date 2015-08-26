package org.rency.common.utils.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.rency.common.utils.exception.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

/**
 * SFTP工具
 * @author rencaiyu
 *
 */
public class SFTPUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(SFTPUtils.class);
	
	private static Session session = null;
    private static Channel channel = null;
	
    /**
     * 建立SFTP连接
     * @param sftpHost 远程主机
     * @param sftpPort 连接端口(默认22)
     * @param sftpUserName 用户名
     * @param sftpPasswd 密码
     * @param timeout 超时时间(默认60000)
     * @return
     * @throws IOException 
     */
	private static ChannelSftp getChannel(String sftpHost,int sftpPort,String sftpUserName,String sftpPasswd,int timeout) throws IOException{
		sftpPort = sftpPort == 0 ? 22 : sftpPort;
		timeout = timeout == 0 ? 60000 : timeout;
		Assert.notNull(sftpHost, "SFTP主机不能为空.");
		Assert.notNull(sftpUserName, "SFTP用户不能为空.");
		Assert.notNull(sftpPasswd, "SFTP密码不能为空.");
		
		//创建JSch对象
		JSch jsch = new JSch();
		try {
			if(channel != null){
				return (ChannelSftp) channel;
			}
			session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
			logger.info("SFTP Session Created.");
			session.setPassword(sftpPasswd);
			Properties props = new Properties();
			props.put("StrictHostKeyChecking", "on");
			session.setConfig(props);//为Session对象设置properties
			session.setTimeout(timeout);//设置timeout时间
			session.connect();//通过Session建立链接
			logger.info("SFTP Session Connected.");
			
			channel = session.openChannel("sftp");//打开SFTP通道
			channel.connect();//建立SFTP通道的连接
			logger.info("SFTP Connect Successfully To [{}], and connected at channel[{}].",sftpHost,channel);
			return (ChannelSftp) channel;
		} catch (JSchException e) {
			logger.error("建立远程SFTP连接[{}]异常.",sftpHost,e);
			throw new IOException("建立远程SFTP连接[{"+sftpHost+"}]异常."+e);
		}
	}
	
	/**
	 * 关闭资源
	 */
	private static void closeResource(){
		if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
        logger.info("Close SFTP Resources.");
	}
	
	/**
	 * 列出远程目录下文件列表
	 * @param remoteDir
	 * @param sftpHost
	 * @param sftpUserName
	 * @param sftpPasswd
	 * @return
	 * @throws CoreException
	 */
	@SuppressWarnings("unchecked")
	public static List<String> listFiles(String remoteDir,String sftpHost,String sftpUserName,String sftpPasswd) throws CoreException{
		ChannelSftp channel = null;
		try{
			channel = getChannel(sftpHost,0,sftpUserName,sftpPasswd,0);
			List<String> listFiles = new ArrayList<String>();
			Vector<LsEntry> vector = channel.ls(remoteDir);
			for (LsEntry entry : vector) {
				SftpATTRS attr =  entry.getAttrs();
				if(!attr.isDir()){
					listFiles.add(entry.getFilename());
				}
			}
			return listFiles;
		} catch (Exception e) {
			logger.error("remote directory[{}/{}] list files error.",sftpHost,remoteDir,e);
			throw new CoreException(e);
		}finally{
			if(channel != null){
				channel.exit();
				closeResource();
			}
		}
	}
	
	/**
	 * 下载远程文件
	 * @param remoteDir 远程目录
	 * @param fileName 远程文件名
	 * @param localDir 本地目录
	 * @param sftpHost 远程主机
	 * @param sftpUserName 用户名
	 * @param sftpPasswd 密码
	 * @throws CoreException
	 */
	public static void download(String remoteDir,String fileName,String localDir,String sftpHost,String sftpUserName,String sftpPasswd) throws CoreException{
		logger.info("download remote file[{}] start.",formatPath(remoteDir)+fileName);
		ChannelSftp channel = null;
		try{
			channel = getChannel(sftpHost,0,sftpUserName,sftpPasswd,0);
			channel.cd(remoteDir);
			File file = new File(formatPath(localDir)+fileName);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			channel.get(formatPath(remoteDir)+fileName, new FileOutputStream(file));
		}catch(Exception e){
			logger.error("download remote file[{}/{}{}] error.",sftpHost,remoteDir,fileName,e);
			throw new CoreException(e);
		}finally{
			if(channel != null){
				channel.exit();
				closeResource();
			}
		}
		logger.info("download remote file[{}] finish.",formatPath(remoteDir)+fileName);
	}
	
	/**
	 * 下载远程文件
	 * @param remoteDir 远程目录
	 * @param fileNameSuffix 远程文件名后缀
	 * @param localDir 本地目录
	 * @param sftpHost 远程主机
	 * @param sftpUserName 用户名
	 * @param sftpPasswd 密码
	 * @throws CoreException
	 */
	public static void downloadWithNameSuffix(String remoteDir,String fileNameSuffix,String localDir,String sftpHost,String sftpUserName,String sftpPasswd) throws CoreException{
		remoteDir = formatPath(remoteDir);
		logger.info("download remote file[{}] start.",remoteDir+"*"+fileNameSuffix);
		ChannelSftp channel = null;
		try{
			channel = getChannel(sftpHost,0,sftpUserName,sftpPasswd,0);
			@SuppressWarnings("unchecked")
			Vector<LsEntry> vector = channel.ls(remoteDir);
			for (LsEntry entry : vector) {
				SftpATTRS attr =  entry.getAttrs();
				if(!attr.isDir() && entry.getFilename().endsWith(fileNameSuffix)){
					switchDir(remoteDir,channel);
					File file = new File(formatPath(localDir)+entry.getFilename());
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					channel.get(remoteDir+entry.getFilename(), new FileOutputStream(file));
				}
			}
		}catch(Exception e){
			logger.error("download remote file[{}{}{}] error.",formatPath(sftpHost),remoteDir,"*"+fileNameSuffix,e);
			throw new CoreException(e);
		}finally{
			if(channel != null){
				channel.exit();
				closeResource();
			}
		}
		logger.info("download remote file[{}] finish.",formatPath(remoteDir)+"*"+fileNameSuffix);
	}
	
	/**
	 * 上传文件
	 * @param remoteDir 远程目录
	 * @param localFile 待上传文件
	 * @param sftpHost 远程主机
	 * @param sftpUserName 用户名
	 * @param sftpPasswd 密码
	 * @throws CoreException
	 */
	public static void upload(String remoteDir,File localFile,String sftpHost,String sftpUserName,String sftpPasswd) throws CoreException{
		remoteDir = formatPath(remoteDir);
		logger.info("update file[{}] to remote[{}] start.",localFile.getAbsolutePath(),sftpHost+remoteDir);
		ChannelSftp channel = null;
		try{
			channel = getChannel(sftpHost,0,sftpUserName,sftpPasswd,0);
			if(!localFile.exists()){
				logger.error("本地文件[{}]不存在.",localFile.getAbsolutePath());
				throw new FileNotFoundException(localFile.getAbsolutePath());
			}
			switchDir(remoteDir,channel);
			channel.put(new FileInputStream(localFile), remoteDir+localFile.getName());
		}catch(Exception e){
			logger.error("updoad file[{}] to remote [{}] error.",localFile.getAbsolutePath(),sftpHost+remoteDir,e);
			throw new CoreException(e);
		}finally{
			if(channel != null){
				channel.exit();
				closeResource();
			}
		}
		logger.info("update file[{}] to remote[{}] finish.",localFile.getAbsolutePath(),sftpHost+remoteDir);
	}
	
	/**
	 * 批量上传文件
	 * @param remoteDir 远程目录
	 * @param localFile 待上传文件
	 * @param sftpHost 远程主机
	 * @param sftpUserName 用户名
	 * @param sftpPasswd 密码
	 * @throws CoreException
	 */
	public static void uploadBatch(String remoteDir,String localDir,String sftpHost,String sftpUserName,String sftpPasswd) throws CoreException{
		remoteDir = formatPath(remoteDir);
		logger.info("update directory files[{}] to remote[{}] start.",localDir,sftpHost+remoteDir);
		ChannelSftp channel = null;
		try{
			channel = getChannel(sftpHost,0,sftpUserName,sftpPasswd,0);
			File localFile = new File(localDir);
			if(!localFile.exists()){
				logger.error("本地文件[{}]不存在.",localFile.getAbsolutePath());
				throw new FileNotFoundException(localFile.getAbsolutePath());
			}
			switchDir(remoteDir,channel);
			File[] files = localFile.listFiles();
			for(File f : files){
				channel.put(new FileInputStream(f), remoteDir+f.getName());
			}
		}catch(Exception e){
			logger.error("updoad directory files[{}] to remote [{}] error.",localDir,sftpHost+remoteDir,e);
			throw new CoreException(e);
		}finally{
			if(channel != null){
				channel.exit();
				closeResource();
			}
		}
		logger.info("update directory files[{}] to remote[{}] finish.",localDir,sftpHost+remoteDir);
	}
	
	public static void delete(String remoteDir,String remoteFileName,String sftpHost,String sftpUserName,String sftpPasswd) throws CoreException{
		remoteDir = formatPath(remoteDir);
		logger.info("delete remote file[{}{}{}] start.",sftpHost,remoteDir,remoteFileName);
		ChannelSftp channel = null;
		try{
			channel = getChannel(sftpHost,0,sftpUserName,sftpPasswd,0);
			if(StringUtils.isBlank(remoteFileName)){
				channel.rmdir(remoteDir);
			}else{
				switchDir(remoteDir,channel);
				channel.rm(remoteFileName);
			}
		}catch(Exception e){
			logger.info("delete remote file[{}{}{}] error.",sftpHost,remoteDir,remoteFileName,e);
			throw new CoreException(e);
		}finally{
			if(channel != null){
				channel.exit();
				closeResource();
			}
		}
		logger.info("delete remote file[{}{}{}] finish.",sftpHost,remoteDir,remoteFileName);
	}
	
	/**
	 * 切换远程目录
	 * @param directory 远程目录
	 * @param channel
	 * @throws CoreException
	 */
	private static void switchDir(String directory,ChannelSftp channel) throws CoreException{
		try{
			try {
				channel.cd(directory);
			} catch (SftpException e) {
				channel.mkdir(directory);
				channel.cd(directory);
			}
		}catch(Exception e){
			logger.error("switch to directory[{}] error.",directory,e);
			throw new CoreException(e);
		}
	}
	
	private static String formatPath(String path){
		if(path.endsWith("/")){
			return path;
		}
		return path + "/";
	}

}
