package com.metlife.nm.prioridad.dao;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.prioridad.vo.ProcesoVO;

public interface PrioridadDao {

    List<CatalogoVO> getLobs();

    List<ProcesoVO> getProcesosByIdLob(String idlob);

    void updatePant(ProcesoVO tmp);

}
