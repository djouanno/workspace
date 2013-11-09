package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.List;

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
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.utils.ClientCallbackMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/sweetsnake-server-context-test.xml" })
public class ServerTest
{

    private static final Logger                   log                   = LoggerFactory.getLogger(ServerTest.class);
    private static final String                   player1               = "player1", player2 = "player2";
    private static ClassPathXmlApplicationContext context;
    private static IClientCallback      client1, client2;
    private static boolean                        runDataInitialization = true;

    @Autowired
    @Qualifier("sweetSnakeServiceRMI")
    private IServer                     server;

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


    @Test
    public void getPlayersListTest() {
        log.debug("---------------------------- getPlayersListTest() ----------------------------");

        List<PlayerDTO> playersList = server.getPlayersList(client1);
        log.debug("Number of other players seen by client1 : {}", playersList.size());
        for (final PlayerDTO player : playersList) {
            log.debug("Player[name={}, status={}]", player.getName(), player.getStatus());
        }
        Assert.assertTrue("Number of players must be positive", playersList.size() >= 0);

        playersList = server.getPlayersList(client2);
        log.debug("Number of other players seen by client2 : {}", playersList.size());
        for (final PlayerDTO player : playersList) {
            log.debug("Player[name={}, status={}]", player.getName(), player.getStatus());
        }
        Assert.assertTrue("Number of players must be positive", playersList.size() >= 0);
    }

    @Test
    public void gameSessionTest() throws PlayerNotFoundException, PlayerNotAvailableException, GameRequestNotFoundException, GameSessionNotFoundException, RemoteException {
        log.debug("---------------------------- gameSessionTest() ----------------------------");

        final PlayerDTO player2DTO = new PlayerDTO(client2.getUsername(), PlayerStatus.AVAILABLE);
        final GameRequestDTO requestDTO = server.requestGame(client1, player2DTO);
        final GameSessionDTO sessionDTO = server.acceptGame(client2, requestDTO);

        server.requestMove(client1, sessionDTO, MoveDirection.RIGHT);
    }
}
