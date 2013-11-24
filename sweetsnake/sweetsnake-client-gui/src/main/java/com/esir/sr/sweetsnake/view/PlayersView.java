package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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
    private static final long   serialVersionUID = -5820091417435340407L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(PlayersView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The center panel */
    private JPanel              centerPL;

    /** The players list */
    private PlayersList         playersLST;

    /** The parent dialog */
    private JDialog             dialog;

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
        log.info("Initializing the Players View");
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
        setOpaque(true);
        setBackground(Color.black);

        dimension = new Dimension(350, 350);
        setSize(dimension);
        setPreferredSize(dimension);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        final List<PlayerDTO> players = new LinkedList<PlayerDTO>();

        initPlayersLST(players);
        centerPL.add(new JScrollPane(playersLST));
    }

    /**
     * 
     * @param playersList
     */
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        if (isBuilded) {
            playersLST.removeAllElements();
            final List<PlayerDTO> players = new LinkedList<PlayerDTO>(playersList);

            if (players.isEmpty()) {
                playersLST.disableSelection();
                playersLST.addElement(new PlayerDTO("No available player for the moment", null, null, 0, 0));
            } else {
                playersLST.enableSelection();
                for (final PlayerDTO player : players) {
                    playersLST.addElement(player);
                }
            }
        }
    }

    /**
     * 
     * @param _dialog
     */
    public void setDialog(final JDialog _dialog) {
        dialog = _dialog;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

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
        playersLST = new PlayersList();
        playersLST.addMouseListener(new InviteListener());
        for (final PlayerDTO player : players) {
            playersLST.addElement(player);
        }
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
    private class InviteListener extends MouseAdapter
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        @Override
        public void mouseClicked(final MouseEvent e) {
            final PlayerDTO selectedPlayer = playersLST.getSelectedValue();
            if (selectedPlayer != null) {
                client.sendRequest(selectedPlayer);
                dialog.dispose();
            }
        }

    }

}
