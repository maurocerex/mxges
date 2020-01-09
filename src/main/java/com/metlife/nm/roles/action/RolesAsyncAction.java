package com.metlife.nm.roles.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.UserVO;
import com.metlife.nm.roles.service.RolesService;
import com.metlife.nm.roles.vo.PantallasVO;

public class RolesAsyncAction extends GenericAsyncAction {

	/**
     * 
     */
	private static final long serialVersionUID = 4746960012668055592L;
	private static final Logger log = Logger.getLogger(RolesAsyncAction.class);

	private String idRol;

	private String pantallasAsig;

	@Resource(name = BeanNames.RolesService)
	protected RolesService service;

	public String rolPantallas() {
		if (log.isDebugEnabled()) {
			log.debug("Pantallas asociadas a Roles...");
		}

		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = false;

		try {

			Map<Object, Object> resultSet = new HashMap<Object, Object>();
			List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();

			List<PantallasVO> rolPantallas = service.getPantallas(idRol);

			for (PantallasVO rolPant : rolPantallas) {
				Map<Object, Object> map = new HashMap<Object, Object>();
				map.put("idPantalla", rolPant.getIdPantalla());
				map.put("menu", rolPant.getMenu());
				map.put("pantalla", rolPant.getPantalla());
				map.put("descripcion", rolPant.getDescripcion());
				if (!rolPant.isEdoIni()) {
					map.put("asigPant", false);
					map.put("edoIni", false);
				} else {
					map.put("asigPant", true);
					map.put("edoIni", true);
				}
				mapResult.add(map);
			}

			resultSet.put("Result", mapResult);
			messages.put("ResultSet", resultSet);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			success = false;
			messages.put(JSON_EXCEPTION, e.toString());
		} finally {
			messages.put("JSON_DIVS", divs);
			messages.put("JSON_ALERTS", alerts);
			messages.put("JSON_SUCCESS", success);
		}

		streamIt(messages);

		return SUCCESS;
	}

	public String updatePantUsr() {

		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;

		try {

			List<PantallasVO> pantallas = parseJson(pantallasAsig);
			for (PantallasVO pant : pantallas) {
				log.debug(pant.toString());

				if (pant.isAsig() != pant.isEdoIni()) {
					service.updatePant(pant);
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

	@SuppressWarnings("rawtypes")
	private final List<PantallasVO> parseJson(String jsonString) throws RuntimeException {
		try {
			UserVO user = (UserVO) session.get(ConstantesMxges.USER);
			List<PantallasVO> registros = new ArrayList<PantallasVO>();

			if (jsonString != null) {
				JSONArray jsonArray = JSONArray.fromObject(jsonString);
				if (log.isDebugEnabled()) {
					log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
				}
				String pantallaId, rolPantalla;
				boolean asig, edoIni;
				PantallasVO tmp = null;
				Iterator it = jsonArray.iterator();
				while (it.hasNext()) {
					JSONObject jsonObject = (JSONObject) it.next();

					rolPantalla = idRol;
					pantallaId = parseJsonString(jsonObject, "pantalla");
					asig = parseJsonBoolean(jsonObject, "asigPant");
					edoIni = parseJsonBoolean(jsonObject, "edoIni");

					tmp = new PantallasVO();

					tmp.setRolId(rolPantalla);
					tmp.setIdPantalla(pantallaId);
					tmp.setAsig(asig);
					tmp.setEdoIni(edoIni);
					tmp.setUserName(user.getUsername());
					registros.add(tmp);

				}
			}

			return registros;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getIdRol() {
		return idRol;
	}

	public void setIdRol(String idRol) {
		this.idRol = idRol;
	}

	public String getPantallasAsig() {
		return pantallasAsig;
	}

	public void setPantallasAsig(String pantallasAsig) {
		this.pantallasAsig = pantallasAsig;
	}
}
