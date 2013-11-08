package com.esir.sr.sweetsnake.uicomponent;

import java.awt.Color;
import java.awt.Graphics;

import com.esir.sr.sweetsnake.api.IGui;
import com.esir.sr.sweetsnake.enumeration.ElementType;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Sweet extends AbstractElement
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
     * 
     * @param ihm
     */
    public Sweet(final IGui ihm) {
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
        g.setColor(Color.RED);
        g.fillOval(x, y, w, h);
    }

}
