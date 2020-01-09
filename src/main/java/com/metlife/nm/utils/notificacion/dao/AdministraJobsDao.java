package com.metlife.nm.utils.notificacion.dao;

import java.util.List;

import com.metlife.nm.utils.notificacion.vo.JobVO;


public interface AdministraJobsDao{
	
public List<JobVO> getJobs(); 

public void actualizaJobs(List<JobVO> jobs);
	
	
}

