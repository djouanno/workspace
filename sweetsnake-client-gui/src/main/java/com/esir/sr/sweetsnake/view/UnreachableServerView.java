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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
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

    /** The logo panel */
    private ImagePanel          logoPL;

    /** The error label */
    private JLabel              errorLB;

    /** The retry button */
    private JButton             retryBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
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
        log.info("Initializing a new Unreachable Server View");
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
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        initLogoPL();
        initErrorLB();
        initRetryBTN();

        // logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(logoPL, gbc);

        // label
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(errorLB, gbc);

        // button
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(retryBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initLogoPL() {
        logoPL = new ImagePanel(ClientGuiConstants.LOGO_PATH);
    }

    /**
     * 
     */
    private void initErrorLB() {
        errorLB = new JLabel("Server is not reachable");
        errorLB.setForeground(Color.white);
        errorLB.setFont(new Font("sans-serif", Font.BOLD, 16));
    }

    /**
     * 
     */
    private void initRetryBTN() {
        retryBTN = new JButton("retry");
        retryBTN.addActionListener(new RetryListener());
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
    private class RetryListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            gui.reachServer();
        }

    }

}
