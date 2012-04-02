package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для признака строения.
 */
@XmlRootElement(name = "StructureStatuses")
@Getter
public class StructureStatuses implements Container<StructureStatus> {
    @XmlElement(name = "StructureStatus", required = true)
    private List<StructureStatus> list;
}
