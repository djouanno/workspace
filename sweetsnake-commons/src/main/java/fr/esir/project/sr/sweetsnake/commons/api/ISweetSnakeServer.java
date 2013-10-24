package fr.esir.project.sr.sweetsnake.commons.api;

import fr.esir.project.sr.sweetsnake.commons.enumerations.Direction;
import fr.esir.project.sr.sweetsnake.commons.exceptions.PlayerNotFoundException;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToConnectException;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToMountGameSessionException;

public interface ISweetSnakeServer
{

    void connect(ISweetSnakeClientCallback client) throws UnableToConnectException;

    void disconnect(ISweetSnakeClientCallback client);

    void requestGameSession(ISweetSnakeClientCallback client, String otherPlayer) throws PlayerNotFoundException, UnableToMountGameSessionException;

    void acceptGameSession(ISweetSnakeClientCallback client, ISweetSnakeGameSessionRequest request) throws UnableToMountGameSessionException;

    void leaveGameSession(ISweetSnakeClientCallback client);

    void cancelGameSessionRequest(ISweetSnakeClientCallback client);

    void move(Direction direction);

}
