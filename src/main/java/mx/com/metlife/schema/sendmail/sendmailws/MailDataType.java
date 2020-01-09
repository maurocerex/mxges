//
// Generated By:JAX-WS RI 2.2.4-b01 (JAXB RI IBM 2.2.4-2)
//


package mx.com.metlife.schema.sendmail.sendmailws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MailData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MailData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FieldKeys" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}FieldKeys_Type"/>
 *         &lt;element name="ContentType" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}ContentType_Type"/>
 *         &lt;element name="ImageKeys" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}ImageKeys_Type"/>
 *         &lt;element name="BCCs" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}BCCs_Type" minOccurs="0"/>
 *         &lt;element name="CCs" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}CCs_Type" minOccurs="0"/>
 *         &lt;element name="From" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}From_Type"/>
 *         &lt;element name="Reply" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}Reply_Type" minOccurs="0"/>
 *         &lt;element name="SendDt" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}SendDt_Type"/>
 *         &lt;element name="Subject" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}Subject_Type"/>
 *         &lt;element name="Emails" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}Emails_Type"/>
 *         &lt;element name="TemplateName" type="{http://www.metlife.com.mx/schema/SendMail/SendMailWS}TemplateName_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MailData_Type", propOrder = {
    "fieldKeys",
    "contentType",
    "imageKeys",
    "bcCs",
    "cCs",
    "from",
    "reply",
    "sendDt",
    "subject",
    "emails",
    "templateName"
})
public class MailDataType {

    @XmlElement(name = "FieldKeys", required = true)
    protected FieldKeysType fieldKeys;
    @XmlElement(name = "ContentType", required = true)
    protected String contentType;
    @XmlElement(name = "ImageKeys", required = true)
    protected ImageKeysType imageKeys;
    @XmlElement(name = "BCCs")
    protected BCCsType bcCs;
    @XmlElement(name = "CCs")
    protected CCsType cCs;
    @XmlElement(name = "From", required = true)
    protected String from;
    @XmlElement(name = "Reply")
    protected String reply;
    @XmlElement(name = "SendDt", required = true)
    protected String sendDt;
    @XmlElement(name = "Subject", required = true)
    protected String subject;
    @XmlElement(name = "Emails", required = true)
    protected EmailsType emails;
    @XmlElement(name = "TemplateName", required = true)
    protected String templateName;

    /**
     * Gets the value of the fieldKeys property.
     * 
     * @return
     *     possible object is
     *     {@link FieldKeysType }
     *     
     */
    public FieldKeysType getFieldKeys() {
        return fieldKeys;
    }

    /**
     * Sets the value of the fieldKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldKeysType }
     *     
     */
    public void setFieldKeys(FieldKeysType value) {
        this.fieldKeys = value;
    }

    /**
     * Gets the value of the contentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Sets the value of the contentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContentType(String value) {
        this.contentType = value;
    }

    /**
     * Gets the value of the imageKeys property.
     * 
     * @return
     *     possible object is
     *     {@link ImageKeysType }
     *     
     */
    public ImageKeysType getImageKeys() {
        return imageKeys;
    }

    /**
     * Sets the value of the imageKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImageKeysType }
     *     
     */
    public void setImageKeys(ImageKeysType value) {
        this.imageKeys = value;
    }

    /**
     * Gets the value of the bcCs property.
     * 
     * @return
     *     possible object is
     *     {@link BCCsType }
     *     
     */
    public BCCsType getBCCs() {
        return bcCs;
    }

    /**
     * Sets the value of the bcCs property.
     * 
     * @param value
     *     allowed object is
     *     {@link BCCsType }
     *     
     */
    public void setBCCs(BCCsType value) {
        this.bcCs = value;
    }

    /**
     * Gets the value of the cCs property.
     * 
     * @return
     *     possible object is
     *     {@link CCsType }
     *     
     */
    public CCsType getCCs() {
        return cCs;
    }

    /**
     * Sets the value of the cCs property.
     * 
     * @param value
     *     allowed object is
     *     {@link CCsType }
     *     
     */
    public void setCCs(CCsType value) {
        this.cCs = value;
    }

    /**
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrom(String value) {
        this.from = value;
    }

    /**
     * Gets the value of the reply property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReply() {
        return reply;
    }

    /**
     * Sets the value of the reply property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReply(String value) {
        this.reply = value;
    }

    /**
     * Gets the value of the sendDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendDt() {
        return sendDt;
    }

    /**
     * Sets the value of the sendDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendDt(String value) {
        this.sendDt = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Gets the value of the emails property.
     * 
     * @return
     *     possible object is
     *     {@link EmailsType }
     *     
     */
    public EmailsType getEmails() {
        return emails;
    }

    /**
     * Sets the value of the emails property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmailsType }
     *     
     */
    public void setEmails(EmailsType value) {
        this.emails = value;
    }

    /**
     * Gets the value of the templateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the value of the templateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateName(String value) {
        this.templateName = value;
    }

}