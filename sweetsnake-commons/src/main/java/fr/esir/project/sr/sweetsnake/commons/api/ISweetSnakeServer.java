package fr.esir.project.sr.sweetsnake.commons.api;

import fr.esir.project.sr.sweetsnake.commons.Direction;
import fr.esir.project.sr.sweetsnake.commons.exceptions.UnableToConnectException;

public interface ISweetSnakeServer
{

    void connect(ISweetSnakeClientListener client) throws UnableToConnectException;

    void disconnect(ISweetSnakeClientListener client);

    void startGame();

    void exitGame();

    void move(Direction direction);

}
