package com.esir.sr.sweetsnake.uicomponent;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @param <E>
 */
public class SweetSnakeList<E> extends JList<E>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long         serialVersionUID = -7399464786914708398L;

    /** The logger */
    private static final Logger       log              = LoggerFactory.getLogger(SweetSnakeList.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The list model */
    private final DefaultListModel<E> model;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public SweetSnakeList() {
        super();
        model = new DefaultListModel<E>();
        setModel(model);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param element
     */
    public void addElement(final E element) {
        log.debug("Adding element {} to list", element);
        model.addElement(element);
    }

    /**
     * 
     * @param element
     */
    public void removeElement(final E element) {
        log.debug("Removing element {} to list", element);
        model.removeElement(element);
    }

    /**
     * 
     */
    public void removeAllElements() {
        log.debug("Removing all elements from list");
        model.removeAllElements();
    }

}
