package com.esir.sr.sweetsnake.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientForGui;
import com.esir.sr.sweetsnake.api.IGuiForClient;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.Toast;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.view.AbstractView;
import com.esir.sr.sweetsnake.view.ConnectionView;
import com.esir.sr.sweetsnake.view.GameView;
import com.esir.sr.sweetsnake.view.LobbyView;
import com.esir.sr.sweetsnake.view.PlayersView;
import com.esir.sr.sweetsnake.view.SessionsView;
import com.esir.sr.sweetsnake.view.UnreachableServerView;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class ClientGui extends JFrame implements IGuiForClient
{

    // public static void main(final String[] args) {
    // @SuppressWarnings("resource")
    // final ClassPathXmlApplicationContext context = new
    // ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-client-gui-context.xml");
    // context.registerShutdownHook();
    // }

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long     serialVersionUID = -4189434181017519666L;

    /** The logger */
    private static final Logger   log              = LoggerFactory.getLogger(ClientGui.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client */
    @Autowired
    private IClientForGui         client;

    /** The unreachable server view */
    @Autowired
    private UnreachableServerView unreachableServerView;

    /** The connection view */
    @Autowired
    private ConnectionView        connectionView;

    /** The sessions view */
    @Autowired
    private SessionsView          sessionsView;

    /** The players view */
    @Autowired
    private PlayersView           playersView;

    /** The lobby view */
    @Autowired
    private LobbyView             lobbyView;

    /** The game view */
    @Autowired
    private GameView              gameView;

    /** The gui current view */
    private AbstractView          currentView;

    /** The GUI dimension */
    private Dimension             dimension;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected ClientGui() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.info("Initializing the Client GUI");
                initFrameParameters();
            }
        });
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying the Client GUI");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initFrameParameters() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("SweetSnake");

        final Image icon = Toolkit.getDefaultToolkit().getImage(ClientGui.class.getResource(ClientGuiConstants.ICON_PATH));
        setIconImage(icon);

        dimension = new Dimension(ClientGuiConstants.GUI_WIDTH, ClientGuiConstants.GUI_HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int X = screen.width / 2 - dimension.width / 2;
        final int Y = screen.height / 2 - dimension.height / 2;
        setBounds(X, Y, dimension.width, dimension.height);
        setContentPane(new ImagePanel(ClientGuiConstants.BG_PATH));

        pack();

        setVisible(true);
    }

    /**
     * 
     * @param view
     * @param unbuildPrevious
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
                log.debug("View switched to {}", view.getClass().getSimpleName());
            }
        });
    }

    /**
     * 
     * @param parent
     * @return
     */
    private JOptionPane getOptionPane(final JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent) parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }

    // private void displayInfoMessage(final String title, final String message) {
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // JOptionPane.showMessageDialog(Gui.this, message, title, JOptionPane.INFORMATION_MESSAGE);
    // }
    // }).start();
    // }

    /**
     * 
     * @param message
     */
    private void displayToast(final String message) {
        Toast.displayToast(this, message);
    }

    /**
     * 
     * @param message
     * @param buttonsText
     * @return
     */
    private int displayCustomQuestion(final String title, final String message, final String[] buttonsText) {
        final Object[] buttons = new Object[buttonsText.length];
        int i = 0;
        for (final String buttonText : buttonsText) {
            final JButton button = new JButton(buttonText);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final JOptionPane pane = getOptionPane((JComponent) e.getSource());
                    pane.setValue(button);
                }
            });
            buttons[i] = button;
            i++;
        }
        return JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
    }

    /**
     * 
     * @param players
     */
    private void refreshLobbyView(final List<PlayerDTO> players) {
        lobbyView.setPlayers(players);
        lobbyView.refreshPlayers();
        boolean allReady = true;
        for (final PlayerDTO player : players) {
            if (player.getStatus() != PlayerStatus.READY) {
                allReady = false;
            }
            if (player.getName().equals(client.getUsername())) {
                lobbyView.setPlayerNb(player.getNumber());
            }
        }
        lobbyView.refreshButtons(allReady);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC IMPLEMENTED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverReachable()
     */
    @Override
    public void serverReachable() {
        if (currentView != connectionView) {
            switchView(connectionView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverNotReachable()
     */
    @Override
    public void serverNotReachable() {
        if (currentView != unreachableServerView) {
            switchView(unreachableServerView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientGui#disconnectedFromServer()
     */
    @Override
    public void disconnectedFromServer() {
        if (currentView != connectionView) {
            switchView(connectionView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#successfullyConnected()
     */
    @Override
    public void connectedToServer() {
        switchView(sessionsView, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        if (currentView == playersView) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    playersView.refreshPlayersList(playersList);
                    refreshUI();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshSessionsList(java.util.List)
     */
    @Override
    public void refreshSessionsList(final List<GameSessionDTO> sessionsList) {
        if (currentView == sessionsView) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    sessionsView.refreshSessionsList(sessionsList);
                    refreshUI();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#gameRequested(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public int requestReceived(final GameRequestDTO request) {
        return displayCustomQuestion("Someone wants to play with you", request.getRequestingPlayerDto().getName() + " wants to play with you", new String[] { "accept", "deny" });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#requestSent(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestSent(final GameRequestDTO request) {
        if (currentView != lobbyView) {
            switchView(lobbyView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#requestRefused(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestDenied(final GameRequestDTO request) {
        displayToast(request.getRequestedPlayerDto().getName() + " has denied your request");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#gameJoined(int, java.util.List)
     */
    @Override
    public void sessionJoined(final int playerNb, final List<PlayerDTO> players) {
        if (currentView != lobbyView) {
            switchView(lobbyView, true);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                lobbyView.setPlayerNb(playerNb);
                lobbyView.setPlayers(players);
                lobbyView.refreshPlayers();
                boolean allReady = true;
                for (final PlayerDTO player : players) {
                    if (player.getStatus() != PlayerStatus.READY) {
                        allReady = false;
                    }
                }
                lobbyView.refreshButtons(allReady);
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#sessionStarted(int, java.util.Map, com.esir.sr.sweetsnake.dto.GameBoardDTO)
     */
    @Override
    public void sessionStarted(final int playerNb, final Map<Integer, String> playersSnakes, final GameBoardDTO gameBoard) {
        gameView.setPlayerNb(playerNb);
        gameView.setPlayersSnakesMap(playersSnakes);
        gameView.setGameboardDto(gameBoard);
        switchView(gameView, true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameView.requestFocusInWindow();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#sessionLeft(java.util.List, com.esir.sr.sweetsnake.dto.PlayerDTO, boolean, boolean)
     */
    @Override
    public void sessionLeft(final List<PlayerDTO> players, final PlayerDTO leaver, final boolean stopped, final boolean finished) {
        if (stopped && !finished) {
            if (leaver.getName().equals(client.getUsername())) {
                switchView(sessionsView, true);
            } else {
                switchView(lobbyView, false);
                refreshLobbyView(players);
            }
        } else if (finished) {
            switchView(sessionsView, true);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    displayToast(leaver + " has left the game :(");
                    if (currentView == gameView) {
                        gameView.hideScore(leaver.getNumber());
                    } else if (currentView == lobbyView) {
                        refreshLobbyView(players);
                    }
                    refreshUI();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#sessionFinished(java.util.Map)
     */
    @Override
    public void sessionFinished(final List<PlayerDTO> players) {
        switchView(lobbyView, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshGameboard(com.esir.sr.sweetsnake.dto.GameBoardDTO)
     */
    @Override
    public void refreshGameboard(final GameBoardDTO gameBoard) {
        if (currentView == gameView) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gameView.setGameboardDto(gameBoard);
                    gameView.drawGameboard();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshScores(java.util.Map)
     */
    @Override
    public void refreshScores(final List<PlayerDTO> players) {
        if (currentView == gameView) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gameView.refreshScores(players);
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#displayErrorMessage(java.lang.String)
     */
    @Override
    public void displayErrorMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(ClientGui.this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    public void refreshUI() {
        revalidate();
        repaint();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public Dimension getDimension() {
        return dimension;
    }

}
