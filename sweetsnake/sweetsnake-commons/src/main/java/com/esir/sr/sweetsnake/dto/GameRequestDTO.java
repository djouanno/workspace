package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

/**
 * This class is a Data Transfert Object representing a GameRequest.
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
     * Creates a new game request DTO
     * 
     * @param _id
     *            The game request id
     * @param _sessionId
     *            The game session id from which the request was sent
     * @param _requestingPlayerDto
     *            The DTO representing the requesting player
     * @param _requestedPlayerDto
     *            The DTO representing the requested player
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
     * This method returns the game request id
     * 
     * @return A string representing the game request id
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the id of the game session from which the game request was sent
     * 
     * @return A string representing the game session id from which the game request was sent
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * This method returns the DTO representing the requesting player
     * 
     * @return The DTO representing the requesting player
     */
    public PlayerDTO getRequestingPlayerDto() {
        return requestingPlayerDto;
    }

    /**
     * This method returns the DTO represented the requesting player
     * 
     * @return The DTO representing the requested player
     */
    public PlayerDTO getRequestedPlayerDto() {
        return requestedPlayerDto;
    }

}
