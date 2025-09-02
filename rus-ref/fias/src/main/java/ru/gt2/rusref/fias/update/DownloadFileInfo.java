
package ru.gt2.rusref.fias.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DownloadFileInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DownloadFileInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VersionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TextVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FiasCompleteDbfUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FiasCompleteXmlUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FiasDeltaDbfUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FiasDeltaXmlUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Kladr4ArjUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Kladr47ZUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DownloadFileInfo", propOrder = {
    "versionId",
    "textVersion",
    "fiasCompleteDbfUrl",
    "fiasCompleteXmlUrl",
    "fiasDeltaDbfUrl",
    "fiasDeltaXmlUrl",
    "kladr4ArjUrl",
    "kladr47ZUrl"
})
public class DownloadFileInfo {

    @XmlElement(name = "VersionId")
    protected int versionId;
    @XmlElement(name = "TextVersion")
    protected String textVersion;
    @XmlElement(name = "FiasCompleteDbfUrl")
    protected String fiasCompleteDbfUrl;
    @XmlElement(name = "FiasCompleteXmlUrl")
    protected String fiasCompleteXmlUrl;
    @XmlElement(name = "FiasDeltaDbfUrl")
    protected String fiasDeltaDbfUrl;
    @XmlElement(name = "FiasDeltaXmlUrl")
    protected String fiasDeltaXmlUrl;
    @XmlElement(name = "Kladr4ArjUrl")
    protected String kladr4ArjUrl;
    @XmlElement(name = "Kladr47ZUrl")
    protected String kladr47ZUrl;

    /**
     * Gets the value of the versionId property.
     * 
     */
    public int getVersionId() {
        return versionId;
    }

    /**
     * Sets the value of the versionId property.
     * 
     */
    public void setVersionId(int value) {
        this.versionId = value;
    }

    /**
     * Gets the value of the textVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextVersion() {
        return textVersion;
    }

    /**
     * Sets the value of the textVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextVersion(String value) {
        this.textVersion = value;
    }

    /**
     * Gets the value of the fiasCompleteDbfUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiasCompleteDbfUrl() {
        return fiasCompleteDbfUrl;
    }

    /**
     * Sets the value of the fiasCompleteDbfUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiasCompleteDbfUrl(String value) {
        this.fiasCompleteDbfUrl = value;
    }

    /**
     * Gets the value of the fiasCompleteXmlUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiasCompleteXmlUrl() {
        return fiasCompleteXmlUrl;
    }

    /**
     * Sets the value of the fiasCompleteXmlUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiasCompleteXmlUrl(String value) {
        this.fiasCompleteXmlUrl = value;
    }

    /**
     * Gets the value of the fiasDeltaDbfUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiasDeltaDbfUrl() {
        return fiasDeltaDbfUrl;
    }

    /**
     * Sets the value of the fiasDeltaDbfUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiasDeltaDbfUrl(String value) {
        this.fiasDeltaDbfUrl = value;
    }

    /**
     * Gets the value of the fiasDeltaXmlUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFiasDeltaXmlUrl() {
        return fiasDeltaXmlUrl;
    }

    /**
     * Sets the value of the fiasDeltaXmlUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFiasDeltaXmlUrl(String value) {
        this.fiasDeltaXmlUrl = value;
    }

    /**
     * Gets the value of the kladr4ArjUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKladr4ArjUrl() {
        return kladr4ArjUrl;
    }

    /**
     * Sets the value of the kladr4ArjUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKladr4ArjUrl(String value) {
        this.kladr4ArjUrl = value;
    }

    /**
     * Gets the value of the kladr47ZUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKladr47ZUrl() {
        return kladr47ZUrl;
    }

    /**
     * Sets the value of the kladr47ZUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKladr47ZUrl(String value) {
        this.kladr47ZUrl = value;
    }

}
