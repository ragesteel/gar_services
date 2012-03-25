package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса актальности.
 */
@XmlRootElement(name = "ActualStatuses")
@ToString
public class ActualStatuses {
    @XmlElement(name = "ActualStatus", required = true)
    private List<ActualStatus> actualStatus;
}
