package fr.esir.project.sr.sweetsnake.commons.dto;

import java.io.Serializable;

import fr.esir.project.sr.sweetsnake.commons.enumerations.Status;

public class PlayerDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = -7478382230116293470L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private String            name;
    private Status            status;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public PlayerDTO() {
        super();
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

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    public void setName(final String _name) {
        name = _name;
    }

    public void setStatus(final Status _status) {
        status = _status;
    }

}
