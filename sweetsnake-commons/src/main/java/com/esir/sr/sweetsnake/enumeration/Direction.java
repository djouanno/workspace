package com.esir.sr.sweetsnake.enumeration;

public enum Direction
{

    LEFT("left"), UP("up"), RIGHT("right"), DOWN("down");

    private String value = "unknown";

    Direction(final String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }

}
