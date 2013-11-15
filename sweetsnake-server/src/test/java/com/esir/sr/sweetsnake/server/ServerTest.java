package com.esir.sr.sweetsnake.server;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.utils.ClientCallbackMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/sweetsnake-server-context-test.xml" })
public class ServerTest
{

    private static final Logger                   log                   = LoggerFactory.getLogger(ServerTest.class);
    private static final String                   player1               = "player1", player2 = "player2";
    private static ClassPathXmlApplicationContext context;
    private static IClientCallback                client1, client2;
    private static boolean                        runDataInitialization = true;

    @Autowired
    @Qualifier("sweetSnakeServiceRMI")
    private IServer                               server;

    @BeforeClass
    public static void beforeClass() {
        log.debug("Starting RMI server");
        context = new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context.xml");
    }

    @AfterClass
    public static void afterClass() {
        log.debug("Stopping RMI server");
        context.close();
    }

    @Before
    public void before() throws UnableToConnectException {
        if (runDataInitialization) {
            log.info("Initializing test fields");
            client1 = new ClientCallbackMock(player1);
            client2 = new ClientCallbackMock(player2);
            server.connect(client1);
            server.connect(client2);
            runDataInitialization = false;
        }
    }

    @Test
    public void rmiServiceTest() {
        log.debug("---------------------------- rmiServiceTest() ----------------------------");

        Assert.assertNotNull("RMI Server must not be null", server);
    }

    @Test(expected = UnableToConnectException.class)
    public void connectFailTest() throws UnableToConnectException {
        log.debug("---------------------------- connectTest() ----------------------------");

        server.connect(client1);
    }

}
