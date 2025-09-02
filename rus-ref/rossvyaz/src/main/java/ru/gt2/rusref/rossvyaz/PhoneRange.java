package ru.gt2.rusref.rossvyaz;

import com.google.common.base.Objects;
import com.google.common.collect.Range;
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

    @Override
    public String toString() {
        Range range = Range.closed(begin, end);

        return Objects.toStringHelper(this)
                .add("code", code)
                .add("range", range)
                .add("count", count)
                .add("operator", operator)
                .add("region", region).toString();
    }


}
