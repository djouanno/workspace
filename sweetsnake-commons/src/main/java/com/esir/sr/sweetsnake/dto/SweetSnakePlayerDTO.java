package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.Status;

public class SweetSnakePlayerDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = -7478382230116293470L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String      name;
    private final Status      status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakePlayerDTO(final String _name, final Status _status) {
        name = _name;
        status = _status;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public String getName() {
        return name;
    }

    public Status getStatus() {
        return status;
    }

}
