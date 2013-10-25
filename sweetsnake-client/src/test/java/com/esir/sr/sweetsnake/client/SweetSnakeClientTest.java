package com.esir.sr.sweetsnake.client;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esir.sr.sweetsnake.client.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.commons.api.ISweetSnakeServer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/sweetsnake-client-context-test.xml" })
public class SweetSnakeClientTest
{

    @Autowired
    private ISweetSnakeServer server;

    @Autowired
    private ISweetSnakeClient client;

    @Test
    public void rmiServiceTest() {
        Assert.assertNotNull("RMI Server must not be null", server);
    }

}
