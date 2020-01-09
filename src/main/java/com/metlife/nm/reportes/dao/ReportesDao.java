package com.metlife.nm.reportes.dao;

import java.util.List;

import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.notificaciones.vo.DiaVO;
import com.metlife.nm.reportes.vo.ReporteVO;

public interface ReportesDao {

	List<CatalogoVO> getCatDestinatario();
	
	ReporteVO getDatosReporte();

	ReporteVO getConfiguracion();

	List<DiaVO> getDiasReporteCalidad();

	void insDiaReporteCalidad(ReporteVO vo, int idFrecuencia);

	int getLastIdFrecuencia();

	void updateReporteCalidad(String dia_1, int idFrecuencia);

	void updDestinatario(ReporteVO vo, String destinatario);
	
	ReporteVO getDestinatario(ReporteVO vo);

}
