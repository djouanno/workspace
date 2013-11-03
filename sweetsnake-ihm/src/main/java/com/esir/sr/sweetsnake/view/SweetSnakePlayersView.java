package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.SweetSnakeImagePanel;
import com.esir.sr.sweetsnake.constants.SweetSnakeIhmConstants;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;

@Component
public class SweetSnakePlayersView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long                     serialVersionUID = -5820091417435340407L;
    private static final org.slf4j.Logger         log              = LoggerFactory.getLogger(SweetSnakePlayersView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private SweetSnakeImagePanel                  playersListIPL;
    private JPanel                                topPL, centerPL, bottomPL;
    private JList<SweetSnakePlayerDTO>            playersJLT;
    private DefaultListModel<SweetSnakePlayerDTO> playersJLTModel;
    private JButton                               refreshListBTN, requestBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakePlayersView() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] INIT METHOD
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initializing a new SweetSnakePlayersView");
    }

    @Override
    public void build() {
        setLayout(new BorderLayout());

        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        initPlayersListIPL();
        topPL.add(playersListIPL);

        final List<SweetSnakePlayerDTO> players = client.getPlayersList();
        /*
         * final List<SweetSnakePlayerDTO> players = new LinkedList<SweetSnakePlayerDTO>(); players.add(new
         * SweetSnakePlayerDTO("player1", SweetSnakePlayerStatus.AVAILABLE)); players.add(new SweetSnakePlayerDTO("player2",
         * SweetSnakePlayerStatus.PLAYING));
         */

        initPlayersJLT(players);
        centerPL.add(playersJLT);

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

    private void initTopPL() {
        topPL = new JPanel();
        topPL.setOpaque(false);
    }

    private void initPlayersListIPL() {
        playersListIPL = new SweetSnakeImagePanel(SweetSnakeIhmConstants.PLAYERS_LIST_PATH);
    }

    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new BorderLayout());
        centerPL.setBorder(new EmptyBorder(0, 0, 10, 0));
    }

    private void initPlayersJLT(final List<SweetSnakePlayerDTO> players) {
        playersJLTModel = new DefaultListModel<SweetSnakePlayerDTO>();
        for (final SweetSnakePlayerDTO player : players) {
            playersJLTModel.addElement(player);
        }
        playersJLT = new JList<SweetSnakePlayerDTO>(playersJLTModel);
        // playersJLT.setOpaque(false);
        // ((javax.swing.DefaultListCellRenderer) playersJLT.getCellRenderer()).setOpaque(false);
    }

    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new GridBagLayout());
        bottomPL.setOpaque(false);
    }

    private void initRefreshListBTN() {
        refreshListBTN = new JButton("refresh list");
        refreshListBTN.addActionListener(new RefreshListener());
    }

    private void initRequestBTN() {
        requestBTN = new JButton("request game");
        requestBTN.addActionListener(new RequestGameListener());
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    private class RefreshListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            playersJLTModel.removeAllElements();
            final List<SweetSnakePlayerDTO> players = client.getPlayersList();
            for (final SweetSnakePlayerDTO player : players) {
                playersJLTModel.addElement(player);
            }
        }
    }

    private class RequestGameListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            // FIXME does not work :/
            log.debug("selected index : {}", playersJLT.getSelectedIndex());
            log.debug("selected value : {}", playersJLT.getSelectedValue());
            log.debug("model size : {}", playersJLTModel.size());
            final SweetSnakePlayerDTO requestedPlayer = playersJLTModel.get(playersJLT.getSelectedIndex());
            if (requestedPlayer == null) {
                JOptionPane.showMessageDialog((java.awt.Component) ihm, "Please select an opponent first", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ihm.requestGame(requestedPlayer);
            }
        }
    }

}
