package com.metlife.nm.exclusiones.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;

public interface ExclusionesService {

    List<CatalogoVO> getLobs();

    List<CatalogoVO> getListadoProcesos(String lob);

    List<CatalogoVO> getDisponibles(String retenedor);

    List<CatalogoVO> getSeleccionados(String lob, String proceso, String tipoEnvio);
    
    List<CatalogoVO> getSeleccionadosCT(String lob, String proceso,String tipoEnvio);

	List<String> guardar(String[] seleccionados, String lob, String proceso,String medioEnvio);
	
	List<String> guardarCt(String[] seleccionadosCt, String lob, String proceso,String medioEnvio);

    List<LabelValueBean> getUndadesPago(String retenedor,String proceso, String tipoEnvio);
    
    List<LabelValueBean> getUndadesPagoCt(String retenedor,String proceso, String tipoEnvio);
    
List<LabelValueBean> getUndadesPago(String proceso, String tipoEnvio);
    
    List<LabelValueBean> getUndadesPagoCt(String proceso, String tipoEnvio);
    
    LabeValueBeanCascade getRetenedores(boolean isNumeric, String filter);
}
