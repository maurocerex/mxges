package com.metlife.nm.prioridad.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.prioridad.service.PrioridadService;
import com.metlife.nm.prioridad.vo.ProcesoVO;

public class PrioridadAsyncAction extends GenericAsyncAction {

	/**
     * 
     */
	private static final long serialVersionUID = 7386269035893820195L;
	private static final Logger log = Logger.getLogger(PrioridadAsyncAction.class);

	@Resource(name = "PrioridadService")
	private PrioridadService service;

	private String idlob;
	private String procesoAsig;

	public String execute() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("execute..." + idlob);
		}
		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;

		try {

			ArrayList<ProcesoVO> listaProcesos = (ArrayList<ProcesoVO>) service.getProcesosByIdLob(idlob);

			Map<Object, Object> resultSet = new HashMap<Object, Object>();
			List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();

			Map<Object, Object> map = null;

			for (ProcesoVO tmp : listaProcesos) {
				map = new HashMap<Object, Object>();

				map.put("lob", tmp.getLobDesc());
				map.put("idLob", tmp.getIdLob());
				map.put("proceso", tmp.getProcesoDesc());
				map.put("idProceso", tmp.getIdProceso());
				map.put("prioridad", tmp.getPrioridad());
				map.put("notifica", tmp.isNotifica());
				if (tmp.isNotifica()) {
					map.put("edoIni", true);
				} else {
					map.put("edoIni", false);
				}
				map.put("nuevo", false);
				map.put("modificado", false);
				map.put("eliminado", false);

				mapResult.add(map);
			}

			resultSet.put("Result", mapResult);
			messages.put("ResultSet", resultSet);

		} catch (Exception e) {
			log.error("error", e);
			success = false;
			messages.put(JSON_EXCEPTION, e.toString());
		} finally {
			messages.put(JSON_DIVS, divs);
			messages.put(JSON_ALERTS, alerts);
			messages.put(JSON_SUCCESS, success);
			if (log.isDebugEnabled()) {
				log.debug(messages.toString(1));
			}
			streamIt(messages);
		}
		return SUCCESS;
	}

	public String updPrioridad() {
		log.debug("updPrioridad " + procesoAsig);
		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;
		boolean repetido = false;
		try {

			List<ProcesoVO> procesos = parseJson(procesoAsig);

			HashSet<Integer> set = new HashSet<Integer>();
			for (ProcesoVO tmp : procesos) {
				Integer prio = new Integer(tmp.getPrioridad());
				if (set.contains(prio)) {
					messages.put("proceso", "No pueden haber prioridades repetidas");
					success = false;
					break;
				}
				set.add(prio);
			}

			if (success) {
				for (ProcesoVO tmp : procesos) {
					log.debug(tmp.toString());
					// if (tmp.isNotifica() != tmp.isEdoIni()) {
					service.updatePant(tmp);
					// }
				}
			}
		} catch (Exception e) {

			log.error("error", e);
			success = false;
			messages.put(JSON_EXCEPTION, e.toString());

		} finally {

			messages.put(JSON_DIVS, divs);
			messages.put(JSON_ALERTS, alerts);
			messages.put(JSON_SUCCESS, success);

		}

		streamIt(messages);
		return SUCCESS;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private final List<ProcesoVO> parseJson(String jsonString) throws RuntimeException {
		try {
			UserVO user = (UserVO) session.get(ConstantesMxges.USER);
			List<ProcesoVO> registros = new ArrayList<ProcesoVO>();

			if (jsonString != null) {
				JSONArray jsonArray = JSONArray.fromObject(jsonString);
				if (log.isDebugEnabled()) {
					log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
				}

				String proceso;
				String lob;
				int prioridad;
				boolean notifica;
				String idLob;
				boolean asig, edoIni;
				ProcesoVO tmp = null;
				Iterator it = jsonArray.iterator();
				while (it.hasNext()) {
					JSONObject jsonObject = (JSONObject) it.next();

					idLob = idlob;
					proceso = parseJsonString(jsonObject, "idProceso");
					lob = parseJsonString(jsonObject, "lob");
					prioridad = parseJsonInt(jsonObject, "prioridad");
					notifica = parseJsonBoolean(jsonObject, "notifica");

					tmp = new ProcesoVO();
					tmp.setIdLob(idLob);
					tmp.setIdProceso(proceso);
					tmp.setPrioridad(prioridad);
					tmp.setNotifica(notifica);

					registros.add(tmp);
				}
			}

			return registros;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getIdlob() {
		return idlob;
	}

	public void setIdlob(String idlob) {
		this.idlob = idlob;
	}

	public final String getProcesoAsig() {
		return procesoAsig;
	}

	public final void setProcesoAsig(String procesoAsig) {
		this.procesoAsig = procesoAsig;
	}

}
