package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SweetSnakePlayersView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -5820091417435340407L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakePlayersView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private JList<?>                      playersJLT;
    private JButton                       refreshListBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakePlayersView() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] INIT METHOD
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initializing a new SweetSnakePlayersView");
    }

    @Override
    public void build() {
        setLayout(new BorderLayout());
        removeAll();
        final List<String> players = client.getPlayersList();
        if (!players.isEmpty()) {
            playersJLT = new JList<Object>(players.toArray());
            add(playersJLT);
        } else {
            add(new JLabel("no other players connected"));
            refreshListBTN = new JButton("refresh list");
            refreshListBTN.addActionListener(new RefreshListener());
            add(refreshListBTN);
        }
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    private class RefreshListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            build();
            ihm.refreshUI();
        }
    }

}
