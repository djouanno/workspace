package com.esir.sr.sweetsnake.enumeration;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;

/**
 * This enumeration contains all the possible sound effects.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public enum SoundEffect
{

    /**********************************************************************************************
     * [BLOCK] STATIC ENUMERATIONS
     **********************************************************************************************/

    /** The move effect */
    MOVE(ClientGuiConstants.MOVE_PATH),

    /** The eat effect */
    EAT(ClientGuiConstants.EAT_PATH);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The sound effect value */
    private String value;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new sound effect
     * 
     * @param _value
     *            The sound file path
     */
    SoundEffect(final String _value) {
        value = _value;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHOD
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
