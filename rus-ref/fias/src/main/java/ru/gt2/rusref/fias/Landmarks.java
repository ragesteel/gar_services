package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для описания места расположения имущественных объектов.
 */
@XmlRootElement(name = "Landmarks")
@Getter
public class Landmarks implements Container<Landmark> {
    @XmlElement(name = "Landmark", required = true)
    private List<Landmark> list;
}
