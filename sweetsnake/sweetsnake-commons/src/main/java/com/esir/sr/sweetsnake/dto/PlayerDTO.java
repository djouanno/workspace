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
    private static final long  serialVersionUID = -5312088899568544528L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/


    /** The player name */
    private final String       name;

    /** The player status */
    private final PlayerStatus status;

    /** The player's snake id */
    private final String       snakeId;

    /** The player number */
    private final String       number;

    /** The player score */
    private final String       score;

    /** Is the player fictive */
    private final boolean      isFictive;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _name
     * @param _status
     * @param _snakeId
     * @param _number
     * @param _score
     * @param _isFictive
     */
    public PlayerDTO(final String _name, final PlayerStatus _status, final String _snakeId, final int _number, final int _score, final boolean _isFictive) {
        name = _name;
        status = _status;
        snakeId = _snakeId;
        number = "" + _number;
        score = "" + _score;
        isFictive = _isFictive;
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
    public int getNumber() {
        return Integer.parseInt(number);
    }

    /**
     * 
     * @return
     */
    public int getScore() {
        return Integer.parseInt(score);
    }

    /**
     * 
     * @return
     */
    public boolean isFictive() {
        return isFictive;
    }

}
