package com.metlife.nm.cifras.action;

import static org.apache.commons.lang.StringUtils.isBlank;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.metlife.nm.cifras.service.CifrasService;
import com.metlife.nm.cifras.vo.CifrasVO;
import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.ConstantesMxges;
import com.metlife.nm.domain.GenericAsyncAction;
import com.metlife.nm.domain.UserVO;

public class CifrasAsyncAction extends GenericAsyncAction {

	/**
     * 
     */
	private static final long serialVersionUID = 6650831983911276596L;
	private static final Logger log = Logger.getLogger(CifrasAsyncAction.class);
	private static final int MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;

	private String jsonrecords;
	private String proceso;
	private String medio;
	private String fecInicio;
	private String fecFin;
	private String tipoReporte;
	
	@Resource(name = BeanNames.CifrasService)
	protected CifrasService service;

	
	public String execute() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Method execute with the values:");
			log.debug("Proceso: "+ proceso +", "
					+ "Medio: " + medio +", "
					+ "Fecha Inicio:" + fecInicio +", "
					+ "Fecha Fin" + fecFin +", "
					+ "Tipo Reporte: " + tipoReporte);
		}
		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;

		try {

			Map<Object, Object> resultSet = new HashMap<Object, Object>();
			List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();
			Map<Object, Object> map = null;
			
			List<CifrasVO> dataConsult = new ArrayList<CifrasVO>();
			log.debug("Se va a actualizar primero las cifras de control con espacios vacios");
			int updateCifrasC=service.updateCC(tipoReporte);
			log.debug("actualizo: " + updateCifrasC + " notificaciones para cifras de control");
			
			dataConsult = service.getCifras(proceso, medio, fecInicio, fecFin, tipoReporte);
			
			for (CifrasVO tmp : dataConsult) {
				map = new HashMap<Object, Object>();

				map.put("lob", tmp.getLobDesc());
				map.put("idLob", tmp.getIdLob());
				map.put("proceso", tmp.getProcesoDesc());
				map.put("idProceso", tmp.getIdProceso());
				map.put("medioEnvio", tmp.getMedioEnvio());
				map.put("status", tmp.getStatusEnvio());
				map.put("noRegistros", tmp.getNoRegistros());
				map.put("porcentaje", tmp.getPorcentaje());
				map.put("totalEnviados", tmp.getTotalEnviados());
				
				if(!tmp.getPorcentaje().equals("100") && !tmp.getPorcentaje().equals("NA")){
					map.put("reenvio", true);
				}else{
					map.put("reenvio", false);
				}
				map.put("modificado", false);

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
				log.debug("Exit Execute");
			}
			streamIt(messages);
		}
		return SUCCESS;
	}

	static final String DIV_FECHAS = "div.fecha.inicio.mayor";
	static final String DIV_FECHA_DIAS = "div.fecha.dias";
	static final String DIV_FECHA_INICIO = "div.fecha.inicio";
	static final String DIV_FECHA_FIN = "div.fecha.fin";
	static final String DIV_MEDIO = "div.medio";
    static final String DIV_PROCESO = "div.proceso";
    
	public String busqueda() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("execute... Boton buscar");
		}
		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;
		JSONArray records = new JSONArray();
		String msgError = null;
		try {

			JSONObject map = new JSONObject();
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Calendar inicio = Calendar.getInstance();
			Calendar fin = Calendar.getInstance();
			Calendar hoy = Calendar.getInstance();
			
			/*boolean errormedio = false;
			msgError = "";
            if (isBlank(medio)) {
                errormedio = true;
                msgError = this.getText("cifras.medio.blank");
            }
            if (errormedio) {
                success = false;
            }

            divs.add(getDivYMensaje(DIV_MEDIO, msgError));
            
            boolean errorproceso = false;
            msgError = "";
            if (isBlank(proceso)) {
                errorproceso = true;
                msgError = this.getText("cifras.proceso.blank");
            }
            if (errorproceso) {
                success = false;
            }

            divs.add(getDivYMensaje(DIV_PROCESO, msgError));*/
            
            boolean errorFechaIni = false;
			msgError = "";
			if (isBlank(fecInicio)) {
				errorFechaIni = true;
				msgError = this.getText("cifras.fechas.inicio.blank");
			}
			if (errorFechaIni) {
				success = false;
			}

			divs.add(getDivYMensaje(DIV_FECHA_INICIO, msgError));

			boolean errorFechaFin = false;

			msgError = "";
			if (isBlank(fecFin)) {
				errorFechaFin = true;
				msgError = this.getText("cifras.fechas.fin.blank");
			}
			if (errorFechaFin) {
				success = false;
			}

			divs.add(getDivYMensaje(DIV_FECHA_FIN, msgError));

			if (!isBlank(fecInicio) && !isBlank(fecFin)) {
				inicio.setTime(format.parse(fecInicio));
				fin.setTime(format.parse(fecFin));
				
				long startTime = inicio.getTimeInMillis();
				long endTime = fin.getTimeInMillis();

				long dias = (endTime - startTime) / MILLISECONDS_IN_DAY;

				if (log.isDebugEnabled()) {
					log.debug("Dias de Diferencia -> " + dias);
				}
				boolean errorFechas = false;

				msgError = "";
				if (inicio.after(fin)) {
					errorFechas = true;
					msgError = this.getText("cifras.fechas.inicio.mayor.fin");
				}
				if (errorFechas) {
					success = false;
				}

				divs.add(getDivYMensaje(DIV_FECHAS, msgError));

				boolean errorDias = false;

				int vigencia = service.getVigencia();
				msgError = "";
				if (dias > vigencia) {
					errorDias = true;
					msgError = this.getText("cifras.dias.excedente");
				}
				if (hoy.before(fin) ||hoy.before(inicio)) {
                    errorDias = true;
                    msgError = this.getText("cifras.dias.actual");
                }
				if (errorDias) {
					success = false;
				}

				divs.add(getDivYMensaje(DIV_FECHA_DIAS, msgError));
			}
			if (success) {
				
				log.debug("Se va a actualizar primero las cifras de control con espacios vacios");
				int updateCifrasC=service.updateCC(tipoReporte);
				log.debug("actualizo: " + updateCifrasC + " notificaciones para cifras de control");
				
				for (CifrasVO tmp : service.getCifras(proceso, medio, fecInicio, fecFin, tipoReporte)) {

					if (log.isDebugEnabled()) {
						log.debug("Cifra -> " + tmp.toString());
					}
					map = new JSONObject();

					map.put("lob", tmp.getLobDesc());
					map.put("idLob", tmp.getIdLob());
					map.put("proceso", tmp.getProcesoDesc());
					map.put("idProceso", tmp.getIdProceso());
					map.put("medioEnvio", tmp.getMedioEnvio());
					map.put("status", tmp.getStatusEnvio());
					map.put("noRegistros", tmp.getNoRegistros());
					map.put("porcentaje", tmp.getPorcentaje());
					if(!tmp.getPorcentaje().equals("100")){
					    map.put("reenvio", true);
					}else{
					    map.put("reenvio", false);
					}
					map.put("modificado", false);
					records.add(map);

				}
				messages.put("rows", records);
			}

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

	public String detalleRegistro() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("detalle...");
		}
		JSONObject messages = new JSONObject();
		JSONArray divs = new JSONArray();
		JSONArray alerts = new JSONArray();
		boolean success = true;
		try {

			Map<Object, Object> map = null;
			Map<Object, Object> resultSet = new HashMap<Object, Object>();
			List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();
			List<Map<String,Object>> allLines = service.getReporte(proceso, medio, fecInicio, fecFin,tipoReporte);
			
			
			for (Map<String, Object> line : allLines){
				map = new HashMap<Object, Object>();
				map.put("nmcliente", line.values().toString().split(",")[2]);
				map.put("tpMensaje", line.values().toString().split(",")[20]);
				map.put("proceso", line.values().toString().split(",")[21]);
				map.put("tel", line.values().toString().split(",")[7]);
				map.put("email", line.values().toString().split(",")[10]);
				map.put("nopoliza", line.values().toString().split(",")[1]);
				map.put("refbancaria", line.values().toString().split(",")[13]);
				map.put("montorecibido", line.values().toString().split(",")[24]);
				map.put("recibosPend", line.values().toString().split(",")[25]);
				map.put("cvepromotoria", line.values().toString().split(",")[14]);
				map.put("cveagente", line.values().toString().split(",")[15]);
				map.put("retenedor", line.values().toString().split(",")[16]);
				map.put("unpago", line.values().toString().split(",")[17]);
				map.put("cntrotrabjo", line.values().toString().split(",")[19]);
				map.put("status", line.values().toString().split(",")[23]);
				map.put("detalleStatus", line.values().toString().split(",")[26]);
				map.put("lob", line.values().toString().split(",")[0]);
				map.put("detalleNotificacion", line.values().toString().split(",")[22]);
				
				mapResult.add(map);

			}
			if (log.isDebugEnabled()) {
				log.debug("imprime el numero de registros: " + mapResult.size());
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
				log.debug("Exit Detalle");
			}
			streamIt(messages);
		}
		return SUCCESS;
	}

	public String guardar() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("guardarCambios...");
            log.info("Mensajes..." + jsonrecords);
        }

        JSONObject messages = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        try {

            if (log.isDebugEnabled()) {
                log.debug("jsonrecords: " + jsonrecords);
            }
            List<CifrasVO> registros = parseJson(jsonrecords);
            UserVO user = (UserVO) session.get(ConstantesMxges.USER);

            service.guardar(registros);

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
	
	private List<CifrasVO> parseJson(final String jsonString) throws RuntimeException {
        try {

            List<CifrasVO> registros = new ArrayList<CifrasVO>();

            if (jsonString != null) {
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                if (log.isDebugEnabled()) {
                    log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
                }
                boolean modificado = false;
               
                
                String idProceso = null;
                String medio = null;
                String fecInicio = null;
                String fecFin = null;

                CifrasVO tmp = null;
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject jsonObj = (JSONObject) it.next();

                    // Es obligatorio que las banderas representen exactamente
                    // un valor boolean.
                    modificado = parseJsonBoolean(jsonObj, "modificado");

                    if (modificado) {

                        idProceso = parseJsonString(jsonObj, "idProceso");
                        medio = parseJsonString(jsonObj, "medioEnvio");
                        fecInicio = this.fecInicio;
                        fecFin = this.fecFin;
                        

                        tmp = new CifrasVO();

                        tmp.setIdProceso(idProceso);
                        tmp.setMedioEnvio(medio);
                        tmp.setFechaIni(fecInicio);
                        tmp.setFecFin(fecFin);

                        tmp.setModificado(modificado);

                        registros.add(tmp);
                    }

                }
            }

            return registros;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getMedio() {
		return medio;
	}

	public void setMedio(String medio) {
		this.medio = medio;
	}

	public String getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(String fecInicio) {
		this.fecInicio = fecInicio;
	}

	public String getFecFin() {
		return fecFin;
	}

	public void setFecFin(String fecFin) {
		this.fecFin = fecFin;
	}

    public final String getJsonrecords() {
        return jsonrecords;
    }

    public final void setJsonrecords(String jsonrecords) {
        this.jsonrecords = jsonrecords;
    }

    public String getTipoReporte() {
		return tipoReporte;
	}

	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
}
