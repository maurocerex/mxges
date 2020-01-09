package com.metlife.nm.administra.action;

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
import com.metlife.nm.mensajes.vo.MensajeVO;
import com.metlife.nm.menu.action.AyudaAsyncAction;
import com.metlife.nm.utils.notificacion.service.AdministraJobsService;
import com.metlife.nm.utils.notificacion.vo.JobVO;

public class AdministraAsyncAction extends GenericAsyncAction{


	private static final long serialVersionUID = 3929504770071248220L;
	
	private static final Logger log = Logger.getLogger(AyudaAsyncAction.class);

	 @Resource(name = BeanNames.AdministraJobsService)
	 private AdministraJobsService service;
	
	protected Map<String, Object> session;
	private String jsonrecords;
    
    
	public String getJsonrecords() {
		return jsonrecords;
	}


	public void setJsonrecords(String jsonrecords) {
		this.jsonrecords = jsonrecords;
	}


	public String listadoJobs() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("listadoJobs...");
            
        }
        JSONObject jasJobs = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;

        try {
            List<JobVO> jobs = service.getJobs();
        	//Datos de prueba//
            /*(JobVO dato= new JobVO();
            dato.setIdJob(123);
            dato.setWhat("PROCESO DE EJECUCI�N PARA CARGOS ACEPTADOS");
            dato.setHorario("16:30");
            dato.setBroken(false);
            jobs.add(dato);
            dato= new JobVO();
            dato.setIdJob(456);
            dato.setWhat("PROCESO DE EJECUCI�N PARA NO REINTENTABLES");
            dato.setHorario("10:00");
            dato.setBroken(false);
            jobs.add(dato);
            **/
            //Aqui se acaban los datos de prueba//
           
            Map<Object, Object> resultSet = new HashMap<Object, Object>();
            List<Map<Object, Object>> mapResult = new ArrayList<Map<Object, Object>>();
            String horario[];
            for (JobVO obj : jobs) {
                if (log.isDebugEnabled()) {
                    log.debug("obj => " + obj.toString());
                }
                Map<Object, Object> map = new HashMap<Object, Object>();
                map.put("idJob", obj.getIdJob());
                map.put("what", obj.getWhat());
                map.put("broken",obj.getBroken());
                horario=obj.getHorario().split(":");
                map.put("hora", horario[0]);
                map.put("minuto", horario[1]);
                mapResult.add(map);
            }
            resultSet.put("Result", mapResult);
            jasJobs.put("ResultSet", resultSet);
            jasJobs.put("ResultSet", resultSet);
           
            

        } catch (Exception e) {
            log.error("error", e);
            success = false;
            jasJobs.put(JSON_EXCEPTION, e.toString());
        } finally {
        	jasJobs.put(JSON_DIVS, divs);
        	jasJobs.put(JSON_ALERTS, alerts);
        	jasJobs.put(JSON_SUCCESS, success);
            if (log.isDebugEnabled()) {
                log.debug(jasJobs.toString(1));
            }
            streamIt(jasJobs);
        }
        return SUCCESS;
    }
	
	
	public String guardar() throws Exception {
        if (log.isInfoEnabled()) {
            log.info("guardarCambios...");
            log.info("Mensajes..." + jsonrecords);
        }

        JSONObject jasJobs = new JSONObject();
        JSONArray divs = new JSONArray();
        JSONArray alerts = new JSONArray();
        boolean success = true;
        try {

            if (log.isDebugEnabled()) {
                log.debug("jsonrecords: " + jsonrecords);
            }
            List<JobVO> registros = parseJson(jsonrecords);
            UserVO user = (UserVO) session.get(ConstantesMxges.USER);
            for (JobVO vo : registros) {
                log.debug("mensaje " + vo.toString());
            }
            service.guardarCambios(registros);

        } catch (Exception e) {
            log.error("error", e);
            success = false;
            jasJobs.put(JSON_EXCEPTION, e.toString());
        } finally {
        	jasJobs.put(JSON_DIVS, divs);
        	jasJobs.put(JSON_ALERTS, alerts);
        	jasJobs.put(JSON_SUCCESS, success);
            if (log.isDebugEnabled()) {
                log.debug(jasJobs.toString(1));
            }
            streamIt(jasJobs);
        }

        return SUCCESS;
    }
	
	
	@SuppressWarnings({ "unused", "rawtypes" })
    private List<JobVO> parseJson(final String jsonString) throws RuntimeException {
        try {

            List<JobVO> registros = new ArrayList<JobVO>();

            if (jsonString != null) {
                JSONArray jsonArray = JSONArray.fromObject(jsonString);
                if (log.isDebugEnabled()) {
                    log.debug("jsonArray.size() = > [ " + jsonArray.size() + " ]");
                }
               
               Integer idJob=null;
               String what=null;
               String hora=null;
               String minuto=null;
               String horario=null;
               String broken=null;
               

                JobVO tmp = null;
                Iterator it = jsonArray.iterator();
                while (it.hasNext()) {
                    JSONObject jsonObj = (JSONObject) it.next();

                    


                        idJob = Integer.valueOf(parseJsonString(jsonObj, "idJob"));
                        what = parseJsonString(jsonObj, "what");
                        hora= parseJsonString(jsonObj, "hora");
                        minuto=parseJsonString(jsonObj, "minuto");
                        horario=hora.concat(":").concat(minuto);
                        broken = parseJsonString(jsonObj, "broken");
                        
                        tmp = new JobVO();

                        tmp.setIdJob(idJob);
                        tmp.setWhat(what);
                        tmp.setBroken(broken);
                        tmp.setHorario(horario);
                        registros.add(tmp);
                    }

            }

            return registros;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	
	public void setSession(Map<String, Object> session) {
        this.session = session;
    }

    public final Map<String, Object> getSession() {
        return session;
    }


    

}
