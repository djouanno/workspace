package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JDialog;
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
 * This class graphically reprents the lobby view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
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

    /** The waiting button */
    private JButton             waitBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new lobby view
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

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initQuitBTN();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1000;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        bottomPL.add(quitBTN, gbc);

        initInviteBTN();
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(10, 0, 0, 5);
        bottomPL.add(inviteBTN, gbc);

        initStartBTN();
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(10, 0, 0, 0);
        bottomPL.add(startBTN, gbc);
    }

    /**
     * This method refresh the player panels
     * 
     * @param players
     *            The list containing the DTO representing all the players
     */
    public void refreshPlayers(final List<PlayerDTO> players) {
        for (final PlayerPanel panel : playersPL) {
            panel.removePlayer();
        }
        for (final PlayerDTO player : players) {
            final PlayerPanel playerPL = playersPL[player.getNumber() - 1];
            playerPL.refreshPlayer(player);
        }
    }

    /**
     * This method refresh the buttons
     * 
     * @param leaderName
     *            The name of the leader player
     * @param isStarted
     *            True if the session is started, false otherwise
     */
    public void refreshButtons(final String leaderName, final boolean isStarted) {
        final GridBagConstraints gbc = new GridBagConstraints();
        if (startBTN != null) {
            bottomPL.remove(startBTN);
            startBTN = null;
        }
        if (waitBTN != null) {
            bottomPL.remove(waitBTN);
            waitBTN = null;
        }
        gbc.gridx = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 0.1;
        gbc.weighty = 1;
        gbc.insets = new Insets(10, 0, 0, 0);
        if (leaderName.equals(client.getUsername())) {
            initStartBTN();
            bottomPL.add(startBTN, gbc);
        } else {
            initWaitBTN(isStarted, leaderName);
            bottomPL.add(waitBTN, gbc);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This methods initializes the top panel
     */
    private void initTopPL() {
        topPL = new JPanel();
        topPL.setOpaque(false);
    }

    /**
     * This methods initializes the lobby image panel
     */
    private void initLobbyIPL() {
        lobbyIPL = new ImagePanel(ClientGuiConstants.GAME_LOBBY_PATH);
    }

    /**
     * This methods initializes the center panel
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new GridLayout(GameConstants.MAX_NUMBER_OF_PLAYERS / 2, GameConstants.MAX_NUMBER_OF_PLAYERS / 2));
    }

    /**
     * This methods initializes the players panel
     */
    private void initPlayersPL() {
        playersPL = new PlayerPanel[GameConstants.MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < playersPL.length; i++) {
            playersPL[i] = new PlayerPanel(i + 1);
        }
    }

    /**
     * This methods initializes the bottom pannel
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new GridBagLayout());
        bottomPL.setOpaque(false);
    }

    /**
     * This methods initializes the quit button
     */
    private void initQuitBTN() {
        quitBTN = new JButton("quit game");
        quitBTN.addActionListener(new QuitGameListener());
    }

    /**
     * This methods initializes the invite button
     */
    private void initInviteBTN() {
        inviteBTN = new JButton("invite");
        inviteBTN.addActionListener(new InviteListener());
    }

    /**
     * This methods initializes the start button
     */
    private void initStartBTN() {
        startBTN = new JButton("start game");
        startBTN.addActionListener(new StartGameListener());
    }

    /**
     * This methods initializes the wait button
     * 
     * @param isStarted
     *            True if the game session is started, false otherwise
     * @param playerName
     *            The head playername
     */
    private void initWaitBTN(final boolean isStarted, final String playerName) {
        if (isStarted) {
            waitBTN = new JButton("waiting for the game to end");
        } else {
            waitBTN = new JButton("waiting for " + playerName + " to start the game");
        }
        waitBTN.setEnabled(false);
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * This class provides an action listener for the quit button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
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
     * This class provides an action listener for the invite button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
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
     * This class provides an action listener for the start button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
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
