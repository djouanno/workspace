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
 * This class extends the JPanel class in order to provide a panel with a common layout for all players in a game lobby.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JPanel
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
     * Creates a new player panel
     * 
     * @param _playerNb
     *            The player's number in the game session
     */
    public PlayerPanel(final int playerNb) {
        final GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());
        setOpaque(false);
        setBorder(generateBorder(playerNb));

        playerLB = generateLabel("Available", 20, Color.gray, Font.ITALIC);
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(25, 0, 0, 0);
        add(playerLB, gbc);

        iconLB = generateLabel("", 15, Color.black, Font.PLAIN);
        gbc.gridy = 1;
        gbc.weighty = 500;
        gbc.insets = new Insets(10, 0, 0, 0);
        add(iconLB, gbc);

        statusLB = generateLabel("", 15, Color.black, Font.ITALIC);
        gbc.gridy = 2;
        gbc.weighty = 500;
        gbc.anchor = GridBagConstraints.NORTH;
        add(statusLB, gbc);

        scoreLB = generateLabel("", 15, Color.black, Font.BOLD);
        gbc.gridy = 3;
        gbc.weighty = 1000;
        add(scoreLB, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method refreshes the panel with the specified player
     * 
     * @param player
     *            The player to display on the panel
     */
    public void refreshPlayer(final PlayerDTO player) {
        playerLB.setText(player.getName());
        playerLB.setFont(new Font("sans-serif", Font.BOLD, 25));
        playerLB.setForeground(Color.black);
        statusLB.setText("" + player.getStatus());
        iconLB.setIcon(new ImageIcon(LobbyView.class.getResource(Snake.findSnakeIconPath(player.getNumber()))));
        scoreLB.setText("Score : " + player.getScore());
    }

    /**
     * This method remove any player displayed on the panel
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
     * This method generates a label with the specified parameters
     * 
     * @param text
     *            The label text
     * @param fontSize
     *            The font size
     * @param fontColor
     *            The font color
     * @param fontWeight
     *            The font weight
     * @return A JLabel with the specified text and parameters
     */
    private JLabel generateLabel(final String text, final int fontSize, final Color color, final int fontWeight) {
        final JLabel label = new JLabel(text);
        label.setForeground(color);
        label.setFont(new Font("sans-serif", fontWeight, fontSize));
        return label;
    }

    /**
     * This method generate the panel border according to the specified player's number in the game session
     * 
     * @param nb
     *            The player's number in the game session
     * @return A border generated for the specified player's number in the game session
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
