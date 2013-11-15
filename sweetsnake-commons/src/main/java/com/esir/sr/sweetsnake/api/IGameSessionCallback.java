package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;

public interface IGameSessionCallback extends Remote
{

    void startGame(IClientCallback client) throws UnauthorizedActionException, RemoteException;

    void leaveGame(IClientCallback client) throws RemoteException;

    void move(IClientCallback client, MoveDirection direction) throws UnauthorizedActionException, RemoteException;

}
