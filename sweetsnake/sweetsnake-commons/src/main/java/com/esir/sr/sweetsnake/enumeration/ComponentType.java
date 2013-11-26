package com.esir.sr.sweetsnake.enumeration;

/**
 * This enumeration contains all the possible types for a game component.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum ComponentType
{

    /**********************************************************************************************
     * [BLOCK] STATIC ENUMERATIONS
     **********************************************************************************************/

    /** The snake type */
    SNAKE("snake"),

    /** The sweet type */
    SWEET("sweet");

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The type value */
    private String value;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new component type
     * 
     * @param _value
     *            The component type value
     */
    ComponentType(final String _value) {
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