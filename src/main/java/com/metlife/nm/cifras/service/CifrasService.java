package com.metlife.nm.cifras.service;

import java.util.List;
import java.util.Map;

import com.metlife.nm.cifras.vo.CifrasVO;
import com.metlife.nm.domain.CatalogoVO;

public interface CifrasService {

	List<CatalogoVO> getLobs();

	List<CatalogoVO> getListadoProcesos();

	List<CifrasVO> getCifras(String proceso, String medio, String fecInicio, String fecFin, String tipoReporte);

	List<CifrasVO> getDetalle(String proceso, String medio, String fecInicio, String fecFin);

	List<Map<String,Object>> getReporte(String proceso, String medio, String fecIni, String fecFin, String tipoReport);

	List<CatalogoVO> getCatMedio();
	
    int getVigencia();

    void guardar(List<CifrasVO> registros);
    
    int updateCC(String tipoReporte);

}
