package com.esir.sr.sweetsnake.constants;


public class SweetSnakeIhmConstants
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final int   IHM_OFFSET        = 10;

    /** */
    public static final int    IHM_WIDTH         = SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1) + IHM_OFFSET;

    /** */
    public static final int    IHM_HEIGHT        = SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1) + IHM_OFFSET;

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
