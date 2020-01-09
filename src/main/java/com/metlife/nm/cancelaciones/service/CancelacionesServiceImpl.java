package com.metlife.nm.cancelaciones.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.metlife.nm.cancelaciones.dao.CancelacionesDao;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;

@Service(value = BeanNames.CancelacionesService)
public class CancelacionesServiceImpl implements CancelacionesService {

	@Resource(name = BeanNames.CancelacionesDao)
	private CancelacionesDao dao;
	
	public CatalogoVO getRetainers(String retenedor) {		
		return dao.getRetainer(retenedor);
	}

	public List<CatalogoVO> getPayUnit(String retenedor) {
		return dao.getPayUnit(retenedor);
	}

	public String getCancelaNotif(String retenedor, String retenedorCT,
			String unidadPago, String unidadPagoCT, String fechaInicio,
			String fechaFin) {
		return dao.getCancelaNotif(retenedor, retenedorCT, unidadPago, unidadPagoCT, fechaInicio, fechaFin);
	}

}
