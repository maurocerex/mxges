package com.metlife.nm.utils.notificacion.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.MxgesProperties;
import com.metlife.nm.utils.notificacion.dao.EnviaNotificacionesDao;
import com.metlife.nm.utils.notificacion.vo.PolizaCliente;

import mx.com.metlife.schema.massiveprocess.massiveprocessws.TXLifeResponseType;
import mx.com.metlife.ws.client.HubComServiceMassProceClient;
import mx.com.metlife.ws.hubcomunicaciones.MassiveProcessResponse;

@Service("notifHubService")
public class NotifHubServiceImpl implements NotifHubService{

	private static final Logger log = Logger.getLogger(NotifHubServiceImpl.class);
	
	@Resource
	private HubComServiceMassProceClient serviceClient;
	
	@Resource
	private EnviaNotificacionesDao enviaNotificacionesDao;
	
	@Resource(name = BeanNames.MxgesProperties)
	private MxgesProperties mxgesProperties;
	
	
	
	public boolean invokeHubRenew(String procesoHUB) throws Exception {

	    String date = getDate();
	    String dateFormat= date.split(" ")[0];
	    List<PolizaCliente> polizaClienteList= enviaNotificacionesDao.getPolizasClientesHUB(dateFormat);
	    String payload= createPayload(polizaClienteList);
	    MassiveProcessResponse response= serviceClient.invokeMassiveProcess(procesoHUB + dateFormat, String.valueOf(payload.getBytes().length), payload, date );
	    TXLifeResponseType tXLifeResponse= response.getTXLifeResponse();
	     if(tXLifeResponse.getTransResult().getResultCode().getValue().equals("Success")){
	    	 System.out.println("records: "+  tXLifeResponse.getTransResult().getRecordsFound());
	    	 int ci = 0;
	    	 for(PolizaCliente datosCliente: polizaClienteList){
				 
				 log.info("DATOS DE LA POLIZA ----> " + ci + " :   " + datosCliente.getProceso() + " " + datosCliente.getRegistro() + " " + 
						 datosCliente.getCveProcesoOrigen() + " " + datosCliente.getCobertura_column5() + " " + datosCliente.getPoliza() + " "  + 
						 datosCliente.getFechaVigencia_column6() + " " + datosCliente.getEmail() + " " + datosCliente.getNombre() + " " + 
						 datosCliente.getApellidoPaterno() + " " + datosCliente.getApellidoMaterno());
				 enviaNotificacionesDao.updRegistrosRenew(datosCliente.getProceso(), datosCliente.getRegistro(), datosCliente.getCveProcesoOrigen());
	    	 }
	    	 return true;
	    	 
	     }
	     else{
	    	 System.out.println("No success: "+ tXLifeResponse.getTransResult().getResultCode().getValue());
	    	 return false;
	     }
	
    }
	
	
	private String removeAcentos(String input) {
	        //String original = "��������������u�������������������";
	        //String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	        String output = input;
	        
	   
	        /*input = input.replace("Á", "&Aacute;");
	        input = input.replace("É", "&Eacute;");
	        input = input.replace("Í", "&Iacute;");
	        input = input.replace("Ó", "&Oacute;");
	        input = input.replace("Ú", "&Uacute;");
	        input = input.replace("á", "&aacute;");
	        input = input.replace("é", "&eacute;");
	        input = input.replace("í", "&iacute;");
	        input = input.replace("ó", "&oacute;");
	        input = input.replace("ú", "&uacute;");*/
	        input = input.replace("Á", "A");
	        input = input.replace("É", "E");
	        input = input.replace("Í", "I");
	        input = input.replace("Ó", "O");
	        input = input.replace("Ú", "U");
	        input = input.replace("á", "a");
	        input = input.replace("é", "e");
	        input = input.replace("í", "i");
	        input = input.replace("ó", "o");
	        input = input.replace("ú", "u");
	        log.info("input text ------>>>:  " + input);
	        output = input;
	        log.info("output text ------>>>:  " + output);
	        return output;
	    }
	
	private String createPayload(List<PolizaCliente> polizaClienteList){
		 
		 String template= mxgesProperties.getTemplateHubCom();
		 StringBuilder result= new StringBuilder();
		 String coberturaHTML = "";
		 for(PolizaCliente datosCliente: polizaClienteList){
			 
			 log.info("DATOS DE LA POLIZA ---->:   " + datosCliente.getCobertura_column5() + " " + datosCliente.getPoliza() + " "  + 
					 datosCliente.getFechaVigencia_column6() + " " + datosCliente.getEmail() + " " + datosCliente.getNombre() + " " + 
					 datosCliente.getApellidoPaterno() + " " + datosCliente.getApellidoMaterno());
			 template = "";
			 log.info("Se limpia TEMPLATE ---->:   " + template);
			 template = mxgesProperties.getTemplateHubCom();
			 log.info("Se asigna TEMPLATE ---->:   " + template);
			 coberturaHTML = removeAcentos(datosCliente.getCobertura_column5());
			 
			//log.info(datosCliente.getNombre()+" "+datosCliente.getApellidoPaterno()+" "+datosCliente.getApellidoMaterno()+" "+datosCliente.getEmail()+" "+datosCliente.getFechaVigencia_column6()+" "+datosCliente.getPoliza());
				template= template.replace("<<1>>", coberturaHTML);  //COLUMN5
				template= template.replace("<<2>>", datosCliente.getPoliza());   // COLUMN6
				template= template.replace("<<3>>", getDateEs_MX_Formated(datosCliente.getFechaVigencia_column6()));  // ID_POLIZA
				template= template.replace("<<4>>", datosCliente.getEmail());  // EMAIL_CLIENTE
				 
			  result.append(template);	
			  result.append("\n");
			  log.info("NHSI template final -------->:   " + template);
			  log.info("NHSI result final -------->:   " + result);
		 }

		 return result.toString();
	 }
	 
	 
	 private String getDate(){
		 /**
		  * "2017-01-02");
		     ("10:10:11");
		  */
		 
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 String date = dateFormat.format(new Date());
		 System.out.println(date);
		 return date;

	 }
	 
	 /**
	  * in 01-01-2020
	  * out: 01 de Enero de 2020
	  * @return
	  */
	 public String getDateEs_MX_Formated(String dateIn){
			 
		 String pattern = "dd-MM-yyyy";
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		 String datefinal = null;
		 Date date= null;
		 
		try {
			date = simpleDateFormat.parse(dateIn);
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd 'de' MMMM 'de' yyyy", new Locale("es", "ES")); // es_MX
			datefinal = dateFormat.format(date);
			System.out.println(date);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return datefinal;
	 }
}
