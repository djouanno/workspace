package com.esir.sr.sweetsnake.provider;

import java.beans.Introspector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
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
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param clazz
     * @return
     */
    public <T> T getPrototype(final Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 
     * @param clazz
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getPrototype(final Class<T> clazz, final Object... args) {
        return (T) context.getBean(Introspector.decapitalize(clazz.getSimpleName()), args);
    }

}
