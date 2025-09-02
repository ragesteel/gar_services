package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для нормативных документов.
 */
@XmlRootElement(name = "NormativeDocumentes")
@Getter
public class NormativeDocumentes implements Container<NormativeDocument> {
    @XmlElement(name = "NormativeDocument", required = true)
    protected List<NormativeDocument> list;
}
