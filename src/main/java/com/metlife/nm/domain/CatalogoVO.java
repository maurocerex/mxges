package com.metlife.nm.domain;


public class CatalogoVO extends BaseObjectVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4014697983604874882L;
	private int key;
	private String keyTxt;
	private String value;
	
	public CatalogoVO(){
		
	}

	public CatalogoVO(int key, String value) {
		this.key = key;
		this.value = value;

	}

	public final int getKey() {
		return key;
	}

	public final void setKey(int key) {
		this.key = key;
	}

	public final String getKeyTxt() {
		return keyTxt;
	}

	public final void setKeyTxt(String keyTxt) {
		this.keyTxt = keyTxt;
	}

	public final String getValue() {
		return value;
	}

	public final void setValue(String value) {
		this.value = value;
	}

}
