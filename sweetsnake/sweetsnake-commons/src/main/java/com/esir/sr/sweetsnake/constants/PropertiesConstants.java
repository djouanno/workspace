package com.esir.sr.sweetsnake.constants;

/**
 * This class contains all the properties constants
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PropertiesConstants
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the properties constants
     */
    private PropertiesConstants() {
    }

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The server application spring context path */
    public static final String SERVER_SPRING_CONTEXT_PATH = "spring/sweetsnake-server-context.xml";

    /** The client application spring context path */
    public static final String CLIENT_SPRING_CONTEXT_PATH = "spring/sweetsnake-client-context.xml";

    /** The server URL */
    public static final String SERVER_URL                 = "rmi://localhost:1199/SweetSnakeServer";

    /** The generated ids length */
    public static final int    GENERATED_ID_LENGTH        = 10;

}
