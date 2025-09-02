package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для признака владения.
 */
@XmlRootElement(name = "EstateStatuses")
@Getter
public class EstateStatuses implements Container<EstateStatus> {
    @XmlElement(name = "EstateStatus", required = true)
    private List<EstateStatus> list;
}
