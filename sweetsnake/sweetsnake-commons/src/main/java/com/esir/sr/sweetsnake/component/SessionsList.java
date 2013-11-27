package com.esir.sr.sweetsnake.component;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;

/**
 * This class provides all the behavior for using a JList containing GameSessionDTO objects.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.component.AbstractList
 */
public class SessionsList extends AbstractList<GameSessionDTO>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 5216550925678462554L;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new sessions list
     */
    public SessionsList() {
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
            private static final long serialVersionUID = -2017430119132035510L;

            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.DefaultListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int,
             * boolean, boolean)
             */
            @Override
            public Component getListCellRendererComponent(final JList<?> list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final GameSessionDTO session = (GameSessionDTO) value;
                ImageIcon imageIcon = new ImageIcon(SessionsList.class.getResource(ClientGuiConstants.AVAILABLE_ICON_PATH));
                if (session.isStarted()) {
                    imageIcon = new ImageIcon(SessionsList.class.getResource(ClientGuiConstants.INVITE_ICON_PATH));
                }
                if (session.getPlayersDto().size() >= GameConstants.MAX_NUMBER_OF_PLAYERS) {
                    imageIcon = new ImageIcon(SessionsList.class.getResource(ClientGuiConstants.UNAVAILABLE_ICON_PATH));
                }
                label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(3, 10, 3, 10)));
                label.setFont(new Font("sans-serif", Font.PLAIN, 16));
                label.setIcon(imageIcon);
                return label;
            }
        });
    }

}
