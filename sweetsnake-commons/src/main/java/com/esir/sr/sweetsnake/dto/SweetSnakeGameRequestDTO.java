package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;


public class SweetSnakeGameRequestDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 7736451985866305018L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String      id, requestingPlayerName, requestedPlayerName;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeGameRequestDTO(final String _id, final String _requestingPlayerName, final String _requestedPlayerName) {
        id = _id;
        requestingPlayerName = _requestingPlayerName;
        requestedPlayerName = _requestedPlayerName;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public String getId() {
        return id;
    }

    public String getRequestingPlayerName() {
        return requestingPlayerName;
    }

    public String getRequestedPlayerName() {
        return requestedPlayerName;
    }

}
