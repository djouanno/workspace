package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.RequestsList;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class RequestsView extends AbstractView
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -1976866058273480264L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(RequestsView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The title label */
    private JLabel              titleLB;

    /** The center panel */
    private JPanel              centerPL;

    /** The requests list */
    private RequestsList        requestsLST;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The remove button */
    private JButton             removeBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public RequestsView() {
        super();
        log.info("Initializing the Requests View");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#buildImpl()
     */
    @Override
    protected void buildImpl() {
        setLayout(new BorderLayout());

        initTitleLB();
        add(titleLB, BorderLayout.NORTH);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initRequestsLST();
        centerPL.add(new JScrollPane(requestsLST), BorderLayout.CENTER);

        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        initRemoveBTN();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        bottomPL.add(removeBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param _requests
     */
    public void refreshRequests(final List<GameRequestDTO> _requests) {
        requestsLST.removeAllElements();
        final List<GameRequestDTO> requests = new LinkedList<GameRequestDTO>(_requests);

        if (requests.isEmpty()) {
            requestsLST.disableSelection();
            requestsLST.addElement(new GameRequestDTO("No available request for the moment", null, null, null));
        } else {
            requestsLST.enableSelection();
            for (final GameRequestDTO request : requests) {
                requestsLST.addElement(request);
            }
        }

        titleLB.setText("Pending requests (" + requests.size() + ")");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTitleLB() {
        titleLB = new JLabel("Pending requests (0)");
        titleLB.setFont(new Font("sans-serif", Font.BOLD, 14));
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel(new BorderLayout());
        centerPL.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * 
     */
    private void initBottomPL() {
        bottomPL = new JPanel(new GridBagLayout());
    }

    /**
     * 
     */
    private void initRemoveBTN() {
        removeBTN = new JButton("remove request");
        removeBTN.addActionListener(new RemoveListener());
    }

    /**
     * 
     */
    private void initRequestsLST() {
        requestsLST = new RequestsList();
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
    private class RemoveListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final GameRequestDTO request = requestsLST.getSelectedValue();
            if (request != null) {
                server.removeRequest(request);
            }
        }

    }

}
