package com.esir.sr.sweetsnake.server;

import static org.mockito.Mockito.when;

import java.rmi.RemoteException;

import junit.framework.Assert;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger                   log = LoggerFactory.getLogger(ServerTest.class);

    /** The spring context */
    private static ClassPathXmlApplicationContext context;

    /** The server instance */
    private static IServer                        server;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client mock */
    @Mock
    private IClientCallback                       client, client2;

    /**********************************************************************************************
     * [BLOCK] BEFORE & AFTER
     **********************************************************************************************/

    /**
     * Initializes the server instance
     */
    @BeforeClass
    public static void beforeClass() {
        context = new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-server-context-test.xml");
        server = (IServer) context.getBean("sweetSnakeService");
    }

    /**
     * Destroys the server instance
     */
    @AfterClass
    public static void afterClass() {
        context.close();
    }

    /**********************************************************************************************
     * [BLOCK] TESTS
     **********************************************************************************************/

    @Test
    public void testRmiService() {
        log.debug("---------------------------- testRmiService() ----------------------------");
        Assert.assertNotNull("RMI Server must not be null", server);
    }

    @Test
    public void testConnect() throws UnableToConnectException, RemoteException {
        log.debug("---------------------------- testConnect() ----------------------------");
        when(client.getUsername()).thenReturn("toto");
        server.connect(client);
    }

    @Test(expected = UnableToConnectException.class)
    public void testConnectNullUsername() throws UnableToConnectException, RemoteException {
        log.debug("---------------------------- testConnectNullUsername() ----------------------------");
        when(client.getUsername()).thenReturn(null);
        server.connect(client);
    }

    @Test(expected = UnableToConnectException.class)
    public void testConnectBadUsername() throws UnableToConnectException, RemoteException {
        log.debug("---------------------------- testConnectBadUsername() ----------------------------");
        when(client.getUsername()).thenReturn("@ç! ~");
        server.connect(client);
    }

    @Test(expected = UnableToConnectException.class)
    public void testConnectDuplicatedUsername() throws UnableToConnectException, RemoteException {
        log.debug("---------------------------- testConnectDuplicatedUsername() ----------------------------");
        when(client.getUsername()).thenReturn("toto");
        server.connect(client);
    }

}
