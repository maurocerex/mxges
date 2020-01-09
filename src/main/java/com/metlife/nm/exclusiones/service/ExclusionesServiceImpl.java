package com.metlife.nm.exclusiones.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.CatalogoVO;
import com.metlife.nm.domain.LabeValueBeanCascade;
import com.metlife.nm.domain.LabelValueBean;
import com.metlife.nm.exclusiones.dao.ExclusionesDao;
import com.metlife.nm.exclusiones.vo.ExcluidosVO;

@Service(value = BeanNames.ExclusionesService)
public class ExclusionesServiceImpl implements ExclusionesService {

	@Resource(name = BeanNames.ExclusionesDao)
	private ExclusionesDao dao;

	private static final Logger log = Logger.getLogger(ExclusionesServiceImpl.class);

	public List<CatalogoVO> getLobs() {
		return dao.getLobs();
	}

	public List<CatalogoVO> getListadoProcesos(String lob) {
		return dao.getListadoProcesos(lob);
	}

	public List<CatalogoVO> getDisponibles(String retenedor) {
	    if(log.isDebugEnabled()){
	        log.debug("TAMANIO "+dao.getDisponibles(retenedor).size());
	    }
		return dao.getDisponibles(retenedor);
	}

	public List<CatalogoVO> getSeleccionados(String lob, String proceso, String tipoEnvio) {
		return dao.getCatSeleccionados(lob, proceso, tipoEnvio);
	}

	public List<CatalogoVO> getSeleccionadosCT(String lob, String proceso, String tipoEnvio) {
		return dao.getCatSeleccionadosCT(lob, proceso, tipoEnvio);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<String> guardar(String[] seleccionados, String lob, String proceso,String medioEnvio) {

		List<ExcluidosVO> existentes = dao.getSeleccionados(lob, proceso, medioEnvio);
		List<String> eliminar = new ArrayList<String>();
		List<String> insertar = new ArrayList<String>();

		if (seleccionados == null) {
			seleccionados = new String[0];
		}
			for (ExcluidosVO c : existentes) {
				boolean existe = false;
				String str = (new Integer(c.getIdRetenedor()).toString()) + "-" + (new Integer(c.getIdUnidadPago()).toString());
				for (String claveCampo : seleccionados) {
					if (str.equals(claveCampo)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					eliminar.add(str);
				}
			}

			for (String claveCampo : seleccionados) {
				boolean existe = false;
				for (ExcluidosVO c : existentes) {
					String str = (new Integer(c.getIdRetenedor()).toString()) + "-" + (new Integer(c.getIdUnidadPago()).toString());
					if (str.equals(claveCampo)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					insertar.add(claveCampo);
				}
			}
		
		for (String claveCampo : eliminar) {
			if (log.isDebugEnabled()) {
				log.debug("Eliminar " + claveCampo);
			}
			ExcluidosVO vo = new ExcluidosVO();
			String[] ids = StringUtils.split(claveCampo, "-");
			vo.setIdRetenedor(new Integer(ids[0]).intValue());
			vo.setIdUnidadPago(new Integer(ids[1]).intValue());
			vo.setLob(lob);
			vo.setProceso(proceso);
			vo.setMedioEnvio(medioEnvio);
			
			dao.eliminar(vo);
		}

		for (String claveCampo : insertar) {
			ExcluidosVO vo = new ExcluidosVO();
			String[] ids = StringUtils.split(claveCampo, "-");
			vo.setIdRetenedor(new Integer(ids[0]).intValue());
			vo.setIdUnidadPago(new Integer(ids[1]).intValue());
			vo.setLob(lob);
			vo.setProceso(proceso);
			vo.setMedioEnvio(medioEnvio);
			
			dao.insertar(vo);
		}

		return null;
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public List<String> guardarCt(String[] seleccionadosCt, String lob, String proceso,String medioEnvio) {

		List<ExcluidosVO> existentes = dao.getSeleccionadosCt(lob, proceso, medioEnvio);
		List<String> eliminar = new ArrayList<String>();
		List<String> insertar = new ArrayList<String>();

		if (seleccionadosCt == null) {
			seleccionadosCt = new String[0];
		}
			for (ExcluidosVO c : existentes) {
				boolean existe = false;
				String str = (new Integer(c.getIdRetenedor()).toString()) + "-" + (new Integer(c.getIdUnidadPago()).toString());
				for (String claveCampo : seleccionadosCt) {
					if (str.equals(claveCampo)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					eliminar.add(str);
				}
			}

			for (String claveCampo : seleccionadosCt) {
				boolean existe = false;
				for (ExcluidosVO c : existentes) {
					String str = (new Integer(c.getIdRetenedor()).toString()) + "-" + (new Integer(c.getIdUnidadPago()).toString());
					if (str.equals(claveCampo)) {
						existe = true;
						break;
					}
				}
				if (!existe) {
					insertar.add(claveCampo);
				}
			}
		
		for (String claveCampo : eliminar) {
			if (log.isDebugEnabled()) {
				log.debug("Eliminar " + claveCampo);
			}
			ExcluidosVO vo = new ExcluidosVO();
			String[] ids = StringUtils.split(claveCampo, "-");
			vo.setIdRetenedor(new Integer(ids[0]).intValue());
			vo.setIdUnidadPago(new Integer(ids[1]).intValue());
			vo.setLob(lob);
			vo.setProceso(proceso);
			vo.setMedioEnvio(medioEnvio);

			dao.eliminarCt(vo);
		}

		for (String claveCampo : insertar) {
			ExcluidosVO vo = new ExcluidosVO();
			String[] ids = StringUtils.split(claveCampo, "-");
			vo.setIdRetenedor(new Integer(ids[0]).intValue());
			vo.setIdUnidadPago(new Integer(ids[1]).intValue());
			vo.setLob(lob);
			vo.setProceso(proceso);
			vo.setMedioEnvio(medioEnvio);

			dao.insertarCt(vo);
		}

		return null;
	}
	
	

    public List<LabelValueBean> getUndadesPago(String retenedor,String proceso, String tipoEnvio) {
        return  dao.getUndadesPago(retenedor, proceso,tipoEnvio);
    }
    
    public List<LabelValueBean> getUndadesPagoCt(String retenedor,String proceso, String tipoEnvio) {
    	 return  dao.getUndadesPagoCt(retenedor,proceso,tipoEnvio);
	}
    
    
    public List<LabelValueBean> getUndadesPago(String proceso, String tipoEnvio) {
        return  dao.getUndadesPago(proceso,tipoEnvio);
    }
    
    public List<LabelValueBean> getUndadesPagoCt(String proceso, String tipoEnvio) {
    	 return  dao.getUndadesPagoCt(proceso,tipoEnvio);
	}

    public LabeValueBeanCascade getRetenedores(boolean isNumeric, String filter) {
        return dao.getRetenedores(isNumeric,filter);
    }

	

	


}
