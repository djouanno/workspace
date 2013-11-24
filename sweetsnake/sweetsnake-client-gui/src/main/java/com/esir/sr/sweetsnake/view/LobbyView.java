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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.PlayerPanel;
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

    /** The players view */
    @Autowired
    private PlayersView         playersView;

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

    /** The invite button */
    private JButton             inviteBTN;

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
    protected void buildImpl() {
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

        centerPL.add(playersPL[0]);
        centerPL.add(playersPL[3]);
        centerPL.add(playersPL[2]);
        centerPL.add(playersPL[1]);

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

        initInviteBTN();
        gbc.gridx = 1;
        bottomPL.add(inviteBTN, gbc);
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
     * @param isStarted
     */
    public void refreshButtons(final boolean isStarted) {
        final GridBagConstraints gbc = new GridBagConstraints();
        if (startBTN != null) {
            bottomPL.remove(startBTN);
            startBTN = null;
        }
        if (waitLB != null) {
            bottomPL.remove(waitLB);
            waitLB = null;
        }
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 0, 0);
        if (players.get(0).getNumber() == playerNb) {
            initStartBTN();
            bottomPL.add(startBTN, gbc);
        } else {
            initWaitLB(isStarted, players.get(0).getName());
            bottomPL.add(waitLB, gbc);
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
    private void initInviteBTN() {
        inviteBTN = new JButton("invite");
        inviteBTN.addActionListener(new InviteListener());
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
     * @param isStarted
     * @param playerName
     */
    private void initWaitLB(final boolean isStarted, final String playerName) {
        if (isStarted) {
            waitLB = new JLabel("waiting for the game to end");
        } else {
            waitLB = new JLabel("waiting for " + playerName + " to start the game");
        }
        waitLB.setForeground(Color.white);
        waitLB.setFont(new Font("sans-serif", Font.PLAIN, 10));
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

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
    private class InviteListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final JDialog dialog = new JDialog(gui, true);
            playersView.setDialog(dialog);
            dialog.setTitle("Connected players");
            dialog.setSize(playersView.getDimension());
            dialog.add(playersView);
            dialog.setLocationRelativeTo(gui);
            dialog.setVisible(true);
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
