package fr.esir.project.sr.sweetsnake.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LaunchServer
{

    /**
     * @param args
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) {
        new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context.xml");
    }

}
