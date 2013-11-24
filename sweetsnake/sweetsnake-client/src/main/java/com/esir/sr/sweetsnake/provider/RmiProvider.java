package com.esir.sr.sweetsnake.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class RmiProvider
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(RmiProvider.class);


    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected RmiProvider() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public IServer getRmiService() {
        try {
            log.debug("Trying to reach server at URL : {}", PropertiesConstants.SERVER_URL);
            final RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
            factory.setServiceInterface(IServer.class);
            factory.setServiceUrl(PropertiesConstants.SERVER_URL);
            factory.afterPropertiesSet();
            return (IServer) factory.getObject();
        } catch (final Exception e) {
            return null;
        }
    }

}