package com.esir.sr.sweetsnake.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esir.sr.sweetsnake.constants.PropertiesConstants;

/**
 * This class provides the server application launcher
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ServerLauncher
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the launcher
     */
    private ServerLauncher() {
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * This method is the entry point of the server and starts the Spring context associated to the application
     * 
     * @param args
     *            The JVM args
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:" + PropertiesConstants.SERVER_SPRING_CONTEXT_PATH);
        context.registerShutdownHook();
    }

}
