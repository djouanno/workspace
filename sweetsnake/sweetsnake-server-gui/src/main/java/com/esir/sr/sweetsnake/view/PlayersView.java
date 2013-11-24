package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.PlayersList;
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
    private static final long   serialVersionUID = 5379038496604386022L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(PlayersView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The title label */
    private JLabel              titleLB;

    /** The center panel */
    private JPanel              centerPL;

    /** The players list */
    private PlayersList         playersLST;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The kick button */
    private JButton             kickBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public PlayersView() {
        super();
        log.info("Initializing the Players View");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#buildImpl()
     */
    @Override
    protected void buildImpl() {
        setLayout(new BorderLayout());

        initTitleLB();
        add(titleLB, BorderLayout.NORTH);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initPlayersLST();
        centerPL.add(new JScrollPane(playersLST), BorderLayout.CENTER);

        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        initKickBTN();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        bottomPL.add(kickBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param _players
     */
    public void refreshPlayers(final List<PlayerDTO> _players) {
        playersLST.removeAllElements();
        final List<PlayerDTO> players = new LinkedList<PlayerDTO>(_players);

        if (players.isEmpty()) {
            playersLST.disableSelection();
            playersLST.addElement(new PlayerDTO("No available player for the moment", null, null, 0, 0));
        } else {
            playersLST.enableSelection();
            for (final PlayerDTO player : players) {
                playersLST.addElement(player);
            }
        }

        titleLB.setText("Connected players (" + players.size() + ")");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTitleLB() {
        titleLB = new JLabel("Connected players (0)");
        titleLB.setFont(new Font("sans-serif", Font.BOLD, 14));
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel(new BorderLayout());
        centerPL.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * 
     */
    private void initBottomPL() {
        bottomPL = new JPanel(new GridBagLayout());
    }

    /**
     * 
     */
    private void initKickBTN() {
        kickBTN = new JButton("kick player");
        kickBTN.addActionListener(new KickListener());
    }

    /**
     * 
     */
    private void initPlayersLST() {
        playersLST = new PlayersList();
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
    private class KickListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final PlayerDTO player = playersLST.getSelectedValue();
            if (player != null) {
                server.kickPlayer(player);
            }
        }

    }

}
