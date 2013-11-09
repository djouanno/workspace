package com.esir.sr.sweetsnake.game.element;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.enumeration.ElementType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

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

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Sweet.class);

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public Sweet() {
        super(ElementType.SWEET);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.game.SweetSnakeAbstractElement#move(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void move(final MoveDirection direction) {
        log.info("Moving sweet to the {}", direction);
    }

}
