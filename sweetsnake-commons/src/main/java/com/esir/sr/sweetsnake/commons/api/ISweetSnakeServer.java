package com.esir.sr.sweetsnake.commons.api;

import java.util.List;

import com.esir.sr.sweetsnake.commons.dto.PlayerDTO;
import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionRequestDTO;
import com.esir.sr.sweetsnake.commons.enumerations.Direction;
import com.esir.sr.sweetsnake.commons.exceptions.PlayerNotFoundException;
import com.esir.sr.sweetsnake.commons.exceptions.UnableToConnectException;
import com.esir.sr.sweetsnake.commons.exceptions.UnableToMountGameSessionException;

public interface ISweetSnakeServer
{

    void connect(ISweetSnakeClientCallback client) throws UnableToConnectException;

    void disconnect(ISweetSnakeClientCallback client);

    SweetSnakeGameSessionRequestDTO requestGameSession(ISweetSnakeClientCallback client, PlayerDTO otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException;

    SweetSnakeGameSessionDTO acceptGameSession(ISweetSnakeClientCallback client, SweetSnakeGameSessionRequestDTO request) throws PlayerNotFoundException, UnableToMountGameSessionException;

    void leaveGameSession(ISweetSnakeClientCallback client);

    void cancelGameSessionRequest(ISweetSnakeClientCallback client);

    List<PlayerDTO> getPlayersList(ISweetSnakeClientCallback client);

    void move(Direction direction);

}
