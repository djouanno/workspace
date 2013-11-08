package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

public interface IClientCallback extends Remote
{

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void requestGame(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void requestRefused(GameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void startGame(GameSessionDTO session) throws RemoteException;


    /**
     * 
     * @param direction
     * @throws RemoteException
     */
    void confirmMove(MoveDirection direction) throws RemoteException;

    /**
     * 
     * @param score
     * @throws RemoteException
     */
    void setScore(long score) throws RemoteException;

    /**
     * 
     * @return
     * @throws RemoteException
     */
    String getUsername() throws RemoteException;

}
