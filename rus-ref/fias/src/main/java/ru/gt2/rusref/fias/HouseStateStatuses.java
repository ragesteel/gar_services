package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса центра.
 */
@XmlRootElement(name = "HouseStateStatuses")
@Getter
public class HouseStateStatuses implements Container<HouseStateStatus> {
    @XmlElement(name = "HouseStateStatus", required = true)
    private List<HouseStateStatus> list;
}
