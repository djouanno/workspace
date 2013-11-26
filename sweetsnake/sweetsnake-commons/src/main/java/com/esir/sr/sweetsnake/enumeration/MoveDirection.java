package com.esir.sr.sweetsnake.enumeration;

/**
 * This enumeration contains all the possible directions for a move.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum MoveDirection
{

    /**********************************************************************************************
     * [BLOCK] STATIC ENUMERATIONS
     **********************************************************************************************/

    /** The left direction */
    LEFT(new int[] { -1, 0 }),

    /** The up direction */
    UP(new int[] { 0, -1 }),

    /** The right direction */
    RIGHT(new int[] { +1, 0 }),

    /** The down direction */
    DOWN(new int[] { 0, +1 });

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The direction value */
    private int[] value;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new move direction
     * 
     * @param _value
     *            The move direction value
     */
    MoveDirection(final int[] _value) {
        value = new int[_value.length];
        System.arraycopy(_value, 0, value, 0, _value.length);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method return the move direction value
     * 
     * @return An array containing the move direction value
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
