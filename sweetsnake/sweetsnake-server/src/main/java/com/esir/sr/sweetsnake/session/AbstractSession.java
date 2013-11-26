package com.esir.sr.sweetsnake.session;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.provider.BeanProvider;
import com.esir.sr.sweetsnake.registry.GameRequestsRegistry;
import com.esir.sr.sweetsnake.registry.GameSessionsRegistry;
import com.esir.sr.sweetsnake.registry.PlayersRegistry;
import com.esir.sr.sweetsnake.server.Server;

/**
 * This class provides all the common fields needed by a session class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public abstract class AbstractSession
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The bean provider */
    @Autowired
    protected BeanProvider         beanProvider;

    /** The server */
    @Autowired
    protected Server               server;

    /** The players registry */
    @Autowired
    protected PlayersRegistry      playersRegistry;

    /** The requests registry */
    @Autowired
    protected GameRequestsRegistry requestsRegistry;

    /** The sessions registry */
    @Autowired
    protected GameSessionsRegistry sessionsRegistry;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new session
     */
    protected AbstractSession() {
        super();
    }

}
