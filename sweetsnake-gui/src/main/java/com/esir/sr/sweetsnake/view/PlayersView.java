package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.SweetSnakeList;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class PlayersView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long         serialVersionUID = -5820091417435340407L;

    /** The logger */
    private static final Logger       log              = LoggerFactory.getLogger(PlayersView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The view title panel */
    private ImagePanel                playersListIPL;

    /** The top panel */
    private JPanel                    topPL;

    /** The center panel */
    private JPanel                    centerPL;

    /** The bottom panel */
    private JPanel                    bottomPL;

    /** The players list */
    private SweetSnakeList<PlayerDTO> playersLST;

    /** The refresh button */
    private JButton                   refreshListBTN;

    /** The request button */
    private JButton                   requestBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected PlayersView() {
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
        log.info("Initializing a new SweetSnakePlayersView");
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

        List<PlayerDTO> players = new LinkedList<PlayerDTO>();
        if (client != null) {
            // Collections.copy(players, client.getPlayersList());
            players = new LinkedList<PlayerDTO>(client.getPlayersList());
        }

        initPlayersLST(players);
        centerPL.add(playersLST);

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initRefreshListBTN();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(refreshListBTN, gbc);

        initRequestBTN();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        bottomPL.add(requestBTN, gbc);
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
        playersListIPL = new ImagePanel(GuiConstants.PLAYERS_LIST_PATH);
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new BorderLayout());
    }

    /**
     * 
     * @param players
     */
    private void initPlayersLST(final List<PlayerDTO> players) {
        playersLST = new SweetSnakeList<PlayerDTO>();
        for (final PlayerDTO player : players) {
            playersLST.addElement(player);
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
    private void initRefreshListBTN() {
        refreshListBTN = new JButton("refresh list");
        refreshListBTN.addActionListener(new RefreshListener());
    }

    /**
     * 
     */
    private void initRequestBTN() {
        requestBTN = new JButton("request game");
        requestBTN.addActionListener(new RequestGameListener());
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
    private class RefreshListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            playersLST.removeAllElements();
            List<PlayerDTO> players = new LinkedList<PlayerDTO>();
            if (client != null) {
                // Collections.copy(players, client.getPlayersList());
                players = new LinkedList<PlayerDTO>(client.getPlayersList());
            }
            for (final PlayerDTO player : players) {
                playersLST.addElement(player);
            }
        }
    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class RequestGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final PlayerDTO selectedPlayer = playersLST.getSelectedValue();
            if (selectedPlayer == null) {
                gui.displayErrorMessage("Please select an opponent first");
            } else {
                log.debug("selected : {}", selectedPlayer);
                gui.requestGame(selectedPlayer);
            }
        }

    }

}
