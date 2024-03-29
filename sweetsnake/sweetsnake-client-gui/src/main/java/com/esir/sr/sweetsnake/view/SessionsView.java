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
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.CustomButton;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.SessionsList;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * This class graphically reprents the sessions view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class SessionsView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 4681114166647634262L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(SessionsView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The view title panel */
    private ImagePanel          sessionsListIPL;

    /** The top panel */
    private JPanel              topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The sessions list */
    private SessionsList        sessionsLST;

    /** The disconnect button */
    private JButton             disconnectBTN;

    /** The create button */
    private JButton             createBTN;

    /** The join button */
    private JButton             joinBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new sessions view
     */
    protected SessionsView() {
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
        log.info("Initializing the Sessions View");
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

        initSessionsListIPL();
        topPL.add(sessionsListIPL);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        final List<GameSessionDTO> sessions = new LinkedList<GameSessionDTO>();

        initSessionsLST(sessions);
        centerPL.add(new JScrollPane(sessionsLST));

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initDisconnectBTN();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1000;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(disconnectBTN, gbc);

        initCreateBTN();
        gbc.gridx = 1;
        gbc.weightx = 0.1;
        bottomPL.add(createBTN, gbc);

        initJoinBTN();
        gbc.gridx = 2;
        gbc.insets = new Insets(5, 5, 0, 0);
        bottomPL.add(joinBTN, gbc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#clear()
     */
    @Override
    public void clear() {
        // do nothing
    }

    /**
     * This method refreshes the sessions list
     * 
     * @param sessionsList
     *            A list containing the DTO representing all the available game sessions
     */
    public void refreshSessionsList(final List<GameSessionDTO> sessionsList) {
        if (isBuilded) {
            sessionsLST.removeAllElements();
            final List<GameSessionDTO> sessions = new LinkedList<GameSessionDTO>(sessionsList);

            if (sessions.isEmpty()) {
                sessionsLST.disableSelection();
                sessionsLST.addElement(new GameSessionDTO("No available game for the moment", new LinkedList<PlayerDTO>(), null, null, false, null));
            } else {
                sessionsLST.enableSelection();
                for (final GameSessionDTO session : sessions) {
                    sessionsLST.addElement(session);
                }
            }
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
     * This methods initializes the sessions list image panel
     */
    private void initSessionsListIPL() {
        sessionsListIPL = new ImagePanel(ClientGuiConstants.GAMES_LIST_PATH);
    }

    /**
     * This methods initializes the center panel
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new BorderLayout());
    }

    /**
     * This methods initializes the sessions list
     * 
     * @param sessions
     *            A list containing the DTO representing all the available sessions
     */
    private void initSessionsLST(final List<GameSessionDTO> sessions) {
        sessionsLST = new SessionsList();
        for (final GameSessionDTO session : sessions) {
            sessionsLST.addElement(session);
        }
    }

    /**
     * This methods initializes the bottom panel
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new GridBagLayout());
        bottomPL.setOpaque(false);
    }

    /**
     * This methods initializes the disconnect button
     */
    private void initDisconnectBTN() {
        disconnectBTN = new CustomButton("disconnect");
        disconnectBTN.addActionListener(new DisconnectListener());
    }

    /**
     * This methods initializes the create button
     */
    private void initCreateBTN() {
        createBTN = new CustomButton("create game");
        createBTN.addActionListener(new CreateGameListener());
    }

    /**
     * This methods initializes the join button
     */
    private void initJoinBTN() {
        joinBTN = new CustomButton("join game");
        joinBTN.addActionListener(new JoinGameListener());
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * This class provides an action listener for the disconnect button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class DisconnectListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.disconnect();
        }

    }

    /**
     * This class provides an action listener for the create button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class CreateGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.createSession();
        }

    }

    /**
     * This class provides an action listener for the join button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class JoinGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final GameSessionDTO session = sessionsLST.getSelectedValue();
            if (session == null) {
                gui.displayErrorMessage("Please select a game session first");
            } else {
                client.joinSession(session);
            }
        }

    }

}
