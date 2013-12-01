package com.esir.sr.sweetsnake.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.CustomButton;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;

/**
 * This class graphically reprents the unreachable server view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class UnreachableServerView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -4955989460436322020L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(UnreachableServerView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The top image panel */
    private ImagePanel          topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The error label */
    private JLabel              errorLB;

    /** The retry button */
    private JButton             retryBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new unreachable server view
     */
    protected UnreachableServerView() {
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
        log.info("Initializing the Unreachable Server View");
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

        initErrorLB();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(0, 0, 10, 0);
        centerPL.add(errorLB, gbc);

        initRetryBTN();
        gbc.gridy = 1;
        gbc.weighty = 1000;
        centerPL.add(retryBTN, gbc);
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
        centerPL.setLayout(new GridBagLayout());
        centerPL.setOpaque(false);
    }

    /**
     * This methods initializes the error label
     */
    private void initErrorLB() {
        errorLB = new JLabel("Server is not reachable");
        errorLB.setForeground(Color.black);
        errorLB.setFont(new Font("sans-serif", Font.BOLD, 16));
    }

    /**
     * This methods initializes the retry button
     */
    private void initRetryBTN() {
        retryBTN = new CustomButton("retry");
        retryBTN.addActionListener(new RetryListener());
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * This class provides an action listener for the retry button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class RetryListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.reachServer();
        }

    }

}
