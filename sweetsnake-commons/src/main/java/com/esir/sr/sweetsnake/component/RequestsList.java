package com.esir.sr.sweetsnake.component;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @param <GameSessionDTO>
 */
public class RequestsList extends AbstractList<GameRequestDTO>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 7652211078417370917L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public RequestsList() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.component.AbstractList#enableSelection()
     */
    @Override
    public void enableSelection() {
        setCellRenderer(new DefaultListCellRenderer() {

            /** The serial version UID */
            private static final long serialVersionUID = -5285498774275432881L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int,
             * boolean, boolean)
             */
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final ImageIcon imageIcon = new ImageIcon(RequestsList.class.getResource(ClientGuiConstants.AVAILABLE_ICON_PATH));
                label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(3, 10, 3, 10)));
                label.setFont(new Font("sans-serif", Font.PLAIN, 16));
                label.setIcon(imageIcon);
                return label;
            }

        });
    }

}
