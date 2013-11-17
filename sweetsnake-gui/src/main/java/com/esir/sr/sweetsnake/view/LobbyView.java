package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.GuiConstants;
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
    private ImagePanel          playersListIPL;

    /** The top panel */
    private JPanel              topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The quit button */
    private JButton             quitBTN;

    /** The ready button */
    private JButton             readyBTN;

    /** The start button */
    private JButton             startBTN;

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
        log.info("Initializing a new LobbyView");
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

        initPlayersListIPL();
        topPL.add(playersListIPL);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        players = new LinkedList<PlayerDTO>();

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initQuitBTN();
        gbc.gridx = 0;
        gbc.gridy = 0;
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                centerPL.removeAll();
                final GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                for (final PlayerDTO player : players) {
                    log.debug("Player to add in lobby : {}", player);
                    final JLabel l = new JLabel(player.getNumber() + " ) " + player.getName() + " - " + player.getStatus());
                    l.setForeground(Color.white);
                    l.setFont(new Font("sans-serif", Font.BOLD, 16));
                    centerPL.add(l, gbc);
                    gbc.gridy++;
                }
                gui.refreshUI();
            }
        });
    }

    /**
     * 
     */
    public void refreshButtons(final boolean allReady) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (allReady) {
                    bottomPL.remove(readyBTN);

                    final GridBagConstraints gbc = new GridBagConstraints();
                    gbc.gridx = 1;
                    gbc.gridy = 0;
                    gbc.gridwidth = 1;
                    gbc.weightx = 1;
                    gbc.weighty = 1;
                    gbc.fill = GridBagConstraints.NONE;
                    gbc.insets = new Insets(5, 0, 0, 0);

                    if (players.get(0).getNumber() == playerNb) {
                        initStartBTN();
                        bottomPL.add(startBTN, gbc);
                    } else {
                        final JLabel label = new JLabel("waiting for " + players.get(0).getName() + " to start the game"); // TODO
                                                                                                                           // set
                                                                                                                           // fields
                                                                                                                           // to
                                                                                                                           // further
                                                                                                                           // remove
                        label.setForeground(Color.white);
                        label.setFont(new Font("sans-serif", Font.PLAIN, 10));
                        bottomPL.add(label, gbc);
                    }
                }
            }
        });
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
    private void initPlayersListIPL() {
        playersListIPL = new ImagePanel(GuiConstants.GAME_LOBBY_PATH);
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new GridBagLayout());
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
            gui.quitGame();
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
            gui.ready();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    readyBTN.setEnabled(false);
                }
            });
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
            gui.startGame();
        }

    }

}
