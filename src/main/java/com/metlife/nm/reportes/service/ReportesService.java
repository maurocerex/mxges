package com.metlife.nm.reportes.service;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.notificaciones.vo.DiaVO;
import com.metlife.nm.reportes.vo.ReporteVO;

public interface ReportesService {

	List<CatalogoVO> getCatDestinatario();
	
	void guardar(String dia_1, String dia_2, String dia_3, String dia_4, String detinatario);

    ReporteVO getDatosReporte();

    List<DiaVO> getDiasReporteCalidad();
    
    ReporteVO getDestinatario();
}
