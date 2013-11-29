package com.esir.sr.sweetsnake.constants;

/**
 * This class contains all the client gui constants.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ClientGuiConstants
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the client GUI constants
     */
    private ClientGuiConstants() {
    }

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The GUI dimension */
    public static final int     GUI_DIMENSION         = 690;

    /** The GUI heifht offset */
    public static final int     GUI_HEIGHT_OFFSET     = 64;

    /** The GUI width */
    public static final int     GUI_WIDTH             = GUI_DIMENSION;

    /** The GUI height */
    public static final int     GUI_HEIGHT            = GUI_DIMENSION + GUI_HEIGHT_OFFSET;

    /** The view dimension **/
    public static final int     VIEW_DIMENSION        = GUI_DIMENSION - 55;

    /** The view width */
    public static final int     VIEW_WIDTH            = VIEW_DIMENSION;

    /** The view height */
    public static final int     VIEW_HEIGHT           = VIEW_DIMENSION + GUI_HEIGHT_OFFSET;

    /** The images folder path */
    public static final String  IMG_PATH              = "/img/";

    /** The application icon path */
    public static final String  ICON_PATH             = IMG_PATH + "icon.png";

    /** The background path */
    public static final String  BG_PATH               = IMG_PATH + "bg.png";

    /** The logo path */
    public static final String  LOGO_PATH             = IMG_PATH + "logo.png";

    /** The games list title path */
    public static final String  GAMES_LIST_PATH       = IMG_PATH + "games_list.png";

    /** The available icon path */
    public static final String  AVAILABLE_ICON_PATH   = IMG_PATH + "available_icon.png";

    /** The invite icon path */
    public static final String  INVITE_ICON_PATH      = IMG_PATH + "invite_icon.png";

    /** The unavailable icon path */
    public static final String  UNAVAILABLE_ICON_PATH = IMG_PATH + "unavailable_icon.png";

    /** The game lobby title path */
    public static final String  GAME_LOBBY_PATH       = IMG_PATH + "game_lobby.png";

    /** The snake icon name */
    private static final String SNAKE_ICON_PATH       = "_snake.png";

    /** The green snake path */
    public static final String  GREEN_SNAKE_ICON_PATH = IMG_PATH + "green" + SNAKE_ICON_PATH;

    /** The red snake path */
    public static final String  RED_SNAKE_ICON_PATH   = IMG_PATH + "red" + SNAKE_ICON_PATH;

    /** The blue snake path */
    public static final String  BLUE_SNAKE_ICON_PATH  = IMG_PATH + "blue" + SNAKE_ICON_PATH;

    /** The black snake path */
    public static final String  BLACK_SNAKE_ICON_PATH = IMG_PATH + "black" + SNAKE_ICON_PATH;

    /** The sweet icon path */
    public static final String  SWEET_ICON_PATH       = IMG_PATH + "sweet.png";

    /** The sounds folder path */
    public static final String  SOUND_PATH            = "/sound/";

    /** The move sound path */
    public static final String  MOVE_PATH             = SOUND_PATH + "move.wav";

    /** The eat sound path */
    public static final String  EAT_PATH              = SOUND_PATH + "sweet.wav";

}
