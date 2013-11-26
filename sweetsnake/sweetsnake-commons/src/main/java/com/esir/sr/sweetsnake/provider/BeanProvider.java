package com.esir.sr.sweetsnake.provider;

import java.beans.Introspector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * This class provides all the methods to manually retrieve Spring prototype beans.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class BeanProvider
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The spring context */
    @Autowired
    private ApplicationContext context;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new bean provider
     */
    protected BeanProvider() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method returns a prototype of the specified class
     * 
     * @param clazz
     *            The prototype class
     * @return An instance of the prototype class
     */
    public <T> T getPrototype(final Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * This method returns a prototype of the specified class initialized with the specified arguments
     * 
     * @param clazz
     *            The prototype class
     * @param args
     *            The prototype arguments
     * @return An instance of the prototype class
     */
    @SuppressWarnings("unchecked")
    public <T> T getPrototype(final Class<T> clazz, final Object... args) {
        return (T) context.getBean(Introspector.decapitalize(clazz.getSimpleName()), args);
    }

}
