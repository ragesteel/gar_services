package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса актальности.
 */
@XmlRootElement(name = "ActualStatuses")
@Getter
public class ActualStatuses implements Container<ActualStatus> {
    @XmlElement(name = "ActualStatus", required = true)
    private List<ActualStatus> list;
}
