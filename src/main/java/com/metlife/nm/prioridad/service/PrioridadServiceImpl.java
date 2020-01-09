package com.metlife.nm.prioridad.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.prioridad.dao.PrioridadDao;
import com.metlife.nm.prioridad.vo.ProcesoVO;

@Service(value = BeanNames.PrioridadService)
public class PrioridadServiceImpl implements PrioridadService {

	@Resource(name = BeanNames.PrioridadDao)
	private PrioridadDao dao;

	public List<CatalogoVO> getLobs() {
		return dao.getLobs();
	}

	public List<ProcesoVO> getProcesosByIdLob(String idlob) {
		return dao.getProcesosByIdLob(idlob);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updatePant(ProcesoVO tmp) {
		dao.updatePant(tmp);
	}

}
