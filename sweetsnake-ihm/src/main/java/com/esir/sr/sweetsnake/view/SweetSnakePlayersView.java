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
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.SweetSnakeImagePanel;
import com.esir.sr.sweetsnake.constants.SweetSnakeIhmConstants;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;

@Component("playersView")
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
    private SweetSnakePlayerDTO                   selectedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakePlayersView() {
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

        final List<SweetSnakePlayerDTO> players = client.getPlayersList();

        initPlayersJLT(players);
        centerPL.add(new JScrollPane(playersJLT));

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
        playersListIPL = new SweetSnakeImagePanel(SweetSnakeIhmConstants.PLAYERS_LIST_PATH);
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
    private void initPlayersJLT(final List<SweetSnakePlayerDTO> players) {
        playersJLTModel = new DefaultListModel<SweetSnakePlayerDTO>();
        for (final SweetSnakePlayerDTO player : players) {
            playersJLTModel.addElement(player);
        }
        playersJLT = new JList<SweetSnakePlayerDTO>(playersJLTModel);
        playersJLT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        playersJLT.setFocusable(true);
        playersJLT.addListSelectionListener(new ListSelectionListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void valueChanged(final ListSelectionEvent e) {
                // FIXME pourquoi ça marche que par le getSource et pas l'attribut direct ? :/ fait chier ce cast est trop laid
                selectedPlayer = ((JList<SweetSnakePlayerDTO>) e.getSource()).getSelectedValue();
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

    private class RefreshListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            playersJLTModel.removeAllElements();
            final List<SweetSnakePlayerDTO> players = client.getPlayersList();
            for (final SweetSnakePlayerDTO player : players) {
                playersJLTModel.addElement(player);
            }
            playersJLT.setFocusable(true);
        }
    }

    private class RequestGameListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            if (selectedPlayer == null) {
                JOptionPane.showMessageDialog(ihm, "Please select an opponent first", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                ihm.requestGame(selectedPlayer);
            }
        }
    }

}
