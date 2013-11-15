package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IGameSessionCallback;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;

public class GameSessionCallback extends UnicastRemoteObject implements IGameSessionCallback
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 1029287568352053172L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(GameSessionCallback.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final GameSession   session;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * @throws RemoteException
     * 
     */
    public GameSessionCallback(final GameSession _session) throws RemoteException {
        super();
        session = _session;
        log.info("Initializing new game session callback for session {}", session.getId());
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

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
    public void move(final IClientCallback client, final MoveDirection direction) throws UnauthorizedActionException, RemoteException {
        session.movePlayer(client, direction);
    }

}
