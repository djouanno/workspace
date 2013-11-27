package com.esir.sr.sweetsnake.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esir.sr.sweetsnake.constants.PropertiesConstants;

/**
 * This class provides the client application launcher.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ClientLauncher
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the launcher
     */
    private ClientLauncher() {
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * This method is the entry point of the client and starts the Spring context associated to the application
     * 
     * @param args
     *            The JVM args
     */
    public static void main(final String[] args) {
        @SuppressWarnings("resource")
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:" + PropertiesConstants.CLIENT_SPRING_CONTEXT_PATH);
        context.registerShutdownHook();
    }

}
