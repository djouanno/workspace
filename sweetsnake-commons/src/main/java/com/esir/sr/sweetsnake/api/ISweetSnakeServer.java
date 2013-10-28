package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.exception.UnableToMountGameSessionException;

public interface ISweetSnakeServer
{

    void connect(ISweetSnakeClientCallback client) throws UnableToConnectException;

    void disconnect(ISweetSnakeClientCallback client);

    SweetSnakeGameRequestDTO requestGameSession(ISweetSnakeClientCallback client, SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException;

    SweetSnakeGameSessionDTO acceptGameSession(ISweetSnakeClientCallback client, SweetSnakeGameRequestDTO request) throws PlayerNotFoundException, UnableToMountGameSessionException;

    void leaveGameSession(ISweetSnakeClientCallback client);

    void cancelGameSessionRequest(ISweetSnakeClientCallback client);

    List<SweetSnakePlayerDTO> getPlayersList(ISweetSnakeClientCallback client);

    void move(Direction direction);

}
