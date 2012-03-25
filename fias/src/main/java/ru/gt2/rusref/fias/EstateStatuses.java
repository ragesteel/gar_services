package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для признака владения.
 */
@XmlRootElement(name = "EstateStatuses")
@ToString
public class EstateStatuses {
    @XmlElement(name = "EstateStatus", required = true)
    private List<EstateStatus> estateStatus;
}
