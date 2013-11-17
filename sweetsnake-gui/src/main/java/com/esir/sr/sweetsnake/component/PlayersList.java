package com.esir.sr.sweetsnake.component;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.view.PlayersView;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @param <E>
 */
public class PlayersList<E> extends JList<E>
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long         serialVersionUID = -7399464786914708398L;

    /** The logger */
    private static final Logger       log              = LoggerFactory.getLogger(PlayersList.class);

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
    public PlayersList() {
        super();
        model = new DefaultListModel<E>();
        setModel(model);

        setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = 3305919865692499917L;

            @Override
            public void setSelectionInterval(final int index0, final int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });

        setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 5859407590339448327L;

            @SuppressWarnings("rawtypes")
            @Override
            public java.awt.Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final PlayerDTO player = (PlayerDTO) value;
                ImageIcon imageIcon = new ImageIcon(PlayersView.class.getResource(GuiConstants.UNAVAILABLE_ICON_PATH));
                if (player.getStatus() == PlayerStatus.AVAILABLE) {
                    imageIcon = new ImageIcon(PlayersView.class.getResource(GuiConstants.AVAILABLE_ICON_PATH));
                }
                if (player.getStatus() == PlayerStatus.PRESENT || player.getStatus() == PlayerStatus.INVITED) {
                    imageIcon = new ImageIcon(PlayersView.class.getResource(GuiConstants.INVITE_ICON_PATH));
                }
                label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(3, 10, 3, 10)));
                label.setFont(new Font("sans-serif", Font.PLAIN, 16));
                label.setIcon(imageIcon);
                return label;
            }
        });
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
