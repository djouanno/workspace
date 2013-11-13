package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.RefreshAction;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameBoardRefreshDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 6781521970787788523L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The component DTO to refresh */
    private final ComponentDTO  componentDto;

    /** The action to perform */
    private final RefreshAction action;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _componentDto
     * @param _action
     */
    public GameBoardRefreshDTO(final ComponentDTO _componentDto, final RefreshAction _action) {
        componentDto = _componentDto;
        action = _action;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public ComponentDTO getComponentDto() {
        return componentDto;
    }

    /**
     * 
     * @return
     */
    public RefreshAction getAction() {
        return action;
    }

}
