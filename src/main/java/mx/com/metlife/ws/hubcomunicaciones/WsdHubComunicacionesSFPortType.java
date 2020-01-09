//
// Generated By:JAX-WS RI 2.2.4-b01 (JAXB RI IBM 2.2.4-2)
//


package mx.com.metlife.ws.hubcomunicaciones;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

@WebService(name = "wsdHubComunicacionesSF_PortType", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    mx.com.metlife.schema.massiveprocess.massiveprocessws.ObjectFactory.class,
    mx.com.metlife.schema.select.selectws.ObjectFactory.class,
    mx.com.metlife.schema.sendmail.sendmailws.ObjectFactory.class,
    mx.com.metlife.ws.hubcomunicaciones.ObjectFactory.class
})
public interface WsdHubComunicacionesSFPortType {


    /**
     * 
     * @param parameters
     * @return
     *     returns mx.com.metlife.ws.hubcomunicaciones.MassiveProcessResponse
     */
    @WebMethod(action = "MxHUBCOM_WSD_wsdHubComunicacionesSF_Binder_massiveProcess")
    @WebResult(name = "massiveProcessResponse", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones", partName = "parameters")
    public MassiveProcessResponse massiveProcess(
        @WebParam(name = "massiveProcessRequest", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones", partName = "parameters")
        MassiveProcessRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns mx.com.metlife.ws.hubcomunicaciones.SelectResponse
     */
    @WebMethod(action = "MxHUBCOM_WSD_wsdHubComunicacionesSF_Binder_select")
    @WebResult(name = "selectResponse", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones", partName = "parameters")
    public SelectResponse select(
        @WebParam(name = "selectRequest", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones", partName = "parameters")
        SelectRequest parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns mx.com.metlife.ws.hubcomunicaciones.SendMailResponse
     */
    @WebMethod(action = "MxHUBCOM_WSD_wsdHubComunicacionesSF_Binder_sendMail")
    @WebResult(name = "sendMailResponse", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones", partName = "parameters")
    public SendMailResponse sendMail(
        @WebParam(name = "sendMailRequest", targetNamespace = "http://www.metlife.com.mx/ws/HubComunicaciones", partName = "parameters")
        SendMailRequest parameters);

}
