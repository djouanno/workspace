package com.esir.sr.sweetsnake.component;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
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
    public Snake(final String _id, final int _x, final int _y, final String iconPath) {
        super(_id, _x, _y, ComponentType.SNAKE, iconPath);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param nb
     * @return
     */
    public static String findSnakeIconPath(final int nb) {
        switch (nb) {
            case 2:
                return ClientGuiConstants.RED_SNAKE_ICON_PATH;
            case 3:
                return ClientGuiConstants.BLUE_SNAKE_ICON_PATH;
            case 4:
                return ClientGuiConstants.BLACK_SNAKE_ICON_PATH;
            default:
                return ClientGuiConstants.GREEN_SNAKE_ICON_PATH;
        }
    }

}
