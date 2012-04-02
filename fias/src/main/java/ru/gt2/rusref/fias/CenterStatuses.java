package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса центра.
 */
@XmlRootElement(name = "CenterStatuses")
@Getter
public class CenterStatuses implements Container<CenterStatus> {
    @XmlElement(name = "CenterStatus", required = true)
    private List<CenterStatus> list;
}
