package ru.gt2.gar.parse.xml.stax2;

import org.codehaus.stax2.typed.TypedValueDecoder;

public class IntAsBooleanDecoder  extends TypedValueDecoder {
    public boolean value;

    @Override
    public void decode(String input) throws IllegalArgumentException {
        if (input.length() != 1) {
            throw new IllegalArgumentException("Length must be exactly one character");
        }
        setValue(input.charAt(0));
    }

    @Override
    public void decode(char[] buffer, int start, int end) throws IllegalArgumentException {
        if (end - start != 1) {
            throw new IllegalArgumentException("Length must be exactly one character");
        }
        setValue(buffer[start]);
    }

    private void setValue(char character) {
        value = switch (character) {
            case '0' -> false;
            case '1' -> true;
            default -> throw new IllegalArgumentException("Invalid value for Boolean");
        };
    }

    @Override
    public void handleEmptyValue() throws IllegalArgumentException {
        throw new IllegalArgumentException("Unable to parse boolean from empty string");
    }
}
