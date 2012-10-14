
package ru.gt2.rusref.fias.update;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfDownloadFileInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfDownloadFileInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DownloadFileInfo" type="{http://fias.nalog.ru/WebServices/Public/DownloadService.asmx/}DownloadFileInfo" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfDownloadFileInfo", propOrder = {
    "downloadFileInfo"
})
public class ArrayOfDownloadFileInfo {

    @XmlElement(name = "DownloadFileInfo", nillable = true)
    protected List<DownloadFileInfo> downloadFileInfo;

    /**
     * Gets the value of the downloadFileInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the downloadFileInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDownloadFileInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DownloadFileInfo }
     * 
     * 
     */
    public List<DownloadFileInfo> getDownloadFileInfo() {
        if (downloadFileInfo == null) {
            downloadFileInfo = new ArrayList<DownloadFileInfo>();
        }
        return this.downloadFileInfo;
    }

}
