package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

public class PlayerDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long            serialVersionUID = -7478382230116293470L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String                 name;
    private final PlayerStatus status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public PlayerDTO(final String _name, final PlayerStatus _status) {
        name = _name;
        status = _status;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public String toString() {
        return name + " [" + status + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public String getName() {
        return name;
    }

    public PlayerStatus getStatus() {
        return status;
    }

}
