package com.esir.sr.sweetsnake.component;

import java.awt.Color;
import java.awt.Graphics;

import javax.annotation.PostConstruct;

import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeElementType;

public class SweetSnakeSnake extends SweetSnakeAbstractElement
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long serialVersionUID = 3698273223538543595L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeSnake(final ISweetSnakeIhm ihm) {
        super(ihm);
        type = SweetSnakeElementType.SWEET;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        System.out.println("IHM IS NULL : " + (ihm == null));
    }

    @Override
    public void drawShape(final Graphics g, final int x, final int y, final int w, final int h) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, w, h);
    }

}
