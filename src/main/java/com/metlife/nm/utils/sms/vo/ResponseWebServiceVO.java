package com.metlife.nm.utils.sms.vo;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ResponseWebServiceVO {
 
	/**
	 * Codigo de respuesta del servicio 1 exitoso, 5 fallido.
	 */
	private String code;
	/**
	 * Descripcion de codigo de respuesta.
	 */
	private String messageCode;
	/**
	 * Codigo de error catalogado
	 */
	private String resultInfoCode;
	/**
	 * Descripcion "amigable del error"
	 */
	private String resultInfoDesc;
	/**
	 * Error tecnico excepci�n, stack trace, etc.
	 */
	private String resultInfoSysMessageCode;
	/**
	 * Codigo de severidad.
	 */
	private String resultInfoSeverity;
	/**
	 * Estatus de entrega.
	 */
	private String deliveryStatus;
	/**
	 * Tipo de error: C-comunicacion, V-validacion y T-transaccion.
	 */
	private String resultInfoErrorType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public String getResultInfoCode() {
		return resultInfoCode;
	}

	public void setResultInfoCode(String resultInfoCode) {
		this.resultInfoCode = resultInfoCode;
	}

	public String getResultInfoDesc() {
		return resultInfoDesc;
	}

	public void setResultInfoDesc(String resultInfoDesc) {
		this.resultInfoDesc = resultInfoDesc;
	}

	public String getResultInfoSysMessageCode() {
		return resultInfoSysMessageCode;
	}

	public void setResultInfoSysMessageCode(String resultInfoSysMessageCode) {
		this.resultInfoSysMessageCode = resultInfoSysMessageCode;
	}

	public String getResultInfoSeverity() {
		return resultInfoSeverity;
	}

	public void setResultInfoSeverity(String resultInfoSeverity) {
		this.resultInfoSeverity = resultInfoSeverity;
	}

	public String getResultInfoErrorType() {
		return resultInfoErrorType;
	}

	public void setResultInfoErrorType(String resultInfoErrorType) {
		this.resultInfoErrorType = resultInfoErrorType;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
