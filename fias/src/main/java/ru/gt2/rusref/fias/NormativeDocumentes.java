package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для нормативных документов.
 */
@XmlRootElement(name = "NormativeDocumentes")
@ToString
public class NormativeDocumentes {
    @XmlElement(name = "NormativeDocument", required = true)
    protected List<NormativeDocument> normativeDocument;
}
