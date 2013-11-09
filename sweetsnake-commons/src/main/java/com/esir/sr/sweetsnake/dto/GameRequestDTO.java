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
     * @param _requestingPlayerDto
     * @param _requestedPlayerDto
     */
    public GameRequestDTO(final String _id, final PlayerDTO _requestingPlayerDto, final PlayerDTO _requestedPlayerDto) {
        id = _id;
        requestingPlayerDto = _requestingPlayerDto;
        requestedPlayerDto = _requestedPlayerDto;
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
