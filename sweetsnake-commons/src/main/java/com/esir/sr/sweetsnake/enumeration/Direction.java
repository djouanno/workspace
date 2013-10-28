package com.esir.sr.sweetsnake.enumeration;

public enum Direction
{

    LEFT(new int[] { -1, 0 }), UP(new int[] { 0, -1 }), RIGHT(new int[] { +1, 0 }), DOWN(new int[] { 0, +1 });

    private int[] value = { 0, 0 };

    Direction(final int[] _value) {
        value = _value;
    }

    public int[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case LEFT:
                return "left";
            case UP:
                return "up";
            case RIGHT:
                return "right";
            case DOWN:
                return "down";
            default:
                return "unknown";
        }
    }

}
