package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;


public class SweetSnakeGameSessionRequestDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 7736451985866305018L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String      requestingPlayerName, requestedPlayerName;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeGameSessionRequestDTO(final String _requestingPlayerName, final String _requestedPlayerName) {
        requestingPlayerName = _requestingPlayerName;
        requestedPlayerName = _requestedPlayerName;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public String getRequestingPlayerName() {
        return requestingPlayerName;
    }

    public String getRequestedPlayerName() {
        return requestedPlayerName;
    }

}
