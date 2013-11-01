package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;

public interface ISweetSnakeGameRequestsRegistry
{

    /**
     * 
     * @param id
     * @return
     */
    public boolean contains(final String id);

    /**
     * 
     * @param request
     */
    public void add(final ISweetSnakeGameRequest request);

    /**
     * 
     * @param id
     * @return
     * @throws GameRequestNotFoundException
     */
    public ISweetSnakeGameRequest get(final String id) throws GameRequestNotFoundException;

    /**
     * 
     * @param id
     * @throws GameRequestNotFoundException
     */
    public void remove(final String id) throws GameRequestNotFoundException;

    /**
     * 
     * @return
     */
    public List<String> getRequestsId();

}
