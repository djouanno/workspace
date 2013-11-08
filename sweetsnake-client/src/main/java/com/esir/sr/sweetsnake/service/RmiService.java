package com.esir.sr.sweetsnake.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Service;

import com.esir.sr.sweetsnake.api.IServer;

@Service
public class RmiService
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    // TODO move to constants class
    public static final String  SERVER_URL = "rmi://localhost:1199/SweetSnakeServer";
    private static final Logger log        = LoggerFactory.getLogger(RmiService.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private IServer   server;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected RmiService() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        try {
            log.debug("Trying to reach server at URL : {}", SERVER_URL);
            final RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
            factory.setServiceInterface(IServer.class);
            factory.setServiceUrl(SERVER_URL);
            factory.afterPropertiesSet();
            server = (IServer) factory.getObject();
        } catch (final Exception e) {
            // do nothing, let server be null
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHOD
     **********************************************************************************************/

    /**
     * 
     */
    public void retryReach() {
        init();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public IServer getRmiService() {
        return server;
    }

}