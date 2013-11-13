package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.ComponentType;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ComponentDTO implements Serializable
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 4845304179195761034L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The element id */
    protected String            id;

    /** The element x position on the game map */
    protected int               x;

    /** The element y position on the game map */
    protected int               y;

    /** The element type */
    protected final ComponentType type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _x
     * @param _y
     * @param _type
     */
    public ComponentDTO(final String _id, final int _x, final int _y, final ComponentType _type) {
        id = _id;
        x = _x;
        y = _y;
        type = _type;
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
    public int getX() {
        return x;
    }

    /**
     * 
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * 
     * @return
     */
    public ComponentType getType() {
        return type;
    }

}
