package fr.esir.project.sr.sweetsnake.server;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/sweetsnake-server-context.xml" })
public class SweetSnakeServerTest
{

    @Autowired
    private ISweetSnakeServer server;

    @Test
    public void rmiServiceTest() {
        Assert.assertNotNull("RMI Server must not be null", server);
    }

}
