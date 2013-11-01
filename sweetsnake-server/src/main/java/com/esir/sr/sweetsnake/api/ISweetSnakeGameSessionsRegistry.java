package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;

public interface ISweetSnakeGameSessionsRegistry
{

    /**
     * 
     * @param id
     * @return
     */
    public boolean contains(final String id);

    /**
     * 
     * @param session
     */
    public void add(final ISweetSnakeGameSession session);

    /**
     * 
     * @param id
     * @return
     * @throws GameSessionNotFoundException
     */
    public ISweetSnakeGameSession get(final String id) throws GameSessionNotFoundException;

    /**
     * 
     * @param id
     * @throws GameSessionNotFoundException
     */
    public void remove(final String id) throws GameSessionNotFoundException;

    /**
     * 
     * @return
     */
    public List<String> getSessionsId();

}
