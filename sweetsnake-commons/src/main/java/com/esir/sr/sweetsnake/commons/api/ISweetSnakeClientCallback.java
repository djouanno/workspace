package com.esir.sr.sweetsnake.commons.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionRequestDTO;

public interface ISweetSnakeClientCallback extends Remote
{

    void requestGame(SweetSnakeGameSessionRequestDTO request);

    void addElement(IElement element) throws RemoteException;

    void updateElement(IElement element) throws RemoteException;

    void removeElement(IElement element) throws RemoteException;

    void setScore(long score) throws RemoteException;

    String getName() throws RemoteException;

}
