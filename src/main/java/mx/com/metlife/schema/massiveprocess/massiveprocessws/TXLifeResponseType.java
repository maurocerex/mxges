//
// Generated By:JAX-WS RI 2.2.4-b01 (JAXB RI IBM 2.2.4-2)
//


package mx.com.metlife.schema.massiveprocess.massiveprocessws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TXLifeResponse_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TXLifeResponse_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransRefGUID" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}TransRefGUID_Type"/>
 *         &lt;element name="TransType" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}TransType_Type"/>
 *         &lt;element name="TransExeDate" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}TransExeDate_Type"/>
 *         &lt;element name="TransExeTime" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}TransExeTime_Type"/>
 *         &lt;element name="TransResult" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}TransResult_Type"/>
 *         &lt;element name="OLifE" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}OLifEResponse_Type"/>
 *         &lt;element name="OLifEExtension" type="{http://www.metlife.com.mx/schema/MassiveProcess/MassiveProcessWS}OLifEExtensionResponse_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TXLifeResponse_Type", propOrder = {
    "transRefGUID",
    "transType",
    "transExeDate",
    "transExeTime",
    "transResult",
    "oLifE",
    "oLifEExtension"
})
public class TXLifeResponseType {

    @XmlElement(name = "TransRefGUID", required = true)
    protected String transRefGUID;
    @XmlElement(name = "TransType", required = true)
    protected TransTypeType transType;
    @XmlElement(name = "TransExeDate", required = true)
    protected String transExeDate;
    @XmlElement(name = "TransExeTime", required = true)
    protected String transExeTime;
    @XmlElement(name = "TransResult", required = true)
    protected TransResultType transResult;
    @XmlElement(name = "OLifE", required = true)
    protected OLifEResponseType oLifE;
    @XmlElement(name = "OLifEExtension", required = true)
    protected OLifEExtensionResponseType oLifEExtension;

    /**
     * Gets the value of the transRefGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransRefGUID() {
        return transRefGUID;
    }

    /**
     * Sets the value of the transRefGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransRefGUID(String value) {
        this.transRefGUID = value;
    }

    /**
     * Gets the value of the transType property.
     * 
     * @return
     *     possible object is
     *     {@link TransTypeType }
     *     
     */
    public TransTypeType getTransType() {
        return transType;
    }

    /**
     * Sets the value of the transType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransTypeType }
     *     
     */
    public void setTransType(TransTypeType value) {
        this.transType = value;
    }

    /**
     * Gets the value of the transExeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransExeDate() {
        return transExeDate;
    }

    /**
     * Sets the value of the transExeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransExeDate(String value) {
        this.transExeDate = value;
    }

    /**
     * Gets the value of the transExeTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransExeTime() {
        return transExeTime;
    }

    /**
     * Sets the value of the transExeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransExeTime(String value) {
        this.transExeTime = value;
    }

    /**
     * Gets the value of the transResult property.
     * 
     * @return
     *     possible object is
     *     {@link TransResultType }
     *     
     */
    public TransResultType getTransResult() {
        return transResult;
    }

    /**
     * Sets the value of the transResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransResultType }
     *     
     */
    public void setTransResult(TransResultType value) {
        this.transResult = value;
    }

    /**
     * Gets the value of the oLifE property.
     * 
     * @return
     *     possible object is
     *     {@link OLifEResponseType }
     *     
     */
    public OLifEResponseType getOLifE() {
        return oLifE;
    }

    /**
     * Sets the value of the oLifE property.
     * 
     * @param value
     *     allowed object is
     *     {@link OLifEResponseType }
     *     
     */
    public void setOLifE(OLifEResponseType value) {
        this.oLifE = value;
    }

    /**
     * Gets the value of the oLifEExtension property.
     * 
     * @return
     *     possible object is
     *     {@link OLifEExtensionResponseType }
     *     
     */
    public OLifEExtensionResponseType getOLifEExtension() {
        return oLifEExtension;
    }

    /**
     * Sets the value of the oLifEExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link OLifEExtensionResponseType }
     *     
     */
    public void setOLifEExtension(OLifEExtensionResponseType value) {
        this.oLifEExtension = value;
    }

}
