package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

import com.esir.sr.sweetsnake.enumeration.ComponentType;

/**
 * This class is a Data Transfert Object representing an IComponent.
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
    private final String        id;

    /** The element x position on the game map */
    private final int           x;

    /** The element y position on the game map */
    private final int           y;

    /** The element type */
    private final ComponentType type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new component DTO
     * 
     * @param _id
     *            The component id
     * @param _x
     *            The component x position
     * @param _y
     *            The component y position
     * @param _type
     *            The component type
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
     * This method returns the component id
     * 
     * @return A string representing the component id
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the component X position
     * 
     * @return An integer representing the component X position
     */
    public int getX() {
        return x;
    }

    /**
     * This method returns the component Y position
     * 
     * @return An integer representing the component Y position
     */
    public int getY() {
        return y;
    }

    /**
     * This method returns the component type
     * 
     * @return The component type
     */
    public ComponentType getType() {
        return type;
    }

}
