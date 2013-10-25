package com.esir.sr.sweetsnake.commons.api;

import java.util.List;

import com.esir.sr.sweetsnake.commons.dto.PlayerDTO;
import com.esir.sr.sweetsnake.commons.enumerations.Direction;
import com.esir.sr.sweetsnake.commons.exceptions.PlayerNotFoundException;
import com.esir.sr.sweetsnake.commons.exceptions.UnableToConnectException;
import com.esir.sr.sweetsnake.commons.exceptions.UnableToMountGameSessionException;

public interface ISweetSnakeServer
{

    void connect(ISweetSnakeClientCallback client) throws UnableToConnectException;

    void disconnect(ISweetSnakeClientCallback client);

    void requestGameSession(ISweetSnakeClientCallback client, String otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException;

    void acceptGameSession(ISweetSnakeClientCallback client, ISweetSnakeGameSessionRequest request) throws UnableToMountGameSessionException;

    void leaveGameSession(ISweetSnakeClientCallback client);

    void cancelGameSessionRequest(ISweetSnakeClientCallback client);

    List<PlayerDTO> getPlayersList(ISweetSnakeClientCallback client);

    void move(Direction direction);

}
