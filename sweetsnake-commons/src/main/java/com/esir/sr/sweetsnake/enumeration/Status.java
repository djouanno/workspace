package com.esir.sr.sweetsnake.enumeration;


public enum Status
{

    AVAILABLE("available"), PENDING("pending"), PLAYING("playing");

    private String value = "unknown";

    Status(final String _value) {
        value = _value;
    }

    @Override
    public String toString() {
        return value;
    }

}
