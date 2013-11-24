package com.esir.sr.sweetsnake.component;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.view.LobbyView;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerPanel extends JPanel
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -5926630505331840276L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The player label */
    private final JLabel      playerLB;

    /** The icon label */
    private final JLabel      iconLB;

    /** The status label */
    private final JLabel      statusLB;

    /** The score label */
    private final JLabel      scoreLB;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     * @param _playerNb
     */
    public PlayerPanel(final int playerNb) {
        final GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(generateBorder(playerNb));

        playerLB = generateLabel("Available", 15, Color.gray, Font.ITALIC);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(25, 0, 0, 0);
        add(playerLB, gbc);

        iconLB = generateLabel("", 15, Color.white, Font.PLAIN);
        gbc.gridy = 1;
        gbc.weighty = 500;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(iconLB, gbc);

        statusLB = generateLabel("", 15, Color.white, Font.ITALIC);
        gbc.gridy = 2;
        gbc.weighty = 500;
        gbc.anchor = GridBagConstraints.NORTH;
        add(statusLB, gbc);

        scoreLB = generateLabel("", 15, Color.white, Font.BOLD);
        gbc.gridy = 3;
        gbc.weighty = 1000;
        add(scoreLB, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param player
     */
    public void refreshPlayer(final PlayerDTO player) {
        playerLB.setText(player.getName());
        playerLB.setFont(new Font("sans-serif", Font.BOLD, 25));
        playerLB.setForeground(Color.white);
        statusLB.setText("" + player.getStatus());
        iconLB.setIcon(new ImageIcon(LobbyView.class.getResource(Snake.findSnakeIconPath(player.getNumber()))));
        scoreLB.setText("Score : " + player.getScore());
    }

    /**
     * 
     */
    public void removePlayer() {
        playerLB.setText("Available");
        playerLB.setFont(new Font("sans-serif", Font.ITALIC, 15));
        playerLB.setForeground(Color.gray);
        statusLB.setText("");
        iconLB.setIcon(null);
        scoreLB.setText("");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param text
     * @param fontSize
     * @param fontColor
     * @param fontWeight
     * @return
     */
    private JLabel generateLabel(final String text, final int fontSize, final Color color, final int fontWeight) {
        final JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("sans-serif", fontWeight, fontSize));
        return label;
    }

    /**
     * 
     * @param nb
     * @return
     */
    private MatteBorder generateBorder(final int nb) {
        switch (nb) {
            case 4:
                return BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);
            case 3:
                return BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray);
            case 2:
                return BorderFactory.createMatteBorder(0, 0, 0, 0, Color.gray);
            default:
                return BorderFactory.createMatteBorder(0, 0, 1, 1, Color.gray);
        }
    }

}
