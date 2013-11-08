package com.esir.sr.sweetsnake.api;

import java.util.List;

import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;

public interface IPlayersRegistry
{

    /**
     * 
     * @param name
     * @return
     */
    public boolean contains(final String name);

    /**
     * 
     * @param player
     */
    public void add(final IPlayer player);

    /**
     * 
     * @param name
     * @return
     * @throws PlayerNotFoundException
     */
    public IPlayer get(final String name) throws PlayerNotFoundException;

    /**
     * 
     * @param name
     * @throws PlayerNotFoundException
     */
    public void remove(final String name) throws PlayerNotFoundException;

    /**
     * 
     * @return
     */
    public List<String> getPlayersName();

}
