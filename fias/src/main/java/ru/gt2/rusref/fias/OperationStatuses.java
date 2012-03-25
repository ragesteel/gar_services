package ru.gt2.rusref.fias;

import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Внешний элемент для статуса действия.
 */
@XmlRootElement(name = "OperationStatuses")
@ToString
public class OperationStatuses {
    @XmlElement(name = "OperationStatus", required = true)
    private List<OperationStatus> operationStatus;
}
