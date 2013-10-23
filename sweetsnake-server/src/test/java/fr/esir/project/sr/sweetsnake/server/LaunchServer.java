package fr.esir.project.sr.sweetsnake.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchServer
{

    /**
     * @param args
     */
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context.xml");
    }

}
