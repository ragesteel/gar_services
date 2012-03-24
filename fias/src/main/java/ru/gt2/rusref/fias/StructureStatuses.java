package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для признака строения.
 */
@XmlRootElement(name = "StructureStatuses")
@ToString
public class StructureStatuses {
    @XmlElement(name = "StructureStatus", required = true)
    private List<StructureStatus> structureStatus;
}
