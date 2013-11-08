package com.esir.sr.sweetsnake.enumeration;


public enum PlayerStatus
{

    DISCONNECTED("disconnected"), AVAILABLE("available"), INVITING("inviting"), INVITED("invited"), PLAYING("playing");

    private String value = "unknown";

    PlayerStatus(final String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }

}
