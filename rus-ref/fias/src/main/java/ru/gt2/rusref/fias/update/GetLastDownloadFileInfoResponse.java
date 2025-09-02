
package ru.gt2.rusref.fias.update;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetLastDownloadFileInfoResult" type="{http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/}DownloadFileInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getLastDownloadFileInfoResult"
})
@XmlRootElement(name = "GetLastDownloadFileInfoResponse")
public class GetLastDownloadFileInfoResponse {

    @XmlElement(name = "GetLastDownloadFileInfoResult")
    protected DownloadFileInfo getLastDownloadFileInfoResult;

    /**
     * Gets the value of the getLastDownloadFileInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link DownloadFileInfo }
     *     
     */
    public DownloadFileInfo getGetLastDownloadFileInfoResult() {
        return getLastDownloadFileInfoResult;
    }

    /**
     * Sets the value of the getLastDownloadFileInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DownloadFileInfo }
     *     
     */
    public void setGetLastDownloadFileInfoResult(DownloadFileInfo value) {
        this.getLastDownloadFileInfoResult = value;
    }

}
