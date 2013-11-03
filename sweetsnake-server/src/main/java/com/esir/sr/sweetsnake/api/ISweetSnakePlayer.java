package com.esir.sr.sweetsnake.api;

import java.util.Set;

import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;


public interface ISweetSnakePlayer
{

    /**
     * 
     * @param requestId
     */
    void addSentRequestId(String requestId);

    /**
     * 
     * @param requestId
     */
    void addReceivedRequestId(String requestId);

    /**
     * 
     * @param requestId
     */
    void removeSentRequestId(String requestId);

    /**
     * 
     * @param requestId
     */
    void removeReceivedRequestId(String requestId);

    /**
     * 
     * @return
     */
    ISweetSnakeClientCallback getClientCallback();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @return
     */
    SweetSnakePlayerStatus getStatus();

    /**
     * 
     * @return
     */
    Set<String> getSentRequestsIds();

    /**
     * 
     * @return
     */
    Set<String> getReceivedRequestsIds();

    /**
     * 
     * @param status
     */
    void setStatus(SweetSnakePlayerStatus status);

}
