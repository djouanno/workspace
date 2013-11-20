package com.esir.sr.sweetsnake.component;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @param <E>
 */
public abstract class AbstractList<E> extends JList<E>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long           serialVersionUID = -2006996730740714705L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The list model */
    protected final DefaultListModel<E> model;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected AbstractList() {
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
        model.addElement(element);
    }

    /**
     * 
     * @param element
     */
    public void removeElement(final E element) {
        model.removeElement(element);
    }

    /**
     * 
     */
    public void removeAllElements() {
        model.removeAllElements();
    }

}
