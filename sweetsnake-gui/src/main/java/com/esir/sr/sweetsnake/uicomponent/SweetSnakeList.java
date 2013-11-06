package com.esir.sr.sweetsnake.uicomponent;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import org.slf4j.LoggerFactory;

public class SweetSnakeList<E> extends JList<E>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -7399464786914708398L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeList.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final DefaultListModel<E>     model;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeList() {
        super();
        model = new DefaultListModel<E>();
        setModel(model);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    public void addElement(final E element) {
        log.debug("Adding element {} to list", element);
        model.addElement(element);
    }

    public void removeElement(final E element) {
        log.debug("Removing element {} to list", element);
        model.removeElement(element);
    }

    public void removeAllElements() {
        log.debug("Removing all elements from list");
        model.removeAllElements();
    }

}
