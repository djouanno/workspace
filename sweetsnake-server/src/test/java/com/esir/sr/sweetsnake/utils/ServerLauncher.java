package com.esir.sr.sweetsnake.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerLauncher
{

    /**
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context.xml");
    }

}
