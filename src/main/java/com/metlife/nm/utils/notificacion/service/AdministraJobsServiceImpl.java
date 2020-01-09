package com.metlife.nm.utils.notificacion.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.utils.notificacion.dao.AdministraJobsDao;
import com.metlife.nm.utils.notificacion.vo.JobVO;


@Service(value=BeanNames.AdministraJobsService)
public class AdministraJobsServiceImpl implements AdministraJobsService {

	@Resource(name=BeanNames.AdministraJobsDao)
    private AdministraJobsDao dao;
	
	public List<JobVO> getJobs() {
		return dao.getJobs();
	}

	public void guardarCambios(List<JobVO> registros) throws Exception {
		dao.actualizaJobs(registros);
	}

}
