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

import com.esir.sr.sweetsnake.component.SweetSnakeImagePanel;
import com.esir.sr.sweetsnake.constants.SweetSnakeIhmConstants;

@Component("unreachableServerView")
public class SweetSnakeUnreachableServerView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long    serialVersionUID = -4955989460436322020L;
    private static final Logger  log              = LoggerFactory.getLogger(SweetSnakeUnreachableServerView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private SweetSnakeImagePanel logoPL;
    private JLabel               errorLB;
    private JButton              retryBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeUnreachableServerView() {
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
        log.info("Initializing a new SweetSnakeUnreachableServerView");
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

        isBuilded = true;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initLogoPL() {
        logoPL = new SweetSnakeImagePanel(SweetSnakeIhmConstants.LOGO_PATH);
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

    private class RetryListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            ihm.reachServer();
        }
    }

}
