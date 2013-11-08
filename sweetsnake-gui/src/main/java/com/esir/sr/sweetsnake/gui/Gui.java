package com.esir.sr.sweetsnake.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClient;
import com.esir.sr.sweetsnake.api.IGui;
import com.esir.sr.sweetsnake.api.IView;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.uicomponent.ImagePanel;

@Component
public class Gui extends JFrame implements IGui
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long   serialVersionUID = -4189434181017519666L;
    private static final Logger log              = LoggerFactory.getLogger(Gui.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private IClient   client;

    @Autowired
    @Qualifier("unreachableServerView")
    private IView     serverNotReachableView;

    @Autowired
    @Qualifier("connectionView")
    private IView     connectionView;

    @Autowired
    @Qualifier("playersView")
    private IView     playersView;

    @Autowired
    @Qualifier("gameView")
    private IView     gameView;

    private Dimension           dimension;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected Gui() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing a new SweetSnakeIhm");
        initFrameParameters();
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying SweetSnakeIhm");
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
        dimension = new Dimension(GuiConstants.IHM_WIDTH, GuiConstants.IHM_HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int X = screen.width / 2 - dimension.width / 2;
        final int Y = screen.height / 2 - dimension.height / 2;
        setBounds(X, Y, dimension.width, dimension.height);
        setContentPane(new ImagePanel(GuiConstants.BG_PATH));

        setVisible(true);
    }

    /**
     * 
     * @param view
     */
    private void switchView(final IView view) {
        view.build();
        getContentPane().removeAll();
        getContentPane().add((JPanel) view);
        refreshUI();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverReachable()
     */
    @Override
    public void serverReachable() {
        switchView(connectionView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverNotReachable()
     */
    @Override
    public void serverNotReachable() {
        switchView(serverNotReachableView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#successfullyConnected()
     */
    @Override
    public void successfullyConnected() {
        switchView(playersView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#requestGame(com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO)
     */
    @Override
    public void requestGame(final PlayerDTO requestedPlayer) {
        client.requestGame(requestedPlayer);
    }

    /*
     * 
     */
    @Override
    public void startGame() {
        switchView(gameView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#moveSnake(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void moveSnake(final MoveDirection direction) {
        // TODO
        refreshUI();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#displayInfoMessage(java.lang.String)
     */
    @Override
    public void displayInfoMessage(final String message) {
        // showMessageDialog is a blocking method while the user has not closed the dialog so we have to launch it in
        // a new thread
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(Gui.this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        t.start();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#displayErrorMessage(java.lang.String)
     */
    @Override
    public void displayErrorMessage(final String message) {
        // showMessageDialog is a blocking method while the user has not closed the dialog so we have to launch it in
        // a new thread
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(Gui.this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        t.start();
    }

    protected JOptionPane getOptionPane(final JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent) parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }

    @Override
    public int displayCustomMessage(final String message, final String[] buttonsText) {
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
        return JOptionPane.showOptionDialog(this, message, "Information", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
        // return JOptionPane.showConfirmDialog(this, message, "Information", JOptionPane.YES_NO_OPTION,
        // JOptionPane.INFORMATION_MESSAGE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#refreshUI()
     */
    @Override
    public void refreshUI() {
        revalidate();
        repaint();
        pack();
    }

    /**
     * 
     */
    public void reachServer() {
        client.reachServer();
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

}
