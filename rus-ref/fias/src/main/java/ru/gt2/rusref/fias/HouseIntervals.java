package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для интервалов домов.
 */
@XmlRootElement(name = "HouseIntervals")
@Getter
public class HouseIntervals implements Container<HouseInterval> {
    @XmlElement(name = "HouseInterval", required = true)
    private List<HouseInterval> list;
}
