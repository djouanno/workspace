package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    /** The top image panel */
    private ImagePanel          topPL;

    /** The center panel */
    private JPanel              centerPL;

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

        initTopPL();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(ClientGuiConstants.VIEW_HEIGHT / 4, 0, 0, 0);
        add(topPL, gbc);

        initCenterPL();
        gbc.gridy = 1;
        gbc.weighty = 1000;
        gbc.insets = new Insets(30, 0, 0, 0);
        add(centerPL, gbc);

        initReachingLB();
        centerPL.add(reachingLB, BorderLayout.NORTH);

        new Timer(500, new ActionListener() {
            private int opacity = 255;

            @Override
            public void actionPerformed(final ActionEvent e) {
                opacity = opacity == 255 ? 0 : 255;
                reachingLB.setForeground(new Color(0, 0, 0, opacity));
            }
        }).start();
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

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This methods initializes the top image panel
     */
    private void initTopPL() {
        topPL = new ImagePanel(ClientGuiConstants.LOGO_PATH);
    }

    /**
     * This methods initializes the center panel
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setLayout(new BorderLayout());
        centerPL.setOpaque(false);
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
