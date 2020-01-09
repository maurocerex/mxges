//
// Generated By:JAX-WS RI 2.2.4-b01 (JAXB RI IBM 2.2.4-2)
//


package mx.com.metlife.ws.hubcomunicaciones;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.com.metlife.ws.hubcomunicaciones package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MassiveProcessResponse_QNAME = new QName("http://www.metlife.com.mx/ws/HubComunicaciones", "massiveProcessResponse");
    private final static QName _SelectRequest_QNAME = new QName("http://www.metlife.com.mx/ws/HubComunicaciones", "selectRequest");
    private final static QName _SendMailResponse_QNAME = new QName("http://www.metlife.com.mx/ws/HubComunicaciones", "sendMailResponse");
    private final static QName _SendMailRequest_QNAME = new QName("http://www.metlife.com.mx/ws/HubComunicaciones", "sendMailRequest");
    private final static QName _MassiveProcessRequest_QNAME = new QName("http://www.metlife.com.mx/ws/HubComunicaciones", "massiveProcessRequest");
    private final static QName _SelectResponse_QNAME = new QName("http://www.metlife.com.mx/ws/HubComunicaciones", "selectResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.metlife.ws.hubcomunicaciones
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MassiveProcessResponse }
     * 
     */
    public MassiveProcessResponse createMassiveProcessResponse() {
        return new MassiveProcessResponse();
    }

    /**
     * Create an instance of {@link SendMailRequest }
     * 
     */
    public SendMailRequest createSendMailRequest() {
        return new SendMailRequest();
    }

    /**
     * Create an instance of {@link SendMailResponse }
     * 
     */
    public SendMailResponse createSendMailResponse() {
        return new SendMailResponse();
    }

    /**
     * Create an instance of {@link SelectResponse }
     * 
     */
    public SelectResponse createSelectResponse() {
        return new SelectResponse();
    }

    /**
     * Create an instance of {@link SelectRequest }
     * 
     */
    public SelectRequest createSelectRequest() {
        return new SelectRequest();
    }

    /**
     * Create an instance of {@link MassiveProcessRequest }
     * 
     */
    public MassiveProcessRequest createMassiveProcessRequest() {
        return new MassiveProcessRequest();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MassiveProcessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.metlife.com.mx/ws/HubComunicaciones", name = "massiveProcessResponse")
    public JAXBElement<MassiveProcessResponse> createMassiveProcessResponse(MassiveProcessResponse value) {
        return new JAXBElement<MassiveProcessResponse>(_MassiveProcessResponse_QNAME, MassiveProcessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.metlife.com.mx/ws/HubComunicaciones", name = "selectRequest")
    public JAXBElement<SelectRequest> createSelectRequest(SelectRequest value) {
        return new JAXBElement<SelectRequest>(_SelectRequest_QNAME, SelectRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMailResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.metlife.com.mx/ws/HubComunicaciones", name = "sendMailResponse")
    public JAXBElement<SendMailResponse> createSendMailResponse(SendMailResponse value) {
        return new JAXBElement<SendMailResponse>(_SendMailResponse_QNAME, SendMailResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendMailRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.metlife.com.mx/ws/HubComunicaciones", name = "sendMailRequest")
    public JAXBElement<SendMailRequest> createSendMailRequest(SendMailRequest value) {
        return new JAXBElement<SendMailRequest>(_SendMailRequest_QNAME, SendMailRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MassiveProcessRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.metlife.com.mx/ws/HubComunicaciones", name = "massiveProcessRequest")
    public JAXBElement<MassiveProcessRequest> createMassiveProcessRequest(MassiveProcessRequest value) {
        return new JAXBElement<MassiveProcessRequest>(_MassiveProcessRequest_QNAME, MassiveProcessRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SelectResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.metlife.com.mx/ws/HubComunicaciones", name = "selectResponse")
    public JAXBElement<SelectResponse> createSelectResponse(SelectResponse value) {
        return new JAXBElement<SelectResponse>(_SelectResponse_QNAME, SelectResponse.class, null, value);
    }

}