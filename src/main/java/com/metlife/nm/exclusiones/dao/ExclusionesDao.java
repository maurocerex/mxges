package com.metlife.nm.exclusiones.dao;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;
import com.metlife.nm.exclusiones.vo.ExcluidosVO;

public interface ExclusionesDao {

    List<CatalogoVO> getLobs();

    List<CatalogoVO> getListadoProcesos(String lob);

    List<CatalogoVO> getDisponibles(String retenedor);

    List<ExcluidosVO> getSeleccionados(String lob, String proceso,String medioEnvio);
    
    List<ExcluidosVO> getSeleccionadosCt(String lob, String proces,String medioEnvioo);
    
    List<CatalogoVO> getCatSeleccionados(String lob, String proceso, String tipoEnvio);
    
    List<CatalogoVO> getCatSeleccionadosCT(String lob, String proceso, String tipoEnvio);

	List<ExcluidosVO> getListaExcluidos();

	void eliminar(ExcluidosVO vo);
	
	void eliminarCt(ExcluidosVO vo);

	void insertar(ExcluidosVO vo);
	
	void insertarCt(ExcluidosVO vo);

	List<LabelValueBean> getUndadesPago(String retenedo,String procesor, String tipoEnvio);
	
	List<LabelValueBean> getUndadesPagoCt(String retenedor,String proceso, String tipoEnvio);
	
	List<LabelValueBean> getUndadesPago(String procesor, String tipoEnvio);
	
	List<LabelValueBean> getUndadesPagoCt(String proceso, String tipoEnvio);
	
	LabeValueBeanCascade getRetenedores(boolean isNumeric, String filter);
}
