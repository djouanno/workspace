package com.esir.sr.sweetsnake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;

/**
 * This class graphically reprents the reaching server view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class ReachingServerView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 6866610932112736031L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(ReachingServerView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The logo panel */
    private ImagePanel          logoPL;

    /** The reaching label */
    private JLabel              reachingLB;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new reaching server view
     */
    protected ReachingServerView() {
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
        log.info("Initializing the Reaching Server View");
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
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        initLogoPL();
        initReachingLB();

        // logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(logoPL, gbc);

        // label
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(reachingLB, gbc);

        new Timer(500, new ActionListener() {
            private int i = 1;

            @Override
            public void actionPerformed(final ActionEvent e) {
                reachingLB.setForeground(new Color(255, 255, 255, i == 0 ? 0 : 255));
                i = (i + 1) % 2;
            }
        }).start();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This methods initializes the logo panel
     */
    private void initLogoPL() {
        logoPL = new ImagePanel(ClientGuiConstants.LOGO_PATH);
    }

    /**
     * This methods initializes the reaching label
     */
    private void initReachingLB() {
        reachingLB = new JLabel("Contacting server...");
        reachingLB.setForeground(Color.black);
        reachingLB.setFont(new Font("sans-serif", Font.BOLD, 16));
    }

}
