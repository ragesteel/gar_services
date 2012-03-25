package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса центра.
 */
@XmlRootElement(name = "HouseStateStatuses")
@ToString
public class HouseStateStatuses {
    @XmlElement(name = "HouseStateStatus", required = true)
    private List<HouseStateStatus> houseStateStatus;
}
