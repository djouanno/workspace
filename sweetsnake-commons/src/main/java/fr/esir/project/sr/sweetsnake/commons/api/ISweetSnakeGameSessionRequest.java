package fr.esir.project.sr.sweetsnake.commons.api;

public interface ISweetSnakeGameSessionRequest
{

    String getPlayerName();

    void acceptRequest();

    void denyRequest();

}
