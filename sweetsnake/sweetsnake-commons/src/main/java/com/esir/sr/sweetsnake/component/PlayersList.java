package com.esir.sr.sweetsnake.component;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * This class provides all the behavior for using a JList containing PlayerDTO objects.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.component.AbstractList
 */
public class PlayersList extends AbstractList<PlayerDTO>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -7399464786914708398L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new players list
     */
    public PlayersList() {
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
            private static final long serialVersionUID = 5859407590339448327L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int,
             * boolean, boolean)
             */
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final PlayerDTO player = (PlayerDTO) value;
                ImageIcon imageIcon = new ImageIcon(PlayersList.class.getResource(ClientGuiConstants.UNAVAILABLE_ICON_PATH));
                if (player.getStatus() == PlayerStatus.AVAILABLE) {
                    imageIcon = new ImageIcon(PlayersList.class.getResource(ClientGuiConstants.AVAILABLE_ICON_PATH));
                }
                if (player.getStatus() == PlayerStatus.READY || player.getStatus() == PlayerStatus.INVITED || player.getStatus() == PlayerStatus.WINNER || player.getStatus() == PlayerStatus.LOSER) {
                    imageIcon = new ImageIcon(PlayersList.class.getResource(ClientGuiConstants.INVITE_ICON_PATH));
                }
                label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(3, 10, 3, 10)));
                label.setFont(new Font("sans-serif", Font.PLAIN, 16));
                label.setIcon(imageIcon);
                return label;
            }
        });
    }

}
