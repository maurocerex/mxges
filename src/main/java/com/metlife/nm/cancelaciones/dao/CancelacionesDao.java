package com.metlife.nm.cancelaciones.dao;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;

public interface CancelacionesDao {
	CatalogoVO getRetainer(String retenedor);
	List<CatalogoVO> getPayUnit(String retenedor);
	String getCancelaNotif(String retenedor, String retenedorCT, String unidadPago, String unidadPagoCT, String fechaInicio, String fechaFin);
}
