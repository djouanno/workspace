package com.esir.sr.sweetsnake.game.board;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;

/**
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
     * 
     * @param _component
     * @param _action
     */
    public GameBoardRefresh(final IComponent _component, final RefreshAction _action) {
        component = _component;
        action = _action;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public IComponent getComponent() {
        return component;
    }

    /**
     * 
     * @return
     */
    public RefreshAction getAction() {
        return action;
    }

}
