package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.uicomponent.ImagePanel;
import com.esir.sr.sweetsnake.uicomponent.SweetSnakeList;

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

    /** The selected player in the players list */
    private PlayerDTO                 selectedPlayer;

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

        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        initPlayersListIPL();
        topPL.add(playersListIPL);

        final List<PlayerDTO> players = client.getPlayersList();

        initPlayersLST(players);
        centerPL.add(playersLST);

        final GridBagConstraints gbc = new GridBagConstraints();

        initRefreshListBTN();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 0);
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
        centerPL.setBorder(new EmptyBorder(0, 0, 10, 0));
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
        playersLST.addListSelectionListener(new ListSelectionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                // FIXME pourquoi ça marche que par le getSource et pas l'attribut direct ? :/ fait chier ce cast est trop laid
                selectedPlayer = ((JList<PlayerDTO>) e.getSource()).getSelectedValue();
            }
        });
        // playersJLT.setOpaque(false);
        // ((javax.swing.DefaultListCellRenderer) playersJLT.getCellRenderer()).setOpaque(false);
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
            final List<PlayerDTO> players = client.getPlayersList();
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
            if (selectedPlayer == null) {
                JOptionPane.showMessageDialog(gui, "Please select an opponent first", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                gui.requestGame(selectedPlayer);
            }
        }

    }

}
