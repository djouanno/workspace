package com.esir.sr.sweetsnake.constants;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ClientGuiConstants
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** */
    public static final int     GUI_DIMENSION         = 420;

    /** **/
    public static final int     GUI_OFFSET            = 10;

    /** */
    public static final int     GUI_HEIGHT_OFFSET     = 106;

    /** */
    public static final int     GUI_WIDTH             = GUI_DIMENSION + GUI_OFFSET;

    /** */
    public static final int     GUI_HEIGHT            = GUI_DIMENSION + GUI_OFFSET + GUI_HEIGHT_OFFSET;

    /** */
    public static final int     VIEW_WIDTH            = GUI_WIDTH - GUI_OFFSET;

    /** */
    public static final int     VIEW_HEIGHT           = GUI_HEIGHT - GUI_OFFSET;

    /** */
    public static final String  IMG_PATH              = "/img/";

    /** */
    public static final String  BG_PATH               = IMG_PATH + "bg.png";

    /** */
    public static final String  LOGO_PATH             = IMG_PATH + "logo.png";

    /** */
    public static final String  PLAYERS_LIST_PATH     = IMG_PATH + "players_list.png";

    /** */
    public static final String  AVAILABLE_ICON_PATH   = IMG_PATH + "available_icon.png";

    /** */
    public static final String  INVITE_ICON_PATH      = IMG_PATH + "invite_icon.png";

    /** */
    public static final String  UNAVAILABLE_ICON_PATH = IMG_PATH + "unavailable_icon.png";

    /** */
    public static final String  GAME_LOBBY_PATH       = IMG_PATH + "game_lobby.png";

    /** */
    private static final String SNAKE_ICON_PATH       = "_snake.png";

    /** The green snake color */
    public static final String  GREEN_SNAKE_ICON_PATH = IMG_PATH + "green" + SNAKE_ICON_PATH;

    /** The red snake color */
    public static final String  RED_SNAKE_ICON_PATH   = IMG_PATH + "red" + SNAKE_ICON_PATH;

    /** The blue snake color */
    public static final String  BLUE_SNAKE_ICON_PATH  = IMG_PATH + "blue" + SNAKE_ICON_PATH;

    /** The black snake color */
    public static final String  BLACK_SNAKE_ICON_PATH = IMG_PATH + "black" + SNAKE_ICON_PATH;

    /** */
    public static final String  SWEET_ICON_PATH       = IMG_PATH + "sweet.png";

}
