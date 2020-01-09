package com.metlife.nm.prioridad.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.prioridad.vo.ProcesoVO;

public interface PrioridadService {
    List<CatalogoVO> getLobs();

    List<ProcesoVO> getProcesosByIdLob(String idlob);

    void updatePant(ProcesoVO tmp);
}
