package com.esir.sr.sweetsnake.enumeration;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum MoveDirection
{

    /** The left direction */
    LEFT(new int[] { -1, 0 }),

    /** The up direction */
    UP(new int[] { 0, -1 }),

    /** The right direction */
    RIGHT(new int[] { +1, 0 }),

    /** The down direction */
    DOWN(new int[] { 0, +1 });

    /** The direction value */
    private int[] value;

    /**
     * 
     * @param _value
     */
    MoveDirection(final int[] _value) {
        value = _value;
    }

    /**
     * 
     * @return
     */
    public int[] getValue() {
        return value;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
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
