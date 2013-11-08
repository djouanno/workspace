package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;


public class GameSessionDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 459664230963147646L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String      id, player1, player2;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public GameSessionDTO(final String _id, final String _player1, final String _player2) {
        id = _id;
        player1 = _player1;
        player2 = _player2;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public String getId() {
        return id;
    }

    public String getPlayer1Name() {
        return player1;
    }

    public String getPlayer2Name() {
        return player2;
    }

}
