package com.esir.sr.sweetsnake.server.api;

public interface ISweetSnakeGameSessionRequest
{

    ISweetSnakePlayer getRequestingPlayer();

    ISweetSnakePlayer getRequestedPlayer();

}
