package com.esir.sr.sweetsnake.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientLauncher
{

    /**
     * @param args
     */
    public static void main(final String[] args) {
        @SuppressWarnings("resource")
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-client-context.xml");
        context.registerShutdownHook();
    }

}
