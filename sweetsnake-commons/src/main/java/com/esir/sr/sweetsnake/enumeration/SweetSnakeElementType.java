package com.esir.sr.sweetsnake.enumeration;

public enum SweetSnakeElementType
{

    SNAKE("snake"), SWEET("sweet");

    private String value;

    SweetSnakeElementType(final String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }

}