package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для сведений по номерам домов улиц городов и населенных пунктов, номера земельных участков и т.п.
 */
@XmlRootElement(name = "Houses")
@Getter
public class Houses implements Container<House> {
    @XmlElement(name = "House", required = true)
    protected List<House> list;
}
