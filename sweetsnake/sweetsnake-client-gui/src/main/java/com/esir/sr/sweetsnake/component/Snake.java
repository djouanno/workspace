package com.esir.sr.sweetsnake.component;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;

/**
 * This class graphically represents a player's snake by extending the AbstractComponent class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.component.AbstractComponent
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
     * Creates a new snake
     * 
     * @param _id
     *            The snake id
     * @param _x
     *            The snake X position
     * @param _y
     *            The snake Y position
     */
    public Snake(final String _id, final int _x, final int _y, final String iconPath) {
        super(_id, _x, _y, ComponentType.SNAKE, iconPath);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * This method returns the snake icon path according to the player's number in the game session
     * 
     * @param nb
     *            The player's number in the game session
     * @return The snake icon path according to the player's number in the game session
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
