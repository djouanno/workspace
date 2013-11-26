package com.esir.sr.sweetsnake.component;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;

/**
 * This class graphically represents a sweet by extending the AbstractComponent class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Sweet extends AbstractComponent
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 2902520296967734181L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new sweet
     * 
     * @param _id
     *            The sweet id
     * @param _x
     *            The sweet X position
     * @param _y
     *            The sweet Y position
     */
    public Sweet(final String _id, final int _x, final int _y) {
        super(_id, _x, _y, ComponentType.SWEET, ClientGuiConstants.SWEET_ICON_PATH);
    }

}
