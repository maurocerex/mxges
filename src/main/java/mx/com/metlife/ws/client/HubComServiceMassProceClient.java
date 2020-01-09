package mx.com.metlife.ws.client;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.metlife.nm.domain.BeanNames;
import com.metlife.nm.domain.MxgesProperties;
import com.metlife.nm.utils.notificacion.service.NotifHubServiceImpl;

import mx.com.metlife.schema.massiveprocess.massiveprocessws.OLifEExtensionType;
import mx.com.metlife.schema.massiveprocess.massiveprocessws.OLifEType;
import mx.com.metlife.schema.massiveprocess.massiveprocessws.TXLifeRequestType;
import mx.com.metlife.schema.massiveprocess.massiveprocessws.TestIndicatorType;
import mx.com.metlife.schema.massiveprocess.massiveprocessws.TransTypeType;
import mx.com.metlife.ws.hubcomunicaciones.MassiveProcessRequest;
import mx.com.metlife.ws.hubcomunicaciones.MassiveProcessResponse;
import mx.com.metlife.ws.hubcomunicaciones.MxHUBCOMWSDWsdHubComunicacionesSF;
import mx.com.metlife.ws.hubcomunicaciones.WsdHubComunicacionesSFPortType;

@Service(value = BeanNames.HubComServiceMassProceClient)
public class HubComServiceMassProceClient {
	
	
	private static final Logger log = Logger.getLogger(HubComServiceMassProceClient.class);
	
	@Resource(name = BeanNames.MxgesProperties)
	private MxgesProperties mxgesProperties;
	

	
	public MassiveProcessResponse invokeMassiveProcess(String file,  String filesize, String payload, String currentDate) throws Exception{
		
		log.info("HCSMPC  Parametros__entrada --------->    " );
		log.info("HCSMPC  file --------->    "  + file);
		log.info("HCSMPC  filesize --------->    "  + filesize);
		log.info("HCSMPC  payload --------->    "  + payload);
		log.info("HCSMPC  currentDate --------->    "  + currentDate);
		String dateProc = currentDate.split(" ")[0];
		String timeProc = currentDate.split(" ")[1];
		
		MassiveProcessRequest  request = new MassiveProcessRequest();
		MassiveProcessResponse response= null;
		TXLifeRequestType TXLifeRequest = new TXLifeRequestType();
		
		OLifEType OLifE = new OLifEType();
		OLifE.setBaseFile(payload.getBytes());	
		OLifE.setFileExtension(mxgesProperties.getFileExtension());
		OLifE.setFileName(file);
		OLifE.setFileSize(filesize);
		log.info("HCSMPC  OLifE.getBaseFile() --------->    "  + OLifE.getBaseFile());
		log.info("HCSMPC  OLifE.getFileExtension() --------->    "  + OLifE.getFileExtension());
		log.info("HCSMPC  OLifE.getFileName() --------->    "  + OLifE.getFileName());
		log.info("HCSMPC  OLifE.getFileSize() --------->    "  + OLifE.getFileSize());
		
		
		OLifEExtensionType OLifEExtension = new OLifEExtensionType();
		OLifEExtension.setSrceCompany(mxgesProperties.getOlifeSourceCompany());
		OLifEExtension.setSrceCountry(mxgesProperties.getOlifeSourceCountry());
		OLifEExtension.setSrceSystem(mxgesProperties.getOlifeSourceSystem());
		log.info("HCSMPC  OLifEExtension.getSrceCompany() --------->    "  + OLifEExtension.getSrceCompany());
		log.info("HCSMPC  OLifEExtension.getSrceCountry() --------->    "  + OLifEExtension.getSrceCountry());
		log.info("HCSMPC  OLifEExtension.getSrceSystem() --------->    "  + OLifEExtension.getSrceSystem());
		
		TestIndicatorType testIndicator = new TestIndicatorType();
		testIndicator.setValue(mxgesProperties.getTestIndicatorValue());
		testIndicator.setTc(mxgesProperties.getTestIndicator());
		log.info("HCSMPC  testIndicator.getValue() --------->    "  + testIndicator.getValue());
		log.info("HCSMPC  testIndicator.getTc() --------->    "  + testIndicator.getTc());
		
		TransTypeType transType = new TransTypeType();
		transType.setValue(mxgesProperties.getTranstypeValue());
		transType.setTc(mxgesProperties.getTranstypeTC());
		log.info("HCSMPC  transType.getValue() --------->    "  + transType.getValue());
		log.info("HCSMPC  transType.getTc() --------->    "  + transType.getTc());
			
		TXLifeRequest.setOLifE(OLifE);
		TXLifeRequest.setOLifEExtension(OLifEExtension);
		TXLifeRequest.setTestIndicator(testIndicator);
		TXLifeRequest.setTransExeDate(dateProc);
		TXLifeRequest.setTransExeTime(timeProc);
		TXLifeRequest.setTransRefGUID(mxgesProperties.getTransRefGUID());
		log.info("HCSMPC  TXLifeRequest.getTransExeDate() --------->    "  + TXLifeRequest.getTransExeDate());
		log.info("HCSMPC  TXLifeRequest.getTransExeTime() --------->    "  + TXLifeRequest.getTransExeTime());
		log.info("HCSMPC  TXLifeRequest.getTransRefGUID() --------->    "  + TXLifeRequest.getTransRefGUID());
		TXLifeRequest.setTransType(transType);
		request.setTXLifeRequest(TXLifeRequest);
		try{
		
			response= massiveProcess(request);
		}
		catch(Exception ex){
			throw ex;
		}
		
		
		if(response != null){

		     return response;
		     
		}else{
			throw new RuntimeException("Respuesta nula en servicio web MxHUBCOMWSDWsdHubComunicacionesSF ");
		}
		
	}
	
	
	
	public MassiveProcessResponse massiveProcess(MassiveProcessRequest request){
		
		try {
	
			MassiveProcessResponse response = new MassiveProcessResponse();
            WsdHubComunicacionesSFPortType service = new MxHUBCOMWSDWsdHubComunicacionesSF().getPortType(mxgesProperties.getEndpointHubComServiceMass());

			response = service.massiveProcess(request);
            return response;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("Exception en servicio web MxHUBCOMWSDWsdHubComunicacionesSF : "+ e.getMessage());
		}
		catch(Throwable tr){
			System.out.println("respuesta mal formada");
			tr.printStackTrace();
			throw new RuntimeException("Throwable en servicio web MxHUBCOMWSDWsdHubComunicacionesSF : "+ tr.getMessage());
		}
		
	}
	
	

	public MxgesProperties getMxgesProperties() {
		return mxgesProperties;
	}



	public void setMxgesProperties(MxgesProperties mxgesProperties) {
		this.mxgesProperties = mxgesProperties;
	}



}
