package com.esir.sr.sweetsnake.enumeration;

import javax.sound.sampled.Clip;

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

    /** The ambiance effect */
    AMBIANCE(ClientGuiConstants.AMBIANCE_PATH, Clip.LOOP_CONTINUOUSLY),

    /** The move effect */
    MOVE(ClientGuiConstants.MOVE_PATH, 0),

    /** The eat effect */
    EAT(ClientGuiConstants.EAT_PATH, 0);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The sound effect path */
    private String path;

    /** The sound effect looping */
    private int    loop;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new sound effect
     * 
     * @param _path
     *            The sound file path
     * @param _loop
     *            The sound looping
     */
    SoundEffect(final String _path, final int _loop) {
        path = _path;
        loop = _loop;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHOD
     **********************************************************************************************/

    /**
     * This method returns the path of the sound effect
     * 
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * This method returns the looping value of the sound effect
     * 
     * @return
     */
    public int getLooping() {
        return loop;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
