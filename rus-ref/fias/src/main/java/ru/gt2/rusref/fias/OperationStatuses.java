package ru.gt2.rusref.fias;

import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса действия.
 */
@XmlRootElement(name = "OperationStatuses")
@Getter
public class OperationStatuses implements Container<OperationStatus> {
    @XmlElement(name = "OperationStatus", required = true)
    private List<OperationStatus> list;
}
