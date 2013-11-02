package com.esir.sr.sweetsnake.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClientLauncher
{

    /**
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-client-context.xml");
    }

}
