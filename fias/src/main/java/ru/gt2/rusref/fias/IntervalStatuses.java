package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса интервалов домов.
 */
@XmlRootElement(name = "IntervalStatuses")
@ToString
public class IntervalStatuses {
    @XmlElement(name = "IntervalStatus", required = true)
    private List<IntervalStatus> intervalStatus;
}
