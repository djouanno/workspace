package com.esir.sr.sweetsnake.enumeration;

/**
 * This enumeration contains all the possible action for a refresh.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum RefreshAction
{

    /**********************************************************************************************
     * [BLOCK] STATIC ENUMERATIONS
     **********************************************************************************************/

    /** The add action */
    ADD("add"),

    /** The move action */
    MOVE("move"),

    /** The remove action */
    REMOVE("remove");

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The refresh action value */
    private String value;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new refresh action
     * 
     * @param _value
     *            The refresh action value
     */
    private RefreshAction(final String _value) {
        value = _value;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return value;
    }

}
