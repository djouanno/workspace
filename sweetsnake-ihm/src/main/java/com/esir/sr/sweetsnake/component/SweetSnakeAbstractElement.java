package com.esir.sr.sweetsnake.component;

import java.awt.Graphics;

import javax.swing.JComponent;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeElement;
import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.constants.SweetSnakePropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeElementType;

public abstract class SweetSnakeAbstractElement extends JComponent implements ISweetSnakeElement
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long       serialVersionUID = 3748120944354885599L;
    private static final Logger     log              = LoggerFactory.getLogger(SweetSnakeAbstractElement.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    protected ISweetSnakeIhm        ihm;
    protected String                id;
    protected int                   x, y;
    protected SweetSnakeElementType type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeAbstractElement(final ISweetSnakeIhm _ihm) {
        super();
        id = RandomStringUtils.randomAlphanumeric(SweetSnakePropertiesConstants.GENERATED_ID_LENGTH);
        ihm = _ihm;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    public abstract void drawShape(Graphics g, int x, int y, int w, int h);

    @Override
    public void move(final SweetSnakeDirection direction) {
        x = (x + direction.getValue()[0] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
        y = (y + direction.getValue()[1] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
    }

    @Override
    public void paint(final Graphics g) {
        drawShape(g, SweetSnakeGameConstants.CELL_SIZE / 2 + x * SweetSnakeGameConstants.CELL_SIZE, SweetSnakeGameConstants.CELL_SIZE / 2 + y * SweetSnakeGameConstants.CELL_SIZE, SweetSnakeGameConstants.CELL_SIZE, SweetSnakeGameConstants.CELL_SIZE);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public SweetSnakeElementType getType() {
        return type;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void setXY(final int _x, final int _y) {
        x = _x;
        y = _y;
    }

    @Override
    public void setX(final int _x) {
        x = _x;

    }

    @Override
    public void setY(final int _y) {
        y = _y;
    }

}
