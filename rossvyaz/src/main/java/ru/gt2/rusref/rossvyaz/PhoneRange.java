package ru.gt2.rusref.rossvyaz;

import lombok.Getter;
import lombok.Setter;

/**
 * Предстваление диапазона телефонных номеров.
 */
@Getter
@Setter
public class PhoneRange {

    private int code;
    private int begin;
    private int end;
    private int count;
    private String operator;
    private String region;

}
