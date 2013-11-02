package com.esir.sr.sweetsnake.enumeration;


public enum SweetSnakePlayerStatus
{

    AVAILABLE("available"), PENDING("pending"), INVITED("invited"), PLAYING("playing");

    private String value = "unknown";

    SweetSnakePlayerStatus(final String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }

}
