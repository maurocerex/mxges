package com.metlife.nm.utils.notificacion.service;

import java.util.List;

import com.metlife.nm.utils.notificacion.vo.JobVO;

public interface AdministraJobsService {
	
	 public List<JobVO> getJobs();
	 
	 public void guardarCambios(List<JobVO> registros) throws Exception;
	 

}
