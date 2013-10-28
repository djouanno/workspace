package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;

public interface ISweetSnakeClientCallback extends Remote
{

    void requestGame(SweetSnakeGameRequestDTO request);

    void addElement(IElement element) throws RemoteException;

    void updateElement(IElement element) throws RemoteException;

    void removeElement(IElement element) throws RemoteException;

    void setScore(long score) throws RemoteException;

    String getName() throws RemoteException;

}
