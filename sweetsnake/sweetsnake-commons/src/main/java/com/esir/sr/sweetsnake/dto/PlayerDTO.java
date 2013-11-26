package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * This class is a Data Transfert Object representing a Player.
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
    private final int          number;

    /** The player score */
    private final int          score;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new player DTO
     * 
     * @param _name
     *            The player's name
     * @param _status
     *            The player's status
     * @param _snakeId
     *            The player's snake id in his current session
     * @param _number
     *            The player's number in his current session
     * @param _score
     *            The player's score in his current session
     */
    public PlayerDTO(final String _name, final PlayerStatus _status, final String _snakeId, final int _number, final int _score) {
        name = _name;
        status = _status;
        snakeId = _snakeId;
        number = _number;
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
     * This method returns the player's name
     * 
     * @return A string representing the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the player's status
     * 
     * @return The player's status
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * This method returns the player's snake id in his current session<br />
     * If the player is not in a session, this method returns null
     * 
     * @return The player's snake id in his current session or null
     */
    public String getSnakeId() {
        return snakeId;
    }

    /**
     * This method returns the player's number in his current session<br />
     * If the player is not in a session, this method returns 0
     * 
     * @return The player's number in his current session or 0
     */
    public int getNumber() {
        return number;
    }

    /**
     * This method returns the player's score in his current session<br />
     * If the player is not in a session, this method returns 0
     * 
     * @return The player's score in his current session or 0
     */
    public int getScore() {
        return score;
    }

}
