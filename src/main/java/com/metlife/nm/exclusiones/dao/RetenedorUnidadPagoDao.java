package com.metlife.nm.exclusiones.dao;

import java.util.List;

import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;

public interface RetenedorUnidadPagoDao {

	public List<LabeValueBeanCascade> getRetenedores(boolean isNumeric, String filter);
	public List<LabelValueBean> getUndadesPago(String retenedor);
	
}
