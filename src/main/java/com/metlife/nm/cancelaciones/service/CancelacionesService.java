package com.metlife.nm.cancelaciones.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;

public interface CancelacionesService {
	CatalogoVO getRetainers(String retenedor);
	List<CatalogoVO> getPayUnit(String retenedor);
	String getCancelaNotif(String retenedor, String retenedorCT, String unidadPago, String unidadPagoCT, String fechaInicio, String fechaFin);
}
