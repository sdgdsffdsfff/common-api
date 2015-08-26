package org.rency.common.messager.mail.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 邮件实体类
* @ClassName: EmailEntity 
* @Description: TODO
* @Author user_rcy@163.com
* @Date 2015年6月5日 下午11:12:08 
*
 */
public class EmailEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135314095344152907L;

	private String from;  
	private String fromName;  
	private List<String> to = new ArrayList<String>();
	private List<String> cc = new ArrayList<String>();
	private String subject;  
	private String content;    //邮件模板
	private boolean isHtml;//是否以html格式发送
	  
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getFromName() {
		return fromName;
	}
	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
	public List<String> getTo() {
		return to;
	}
	public void setTo(List<String> to) {
		this.to = to;
	}
	public List<String> getCc() {
		return cc;
	}
	public void setCc(List<String> cc) {
		this.cc = cc;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public boolean isHtml() {
		return isHtml;
	}
	public void setHtml(boolean isHtml) {
		this.isHtml = isHtml;
	}
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		builder.append("from:").append(from).append(", ");
		builder.append("fromName:").append(fromName).append(", ");
		builder.append("to:");
		if(to != null && to.size() > 0){
			for(String t : to){
				builder.append(t+";");
			}
		}
		builder.append(", ");
		builder.append("cc:");
		if(cc != null && cc.size() > 0){
			for(String c : cc){
				builder.append(c+";");
			}
		}
		builder.append(", ");
		builder.append("subject:").append(subject).append(", ");
		builder.append("content:").append(content);
		builder.append("}");
		return builder.toString();
	}
}
