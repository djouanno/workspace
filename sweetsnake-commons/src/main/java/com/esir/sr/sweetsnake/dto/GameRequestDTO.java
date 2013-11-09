package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameRequestDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 7736451985866305018L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The request id */
    private final String      id;

    /** The requesting player name */
    private final String      requestingPlayerName;

    /** The requested player name */
    private final String      requestedPlayerName;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _requestingPlayerName
     * @param _requestedPlayerName
     */
    public GameRequestDTO(final String _id, final String _requestingPlayerName, final String _requestedPlayerName) {
        id = _id;
        requestingPlayerName = _requestingPlayerName;
        requestedPlayerName = _requestedPlayerName;
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
    public String getRequestingPlayerName() {
        return requestingPlayerName;
    }

    /**
     * 
     * @return
     */
    public String getRequestedPlayerName() {
        return requestedPlayerName;
    }

}
