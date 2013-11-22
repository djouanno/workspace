package com.esir.sr.sweetsnake.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class ServerLauncher
{

    /**
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context.xml");
        context.registerShutdownHook();
    }

}
