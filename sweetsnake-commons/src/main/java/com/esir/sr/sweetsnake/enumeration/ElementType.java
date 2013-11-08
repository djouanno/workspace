package com.esir.sr.sweetsnake.enumeration;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum ElementType
{

    /** The snake type */
    SNAKE("snake"),

    /** The sweet type */
    SWEET("sweet");

    /** The type value */
    private String value;

    /**
     * 
     * @param _value
     */
    ElementType(final String _value) {
        value = _value;
    }

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