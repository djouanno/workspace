package com.esir.sr.sweetsnake.constants;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GuiConstants
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** */
    public static final int    GUI_DIMENSION         = 420;

    /** **/
    public static final int    GUI_OFFSET            = 10;

    /** */
    public static final int    GUI_HEIGHT_OFFSET     = 112;

    /** */
    public static final int    GUI_WIDTH             = GUI_DIMENSION + GUI_OFFSET;

    /** */
    public static final int    GUI_HEIGHT            = GUI_DIMENSION + GUI_OFFSET + GUI_HEIGHT_OFFSET;

    /** */
    public static final int    VIEW_WIDTH            = GUI_WIDTH - GUI_OFFSET;

    /** */
    public static final int    VIEW_HEIGHT           = GUI_HEIGHT - GUI_OFFSET;

    /** */
    public static final String IMG_PATH              = "/img/";

    /** */
    public static final String BG_PATH               = IMG_PATH + "bg.png";

    /** */
    public static final String LOGO_PATH             = IMG_PATH + "logo.png";

    /** */
    public static final String PLAYERS_LIST_PATH     = IMG_PATH + "players_list.png";

    /** */
    public static final String GAME_ON_PATH          = IMG_PATH + "game_on.png";

    /** */
    public static final String GREEN_SNAKE_ICON_PATH = IMG_PATH + "snake_green.png";

    /** The green snake color */
    public static final String GREEN_SNAKE_VALUE     = "green";

    /** */
    public static final String RED_SNAKE_ICON_PATH   = IMG_PATH + "snake_red.png";

    /** The red snake color */
    public static final String RED_SNAKE_VALUE       = "red";

    /** */
    public static final String SWEET_ICON_PATH       = IMG_PATH + "sweet.png";

}
