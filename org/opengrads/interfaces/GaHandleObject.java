package org.opengrads.interfaces;

import org.python.core.*;
import org.python.util.*;

//                         I n t e r f a c e s

/**
 * Interface for retrieving and setting data in a GrADS Handle object.
 * A GrADS Handle is basically a Map where the strings are keys, and
 * the values can be multiple types.
 * 
 * @author Arlindo da Silva <dasilva@alum.mit.edu>
 */
public interface GaHandleObject {
    
    // Get methods

    /**
     * Retrieve a value from the GrADS handle in the generic PyObject type
     * @param key key string
     * @return assosciated value as a PyObject [generic dynamic type for a python-derived object]
     */
    public PyObject   get   ( String key );
    
    /**
     * Retrieve an integer value assosciated with a given key.
     * @param key key string
     * @return assosciated int value
     */
    public int        geti  ( String key );


    /**
     * Retrieve a float value assosciated with a given key.
     * @param key key string
     * @retrun assosciated float value
   n ;5A */
    public float      getf  ( String key );

    /**
     * Retrieve a String value assosciated with a given key.
     * @param key key string
     * @retrun assosciated string value
     */
    public String     gets  ( String key );

    /**
     * Retrieve an integer array value assosciated with a given key.
     * @param key key string
     * @return assosciated int[] value
     */
    public int    []  getia ( String key );
    
    /**
     * Retrieve a float array value assosciated with a given key.
     * @param key key string
     * @return assosciated float[] value
     */
    public float  []  getfa ( String key );

    /**
     * Retrieve a String array value assosciated with a given key.
     * @param key key string
     * @return assosciated String[] value
     */
    public String []  getsa ( String key );
    

    // Set methods

    /**
     * Assosciate a key with a string value.
     * @param key key string
     * @param value value string
     */
    public void set ( String key, String value );


    /**
     * Assosciate a key with an int value.
     * @param key key string
     * @param value value integer
     */
    public void set ( String key, int    value );

    /**
     * Assosciate a key with a float value.
     * @param key key string
     * @param value value float
     */
    public void set ( String key, float  value );

    /**
     * Assosciate a key with a string array value.
     * @param key key string
     * @param value value String array
     */
    public void set ( String key, String [] value );

    /**
     * Assosciate a key with an integer array.
     * @param key key string
     * @param value value integer array
     */
    public void set ( String key, int    [] value );

    /**
     * Assosciate a key with a float array.
     * @param key key string
     * @param value value float array
     */
    public void set ( String key, float  [] value );
    
}



