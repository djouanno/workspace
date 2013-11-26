package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.RefreshAction;

/**
 * This class is a Data Transfert Object representing a GameBoardRefresh.
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
     * Creates a new gameboard refresh DTO
     * 
     * @param _componentDto
     *            The DTO representing the component to refresh
     * @param _action
     *            The action to perform on the component
     */
    public GameBoardRefreshDTO(final ComponentDTO _componentDto, final RefreshAction _action) {
        componentDto = _componentDto;
        action = _action;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the DTO representing the component to refresh
     * 
     * @return The DTO representing the component to refresh
     */
    public ComponentDTO getComponentDto() {
        return componentDto;
    }

    /**
     * This method returns the action to perform
     * 
     * @return The refresh action to perform
     */
    public RefreshAction getAction() {
        return action;
    }

}
