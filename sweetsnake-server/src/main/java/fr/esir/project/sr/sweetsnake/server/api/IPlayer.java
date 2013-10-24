package fr.esir.project.sr.sweetsnake.server.api;

import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeClientCallback;
import fr.esir.project.sr.sweetsnake.commons.enumerations.Status;


public interface IPlayer
{

    ISweetSnakeClientCallback getClientCallback();

    String getName();

    Status getStatus();

    void setStatus(Status status);

}
