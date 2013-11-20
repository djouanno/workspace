package com.esir.sr.sweetsnake.component;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
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

    /**
     * 
     */
    public abstract void enableSelection();

    /**
     * 
     */
    public void disableSelection() {
        setCellRenderer(new DefaultListCellRenderer() {

            /** The serial version UID */
            private static final long serialVersionUID = 4903155214602658319L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int,
             * boolean, boolean)
             */
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, false, false);
                label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(3, 10, 3, 10)));
                label.setFont(new Font("sans-serif", Font.PLAIN, 16));
                return this;
            }
        });
    }

}
