package com.esir.sr.sweetsnake.server.api;

import com.esir.sr.sweetsnake.commons.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.commons.enumerations.Status;


public interface ISweetSnakePlayer
{

    ISweetSnakeClientCallback getClientCallback();

    String getName();

    Status getStatus();

    void setStatus(Status status);

}
