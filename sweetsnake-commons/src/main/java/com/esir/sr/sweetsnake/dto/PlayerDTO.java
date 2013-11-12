package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long  serialVersionUID = -7478382230116293470L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The player name */
    private final String       name;

    /** The player status */
    private final PlayerStatus status;

    /** The player's snake id */
    private final String       snakeId;

    /** The player score */
    private final int          score;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _name
     * @param _status
     * @param _score
     */
    public PlayerDTO(final String _name, final PlayerStatus _status, final String _snakeId, final int _score) {
        name = _name;
        status = _status;
        snakeId = _snakeId;
        score = _score;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return name;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @return
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * 
     * @return
     */
    public String getSnakeId() {
        return snakeId;
    }

    /**
     * 
     * @return
     */
    public int getScore() {
        return score;
    }

}
