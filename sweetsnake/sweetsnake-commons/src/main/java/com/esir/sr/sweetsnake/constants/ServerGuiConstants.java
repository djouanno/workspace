package com.esir.sr.sweetsnake.constants;

/**
 * This class contains all the server GUI constants.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ServerGuiConstants
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the server GUI constants
     */
    private ServerGuiConstants() {
    }

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The GUI dimension */
    public static final int    GUI_DIMENSION         = 700;

    /** The GUI width offset */
    public static final int    GUI_WIDTH_OFFSET      = 400;

    /** The GUI width */
    public static final int    GUI_WIDTH             = GUI_DIMENSION + GUI_WIDTH_OFFSET;

    /** The GUI height */
    public static final int    GUI_HEIGHT            = GUI_DIMENSION;

    /** The images folder path */
    public static final String IMG_PATH              = "/img/";

    /** The application icon path */
    public static final String ICON_PATH             = IMG_PATH + "icon.png";

    /** The available icon path */
    public static final String AVAILABLE_ICON_PATH   = IMG_PATH + "available_icon.png";

    /** The invite icon path */
    public static final String INVITE_ICON_PATH      = IMG_PATH + "invite_icon.png";

    /** The unavailable icon path */
    public static final String UNAVAILABLE_ICON_PATH = IMG_PATH + "unavailable_icon.png";

}
