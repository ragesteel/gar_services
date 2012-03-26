package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для сведений по номерам домов улиц городов и населенных пунктов, номера земельных участков и т.п.
 */
@XmlRootElement(name = "Houses")
@ToString
public class Houses {
    @XmlElement(name = "House", required = true)
    protected List<House> house;
}
