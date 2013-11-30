package com.esir.sr.sweetsnake.callback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IGameSessionCallback;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;
import com.esir.sr.sweetsnake.session.GameSession;

/**
 * This class implements the IGameSessionCallback defined in the API.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
@Scope("prototype")
public class GameSessionCallback extends UnicastRemoteObject implements IGameSessionCallback
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 1029287568352053172L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game session */
    private final GameSession session;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new game session callback
     * 
     * @throws RemoteException
     */
    protected GameSessionCallback(final GameSession _session) throws RemoteException {
        super();
        session = _session;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGameSessionCallback#changeNumber(com.esir.sr.sweetsnake.api.IClientCallback, int)
     */
    @Override
    public void changeNumber(final IClientCallback client, final int number) throws RemoteException {
        session.changeNumber(client, number);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGameSessionCallback#startGame(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void startGame(final IClientCallback client) throws UnauthorizedActionException, RemoteException {
        session.startGame(client);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGameSessionCallback#leaveGame(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void leaveGame(final IClientCallback client) throws RemoteException {
        session.leaveGame(client, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGameSessionCallback#move(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.enumeration.MoveDirection)
     */
    @Override
    public void move(final IClientCallback client, final MoveDirection direction) throws RemoteException {
        session.movePlayer(client, direction);
    }

}
