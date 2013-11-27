package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class graphically reprents the main view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class MainView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -3325552033233118266L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(MainView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

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

    /** The logs view */
    @Autowired
    private LogsView            logsView;

    /** The top panel */
    private JPanel              topPL;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The monitoring panels */
    private JPanel[]            monitoringPL;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new main view
     */
    protected MainView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#init()
     */
    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.info("Initializing a new Main View");
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
        setLayout(new GridLayout(2, 1));

        initTopPL();
        add(topPL);

        initMonitoringPL();
        for (final JPanel panel : monitoringPL) {
            topPL.add(panel);
        }

        statusView.build();
        monitoringPL[0].add(statusView);

        playersView.build();
        monitoringPL[1].add(playersView);

        sessionsView.build();
        monitoringPL[2].add(sessionsView);

        requestsView.build();
        monitoringPL[3].add(requestsView);

        initBottomPL();
        add(bottomPL);

        logsView.build();
        bottomPL.add(logsView);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This method initializes the top panel
     */
    private void initTopPL() {
        topPL = new JPanel();
        topPL.setLayout(new GridLayout(1, 3));
    }

    /**
     * This method initializes the bottom panel
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new BorderLayout());
        bottomPL.setBorder(new EmptyBorder(0, 10, 10, 0));
    }

    /**
     * This method initializes the monitoring panels
     */
    private void initMonitoringPL() {
        monitoringPL = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            monitoringPL[i] = new JPanel();
            monitoringPL[i].setLayout(new BorderLayout());
            monitoringPL[i].setBorder(new EmptyBorder(10, 10, 10, 10));
        }
    }

}
