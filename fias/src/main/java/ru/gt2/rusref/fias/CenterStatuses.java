package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса центра.
 */
@XmlRootElement(name = "CenterStatuses")
@ToString
public class CenterStatuses {
    @XmlElement(name = "CenterStatus", required = true)
    private List<CenterStatus> centerStatus;
}
