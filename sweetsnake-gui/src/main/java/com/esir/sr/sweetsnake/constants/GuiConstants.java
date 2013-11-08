package com.esir.sr.sweetsnake.constants;


public class GuiConstants
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** **/
    public static final int    IHM_OFFSET        = 10;

    /** */
    public static final int    IHM_WIDTH         = GameConstants.CELL_SIZE * (GameConstants.GRID_SIZE + 1) + IHM_OFFSET;

    /** */
    public static final int    IHM_HEIGHT        = GameConstants.CELL_SIZE * (GameConstants.GRID_SIZE + 1) + IHM_OFFSET;

    /** */
    public static final int    VIEW_WIDTH        = IHM_WIDTH - IHM_OFFSET;

    /** */
    public static final int    VIEW_HEIGHT       = IHM_HEIGHT - IHM_OFFSET;

    /** */
    public static final String IMG_PATH          = "/img/";

    /** */
    public static final String BG_PATH           = IMG_PATH + "bg.png";

    /** */
    public static final String LOGO_PATH         = IMG_PATH + "logo.png";

    /** */
    public static final String PLAYERS_LIST_PATH = IMG_PATH + "players_list.png";

}
