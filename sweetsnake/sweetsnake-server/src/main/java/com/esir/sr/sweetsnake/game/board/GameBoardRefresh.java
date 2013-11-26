package com.esir.sr.sweetsnake.game.board;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;

/**
 * This class represents a refresh done on the gameboard according to the event processed its components.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameBoardRefresh
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The component to refresh */
    private final IComponent    component;

    /** The action to perform */
    private final RefreshAction action;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new gameboard refresh
     * 
     * @param _component
     *            The component to refresh
     * @param _action
     *            The action to perform on the component
     */
    public GameBoardRefresh(final IComponent _component, final RefreshAction _action) {
        component = _component;
        action = _action;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the component to refresh
     * 
     * @return The component to refresh
     */
    public IComponent getComponent() {
        return component;
    }

    /**
     * This method returns the action to perform on the component
     * 
     * @return The action to perform on the component
     */
    public RefreshAction getAction() {
        return action;
    }

}
