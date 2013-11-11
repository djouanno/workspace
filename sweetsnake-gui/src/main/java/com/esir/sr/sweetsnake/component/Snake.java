package com.esir.sr.sweetsnake.component;

import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Snake extends AbstractComponent
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version uid */
    private static final long serialVersionUID = 3698273223538543595L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _x
     * @param _y
     */
    public Snake(final String _id, final int _x, final int _y, final String color) {
        super(_id, _x, _y, ComponentType.SNAKE, color.equals(GuiConstants.GREEN_SNAKE_VALUE) ? GuiConstants.GREEN_SNAKE_ICON_PATH : GuiConstants.RED_SNAKE_ICON_PATH);
    }

}
