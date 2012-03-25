package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для описания места расположения имущественных объектов.
 */
@XmlRootElement(name = "Landmarks")
@ToString
public class Landmarks {
    @XmlElement(name = "Landmark", required = true)
    private List<Landmark> landmark;
}
