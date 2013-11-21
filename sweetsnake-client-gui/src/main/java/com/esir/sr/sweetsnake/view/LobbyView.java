package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class LobbyView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 7442409526614747493L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(LobbyView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The view title panel */
    private ImagePanel          lobbyIPL;

    /** The top panel */
    private JPanel              topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The players panels */
    private PlayerPanel[]       playersPL;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The quit button */
    private JButton             quitBTN;

    /** The ready button */
    private JButton             readyBTN;

    /** The start button */
    private JButton             startBTN;

    /** The waiting label */
    private JLabel              waitLB;

    /** The current player's number */
    private int                 playerNb;

    /** The players list */
    private List<PlayerDTO>     players;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected LobbyView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#init()
     */
    @Override
    @PostConstruct
    protected void init() {
        super.init();
        log.info("Initializing the Lobby View");
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#buildImpl()
     */
    @Override
    public void buildImpl() {
        setLayout(new BorderLayout());

        // top panel
        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initLobbyIPL();
        topPL.add(lobbyIPL);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initPlayersPL();
        for (final JPanel panel : playersPL) {
            centerPL.add(panel);
        }

        players = new LinkedList<PlayerDTO>();

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initQuitBTN();
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(quitBTN, gbc);

        initReadyBTN();
        gbc.gridx = 1;
        bottomPL.add(readyBTN, gbc);
    }

    /**
     * 
     */
    public void refreshPlayers() {
        for (final PlayerPanel panel : playersPL) {
            panel.removePlayer();
        }
        for (final PlayerDTO player : players) {
            final PlayerPanel playerPL = playersPL[player.getNumber() - 1];
            playerPL.refreshPlayer(player);
        }
    }

    /**
     * 
     * @param allReady
     * @param isStarted
     */
    // FIXME readyBTN can bug
    public void refreshButtons(final boolean allReady, final boolean isStarted) {
        final GridBagConstraints gbc = new GridBagConstraints();
        if (isStarted) {
            readyBTN.setEnabled(false);
        } else if (allReady) {
            bottomPL.remove(readyBTN);
            if (waitLB != null) {
                bottomPL.remove(waitLB);
                waitLB = null;
            }
            gbc.gridx = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(5, 0, 0, 0);
            if (players.get(0).getNumber() == playerNb) {
                initStartBTN();
                bottomPL.add(startBTN, gbc);
            } else {
                initWaitLB(players.get(0).getName());
                bottomPL.add(waitLB, gbc);
            }
        } else if (startBTN != null) {
            bottomPL.remove(startBTN);
            startBTN = null;
            gbc.gridx = 1;
            gbc.gridwidth = 1;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.NONE;
            gbc.insets = new Insets(5, 0, 0, 0);
            bottomPL.add(readyBTN, gbc);
            readyBTN.setEnabled(false);
        }
    }

    /**
     * 
     * @param _playerNb
     */
    public void setPlayerNb(final int _playerNb) {
        playerNb = _playerNb;
    }

    /**
     * 
     * @param _players
     */
    public void setPlayers(final List<PlayerDTO> _players) {
        players = new LinkedList<PlayerDTO>(_players);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTopPL() {
        topPL = new JPanel();
        topPL.setOpaque(false);
    }

    /**
     * 
     */
    private void initLobbyIPL() {
        lobbyIPL = new ImagePanel(ClientGuiConstants.GAME_LOBBY_PATH);
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new GridLayout(GameConstants.MAX_NUMBER_OF_PLAYERS / 2, GameConstants.MAX_NUMBER_OF_PLAYERS / 2));
    }

    /**
     * 
     */
    private void initPlayersPL() {
        playersPL = new PlayerPanel[GameConstants.MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < playersPL.length; i++) {
            playersPL[i] = new PlayerPanel(i + 1);
        }
    }

    /**
     * 
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new GridBagLayout());
        bottomPL.setOpaque(false);
    }

    /**
     * 
     */
    private void initQuitBTN() {
        quitBTN = new JButton("quit game");
        quitBTN.addActionListener(new QuitGameListener());
    }

    /**
     * 
     */
    private void initReadyBTN() {
        readyBTN = new JButton("ready !");
        readyBTN.addActionListener(new ReadyListener());
    }

    /**
     * 
     */
    private void initStartBTN() {
        startBTN = new JButton("start game");
        startBTN.addActionListener(new StartGameListener());
    }

    /**
     * 
     */
    private void initWaitLB(final String playerName) {
        waitLB = new JLabel("waiting for " + playerName + " to start the game");
        waitLB.setForeground(Color.white);
        waitLB.setFont(new Font("sans-serif", Font.PLAIN, 10));
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    private class PlayerPanel extends JPanel
    {

        /** The serial version UID */
        private static final long serialVersionUID = -5926630505331840276L;

        /** The player label */
        private final JLabel      playerLB;

        /** The icon label */
        private final JLabel      iconLB;

        /** The status label */
        private final JLabel      statusLB;

        /** The score label */
        private final JLabel      scoreLB;

        /**
         * 
         * @param nb
         */
        public PlayerPanel(final int nb) {
            final GridBagConstraints gbc = new GridBagConstraints();
            setLayout(new GridBagLayout());
            setOpaque(true);
            setBackground(findSnakeColor(nb));

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

        /**
         * 
         * @param player
         */
        public void refreshPlayer(final PlayerDTO player) {
            playerLB.setText(player.getName());
            playerLB.setFont(new Font("sans-serif", Font.BOLD, 25));
            playerLB.setForeground(Color.white);
            statusLB.setText("" + player.getStatus());
            iconLB.setIcon(new ImageIcon(LobbyView.class.getResource(findSnakeIconPath(player.getNumber()))));
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
         * @param i
         * @return
         */
        private Color findSnakeColor(final int i) { // TODO duplicated
            switch (i) {
                case 2:
                    return new Color(255, 0, 0, 50);
                case 3:
                    return new Color(12, 12, 235, 50);
                case 4:
                    return new Color(0, 0, 0, 50);
                default:
                    return new Color(39, 109, 31, 50);
            }
        }

        /**
         * 
         * @param nb
         * @return
         */
        private String findSnakeIconPath(final int nb) { // TODO duplicated
            switch (nb) {
                case 2:
                    return ClientGuiConstants.RED_SNAKE_ICON_PATH;
                case 3:
                    return ClientGuiConstants.BLUE_SNAKE_ICON_PATH;
                case 4:
                    return ClientGuiConstants.BLACK_SNAKE_ICON_PATH;
                default:
                    return ClientGuiConstants.GREEN_SNAKE_ICON_PATH;
            }
        }

    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class QuitGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.leaveSession();
        }

    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class ReadyListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.readyToPlay();
            readyBTN.setEnabled(false);
        }

    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class StartGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.startSession();
        }

    }

}
