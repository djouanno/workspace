package com.esir.sr.sweetsnake.component;

import java.awt.Color;
import java.awt.Graphics;

import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeElementType;

public class SweetSnakeSweet extends SweetSnakeAbstractElement
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 2902520296967734181L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeSweet(final ISweetSnakeIhm ihm) {
        super(ihm);
        type = SweetSnakeElementType.SWEET;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void drawShape(final Graphics g, final int x, final int y, final int w, final int h) {
        g.setColor(Color.RED);
        g.fillOval(x, y, w, h);
    }

}
