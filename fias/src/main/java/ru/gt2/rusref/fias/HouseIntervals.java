package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для интервалов домов.
 */
@XmlRootElement(name = "HouseIntervals")
@ToString
public class HouseIntervals {
    @XmlElement(name = "HouseInterval", required = true)
    private List<HouseInterval> houseInterval;
}
