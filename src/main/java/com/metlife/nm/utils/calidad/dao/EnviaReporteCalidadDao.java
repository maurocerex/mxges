package com.metlife.nm.utils.calidad.dao;

import java.util.HashMap;
import java.util.List;

import com.metlife.nm.utils.calidad.vo.DestinatarioVO;

public interface EnviaReporteCalidadDao {

    List<DestinatarioVO> getAgentes();
    
    List<DestinatarioVO> getPromotorias();

    List<HashMap<String, String>> getRegistrosAgente(String agente, String promotoria);

    List<HashMap<String, String>> getRegistrosPromotor(String promotoria);

    String getDirectorio();
}
