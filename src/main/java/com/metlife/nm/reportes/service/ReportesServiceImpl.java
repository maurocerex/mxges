package com.metlife.nm.reportes.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.notificaciones.vo.DiaVO;
import com.metlife.nm.reportes.dao.ReportesDao;
import com.metlife.nm.reportes.vo.ReporteVO;

@Service(value = BeanNames.ReportesService)
public class ReportesServiceImpl implements ReportesService {

	@Resource(name = BeanNames.ReportesDao)
	private ReportesDao dao;

	private static final Logger log = Logger.getLogger(ReportesServiceImpl.class);

	public List<CatalogoVO> getCatDestinatario(){
		return dao.getCatDestinatario();
	}
	
    public ReporteVO getDestinatario() {
        ReporteVO vo = dao.getConfiguracion();
        return dao.getDestinatario(vo);
    }

	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void guardar(String dia_1, String dia_2, String dia_3, String dia_4, String detinatario) {

		ReporteVO vo = dao.getConfiguracion();

		/**
		 * Existe un reporte de calidad?
		 */
		ArrayList<DiaVO> dias = (ArrayList<DiaVO>) dao.getDiasReporteCalidad();
		if (log.isDebugEnabled()) {
			log.debug("Validando Tamaño " + dias.size());
		}
		dao.updDestinatario(vo, detinatario);
		if (dias == null || dias.size() == 0) {
			// INSERTAMOS
			vo.setDia(dia_1);
			dao.insDiaReporteCalidad(vo, dao.getLastIdFrecuencia());
			vo.setDia(dia_2);
			dao.insDiaReporteCalidad(vo, dao.getLastIdFrecuencia());
			vo.setDia(dia_3);
			dao.insDiaReporteCalidad(vo, dao.getLastIdFrecuencia());
			vo.setDia(dia_4);
			dao.insDiaReporteCalidad(vo, dao.getLastIdFrecuencia());
		} else if (dias.size() == 4) {
			vo.setDia(dia_1);
			dao.updateReporteCalidad(dia_1, dias.get(0).getIdFrecuencia());
			vo.setDia(dia_2);
			dao.updateReporteCalidad(dia_2, dias.get(1).getIdFrecuencia());
			vo.setDia(dia_3);
			dao.updateReporteCalidad(dia_3, dias.get(2).getIdFrecuencia());
			vo.setDia(dia_4);
			dao.updateReporteCalidad(dia_4, dias.get(3).getIdFrecuencia());
		} else if (dias.size() > 4) {
			throw new RuntimeException("Existen mas de 4 dias para el envio del Reporte");
		}

	}

	public ReporteVO getDatosReporte() {
		ReporteVO reporte = dao.getDatosReporte();

		return reporte;
	}

	public int getLastIdFrecuencia() {
		return dao.getLastIdFrecuencia();
	}

	public List<DiaVO> getDiasReporteCalidad() {
		return dao.getDiasReporteCalidad();
	}

}
