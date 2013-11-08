package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSessionDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 459664230963147646L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private final String      id;

    /** The first player name */
    private final String      player1Name;

    /** The second player name */
    private final String      player2Name;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _player1
     * @param _player2
     */
    public GameSessionDTO(final String _id, final String _player1, final String _player2) {
        id = _id;
        player1Name = _player1;
        player2Name = _player2;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return
     */
    public String getPlayer1Name() {
        return player1Name;
    }

    /**
     * 
     * @return
     */
    public String getPlayer2Name() {
        return player2Name;
    }

}
