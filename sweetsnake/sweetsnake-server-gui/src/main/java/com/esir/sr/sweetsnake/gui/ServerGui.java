package com.esir.sr.sweetsnake.gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IGuiForServer;
import com.esir.sr.sweetsnake.constants.ServerGuiConstants;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.view.AbstractView;
import com.esir.sr.sweetsnake.view.MainView;
import com.esir.sr.sweetsnake.view.PlayersView;
import com.esir.sr.sweetsnake.view.RequestsView;
import com.esir.sr.sweetsnake.view.SessionsView;
import com.esir.sr.sweetsnake.view.StatusView;

/**
 * This class implements the IGuiForServer interface.<br />
 * It extends the JFrame class to provide the main frame GUI.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.api.IGuiForServer
 * @see javax.swing.JFrame
 */
@Component
public class ServerGui extends JFrame implements IGuiForServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -2931754540991928946L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(ServerGui.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The main view */
    @Autowired
    private MainView            mainView;

    /** The status view */
    @Autowired
    private StatusView          statusView;

    /** The players view */
    @Autowired
    private PlayersView         playersView;

    /** The sessions view */
    @Autowired
    private SessionsView        sessionsView;

    /** The requests view */
    @Autowired
    private RequestsView        requestsView;

    /** The current view */
    private AbstractView        currentView;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new server GUI
     */
    protected ServerGui() {
        super();
    }

    /**
     * Initializes a new server GUI
     */
    @PostConstruct
    protected void init() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.info("Initializing the Server GUI");
                initFrameParameters();
                serverStarted();
            }
        });
    }

    /**
     * Called before destroying the server GUI
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying the Server GUI");
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC IMPLEMENTED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerGui#serverLaunched()
     */
    @Override
    public void serverStarted() {
        switchView(mainView, true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                statusView.disableStartBTN();
                statusView.enableStopBTN();
                statusView.startTimer();
                playersView.refreshPlayers(new LinkedList<PlayerDTO>());
                sessionsView.refreshSessions(new LinkedList<GameSessionDTO>());
                requestsView.refreshRequests(new LinkedList<GameRequestDTO>());
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerGui#serverStopped()
     */
    @Override
    public void serverStopped() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                statusView.stopTimer();
                statusView.disableStopBTN();
                statusView.enableStartBTN();
                playersView.refreshPlayers(new LinkedList<PlayerDTO>());
                sessionsView.refreshSessions(new LinkedList<GameSessionDTO>());
                requestsView.refreshRequests(new LinkedList<GameRequestDTO>());
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerGui#refreshPlayers(java.util.List)
     */
    @Override
    public void refreshPlayers(final List<PlayerDTO> players) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playersView.refreshPlayers(players);
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerGui#refreshSessions(java.util.List)
     */
    @Override
    public void refreshSessions(final List<GameSessionDTO> sessions) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                sessionsView.refreshSessions(sessions);
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServerGui#refreshRequests(java.util.List)
     */
    @Override
    public void refreshRequests(final List<GameRequestDTO> requests) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                requestsView.refreshRequests(requests);
                refreshUI();
            }
        });
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method refreshes the GUI
     */
    public void refreshUI() {
        revalidate();
        repaint();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This method initializes the frame parameters
     */
    private void initFrameParameters() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SweetSnake Server");

        final Image icon = Toolkit.getDefaultToolkit().getImage(ServerGui.class.getResource(ServerGuiConstants.ICON_PATH));
        setIconImage(icon);

        final Dimension dimension = new Dimension(ServerGuiConstants.GUI_WIDTH, ServerGuiConstants.GUI_HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        setExtendedState(Frame.MAXIMIZED_BOTH);

        pack();

        setVisible(true);
    }

    /**
     * This method switch the current view displayed in the frame
     * 
     * @param view
     *            The new view to display
     * @param unbuildPrevious
     *            True to unbuild the previous view, false otherwise
     */
    private void switchView(final AbstractView view, final boolean unbuildPrevious) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (currentView != null && unbuildPrevious) {
                    currentView.unbuild();
                }
                view.build();
                getContentPane().removeAll();
                getContentPane().add(view);
                refreshUI();
                currentView = view;
            }
        });
    }

}
