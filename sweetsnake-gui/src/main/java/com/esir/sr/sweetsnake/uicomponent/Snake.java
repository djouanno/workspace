package com.esir.sr.sweetsnake.uicomponent;

import java.awt.Color;
import java.awt.Graphics;

import com.esir.sr.sweetsnake.api.IGui;
import com.esir.sr.sweetsnake.enumeration.ElementType;

public class Snake extends AbstractElement
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 3698273223538543595L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param ihm
     */
    public Snake(final IGui ihm) {
        super(ihm);
        type = ElementType.SWEET;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.component.SweetSnakeAbstractElement#drawShape(java.awt.Graphics, int, int, int, int)
     */
    @Override
    public void drawShape(final Graphics g, final int x, final int y, final int w, final int h) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, w, h);
    }

}
