package com.metlife.nm.utils.notificacion.vo;

public class MailVO {
	
	private long idMail;
	private long idProceso;
	private String emailSubject;
	private String emailContent;
	private String emailTo;
	private String emailBcc;
	private String emailCc;
	private String emailEstatus;
	
	public final long getIdMail() {
		return idMail;
	}
	public final void setIdMail(long idMail) {
		this.idMail = idMail;
	}
	public  long getIdProceso() {
		return idProceso;
	}
	public  void setIdProceso(long idProceso) {
		this.idProceso = idProceso;
	}
	public  String getEmailSubject() {
		return emailSubject;
	}
	public  void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}
	public  String getEmailContent() {
		return emailContent;
	}
	public  void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public  String getEmailTo() {
		return emailTo;
	}
	public  void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public  String getEmailBcc() {
		return emailBcc;
	}
	public  void setEmailBcc(String emailBcc) {
		this.emailBcc = emailBcc;
	}
	public  String getEmailCc() {
		return emailCc;
	}
	public  void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}
	public  String getEmailEstatus() {
		return emailEstatus;
	}
	public  void setEmailEstatus(String emailEstatus) {
		this.emailEstatus = emailEstatus;
	}
	
	
}
