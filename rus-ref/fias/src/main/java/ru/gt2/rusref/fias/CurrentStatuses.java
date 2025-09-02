package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статусов актуальности КЛАДР 4.0.
 */
@XmlRootElement(name = "CurrentStatuses")
@Getter
public class CurrentStatuses implements Container<CurrentStatus> {
    @XmlElement(name = "CurrentStatus", required = true)
    private List<CurrentStatus> list;
}
