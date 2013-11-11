package com.esir.sr.sweetsnake.game.component;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Snake extends AbstractComponent
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Snake.class);

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public Snake() {
        super(ComponentType.SNAKE);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.game.SweetSnakeAbstractElement#move(com.esir.sr.sweetsnake.enumeration .Direction)
     */
    @Override
    public void move(final MoveDirection direction) {
        log.info("Moving snake to the {}", direction);
        x = (x + direction.getValue()[0] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        y = (y + direction.getValue()[1] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
    }

}
