package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.enumeration.Status;


public interface ISweetSnakePlayer
{

    ISweetSnakeClientCallback getClientCallback();

    String getName();

    Status getStatus();

    void setStatus(Status status);

}
