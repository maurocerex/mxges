//
// Generated By:JAX-WS RI 2.2.4-b01 (JAXB RI IBM 2.2.4-2)
//


package mx.com.metlife.schema.select.selectws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OLifE_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OLifE_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Field" type="{http://www.metlife.com.mx/schema/Select/SelectWS}Field_Type"/>
 *         &lt;element name="QueryDate" type="{http://www.metlife.com.mx/schema/Select/SelectWS}QueryDate_Type" minOccurs="0"/>
 *         &lt;element name="LegacyID" type="{http://www.metlife.com.mx/schema/Select/SelectWS}LegacyID_Type"/>
 *         &lt;element name="FiledID" type="{http://www.metlife.com.mx/schema/Select/SelectWS}FiledID_Type" minOccurs="0"/>
 *         &lt;element name="RecordID" type="{http://www.metlife.com.mx/schema/Select/SelectWS}RecordID_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OLifE_Type", propOrder = {
    "field",
    "queryDate",
    "legacyID",
    "filedID",
    "recordID"
})
public class OLifEType {

    @XmlElement(name = "Field", required = true)
    protected String field;
    @XmlElement(name = "QueryDate")
    protected String queryDate;
    @XmlElement(name = "LegacyID", required = true)
    protected String legacyID;
    @XmlElement(name = "FiledID")
    protected String filedID;
    @XmlElement(name = "RecordID")
    protected String recordID;

    /**
     * Gets the value of the field property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getField() {
        return field;
    }

    /**
     * Sets the value of the field property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setField(String value) {
        this.field = value;
    }

    /**
     * Gets the value of the queryDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryDate() {
        return queryDate;
    }

    /**
     * Sets the value of the queryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryDate(String value) {
        this.queryDate = value;
    }

    /**
     * Gets the value of the legacyID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegacyID() {
        return legacyID;
    }

    /**
     * Sets the value of the legacyID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegacyID(String value) {
        this.legacyID = value;
    }

    /**
     * Gets the value of the filedID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiledID() {
        return filedID;
    }

    /**
     * Sets the value of the filedID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiledID(String value) {
        this.filedID = value;
    }

    /**
     * Gets the value of the recordID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordID() {
        return recordID;
    }

    /**
     * Sets the value of the recordID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordID(String value) {
        this.recordID = value;
    }

}