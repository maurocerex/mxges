package com.metlife.nm.cifras.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.metlife.nm.cifras.vo.CifrasVO;
import com.metlife.nm.cifras.vo.ReenvioVO;
import com.metlife.nm.domain.CatalogoVO;

public interface CifrasDao {

	List<CatalogoVO> getLobs();

	List<CatalogoVO> getListadoProcesos();

	List<CifrasVO> getCifras(String proceso, String medio, String fecInicio, String fecFin, String tipoReporte);

	List<CifrasVO> getDetalle(String proceso, String medio, String fecInicio, String fecFin);

	List<Map<String,Object>> getReporte(String proceso, String medio, String fecIni, String fecFin, String tipoReport);

	List<CatalogoVO> getCatMedio();

    int getVigencia();

    List<ReenvioVO> getRegistrosReenvio(String idProceso, String medio, String fecInicio, String fecFin);

    void cambiaEstatus(String idProceso, String idEnvio);
    
    int updateCC(String tipoReporte);

}
