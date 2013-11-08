package com.esir.sr.sweetsnake.enumeration;

public enum ElementType
{

    SNAKE("snake"), SWEET("sweet");

    private String value;

    ElementType(final String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }

}