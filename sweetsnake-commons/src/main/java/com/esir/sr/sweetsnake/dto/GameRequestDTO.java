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

    /** The session id */
    private final String      sessionId;

    /** The requesting player DTO */
    private final PlayerDTO   requestingPlayerDto;

    /** The requested player DTO */
    private final PlayerDTO   requestedPlayerDto;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _sessionId
     * @param _requestingPlayerDto
     * @param _requestedPlayerDto
     */
    public GameRequestDTO(final String _id, final String _sessionId, final PlayerDTO _requestingPlayerDto, final PlayerDTO _requestedPlayerDto) {
        id = _id;
        sessionId = _sessionId;
        requestingPlayerDto = _requestingPlayerDto;
        requestedPlayerDto = _requestedPlayerDto;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String s = id;
        if (requestingPlayerDto != null && requestedPlayerDto != null) {
            s += " [" + requestingPlayerDto.getName() + "/" + requestedPlayerDto.getName() + "]";
        }
        return s;
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
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 
     * @return
     */
    public PlayerDTO getRequestingPlayerDto() {
        return requestingPlayerDto;
    }

    /**
     * 
     * @return
     */
    public PlayerDTO getRequestedPlayerDto() {
        return requestedPlayerDto;
    }

}
