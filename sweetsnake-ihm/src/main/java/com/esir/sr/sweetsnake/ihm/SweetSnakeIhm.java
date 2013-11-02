package com.esir.sr.sweetsnake.ihm;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.api.ISweetSnakeView;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.view.SweetSnakeHomeView;
import com.esir.sr.sweetsnake.view.SweetSnakePlayersView;

@Component
public class SweetSnakeIhm extends JFrame implements ISweetSnakeIhm
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long     serialVersionUID = -4189434181017519666L;
    private static final Logger   log              = LoggerFactory.getLogger(SweetSnakeIhm.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private ISweetSnakeClient     client;

    @Autowired
    private SweetSnakeHomeView    homeView;

    @Autowired
    private SweetSnakePlayersView playersView;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public SweetSnakeIhm() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] INIT METHODS
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initializing a new SweetSnakeIhm");
        initFrameParameters();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    private void initFrameParameters() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocation(30, 30);
        setSize(new Dimension(SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1), SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1)));
        setPreferredSize(new Dimension(SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1), SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1)));
        swithView(homeView);
        addWindowListener(new windowListener());
        setVisible(true);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void successfullyConnected() {
        swithView(playersView);
    }

    @Override
    public void moveSnake(final SweetSnakeDirection direction) {
        // TODO
        refreshUI();
    }

    @Override
    public void swithView(final ISweetSnakeView view) {
        view.build();
        getContentPane().removeAll();
        getContentPane().add((JPanel) view);
        refreshUI();
    }

    @Override
    public void refreshUI() {
        revalidate();
        repaint();
        pack();
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    private class windowListener implements WindowListener
    {
        @Override
        public void windowActivated(final WindowEvent arg0) {
        }

        @Override
        public void windowClosed(final WindowEvent arg0) {
        }

        @Override
        public void windowClosing(final WindowEvent arg0) {
            client.disconnect();
        }

        @Override
        public void windowDeactivated(final WindowEvent arg0) {
        }

        @Override
        public void windowDeiconified(final WindowEvent arg0) {
        }

        @Override
        public void windowIconified(final WindowEvent arg0) {
        }

        @Override
        public void windowOpened(final WindowEvent arg0) {
        }
    }

}
