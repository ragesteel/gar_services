package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статусов актуальности КЛАДР 4.0.
 */
@XmlRootElement(name = "CurrentStatuses")
@ToString
public class CurrentStatuses {
    @XmlElement(name = "CurrentStatus", required = true)
    private List<CurrentStatus> currentStatus;
}
