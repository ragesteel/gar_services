package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса интервалов домов.
 */
@XmlRootElement(name = "IntervalStatuses")
@Getter
public class IntervalStatuses implements Container<IntervalStatus> {
    @XmlElement(name = "IntervalStatus", required = true)
    private List<IntervalStatus> list;
}
