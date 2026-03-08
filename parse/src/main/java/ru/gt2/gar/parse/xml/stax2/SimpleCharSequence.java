package ru.gt2.gar.parse.xml.stax2;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

@RequiredArgsConstructor
public class SimpleCharSequence implements CharSequence {
    private final char[] chars;
    private final int start;
    private final int end;

    @Override
    public int length() {
        return end - start;
    }

    @Override
    public char charAt(int index) {
        return chars[start + index];
    }

    @Override
    @NonNull
    public CharSequence subSequence(int start, int end) {
        return new SimpleCharSequence(chars, this.start + start, this.start + end);
    }

    @Override
    @NonNull
    public String toString() {
        return String.valueOf(chars, start, length());
    }
}
