package com.esir.sr.sweetsnake.enumeration;

public enum MoveDirection
{

    LEFT(new int[] { -1, 0 }), UP(new int[] { 0, -1 }), RIGHT(new int[] { +1, 0 }), DOWN(new int[] { 0, +1 });

    private int[] value;

    MoveDirection(final int[] _value) {
        value = _value;
    }

    public int[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        String direction;
        switch (this) {
            case LEFT:
                direction = "left";
                break;
            case UP:
                direction = "up";
                break;
            case RIGHT:
                direction = "right";
                break;
            case DOWN:
                direction = "down";
                break;
            default:
                return "unknown";
        }
        return direction + "[" + value[0] + "," + value[1] + "]";
    }

}
