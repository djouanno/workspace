package fr.esir.project.sr.sweetsnake.commons.api;

import fr.esir.project.sr.sweetsnake.commons.Direction;

public interface ISweetSnakeServer
{

    boolean connect(ISweetSnakeClient client);

    void disconnect();

    void startGame();

    void exitGame();

    void move(Direction direction);

}
