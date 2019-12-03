package org.opengrads.factory;

import org.opengrads.interfaces.*;
import org.python.core.*;
import org.python.util.*;


//                          F a c t o r i e s

/**
 * Class for creating GrADSHandleObject instances. This should be
 * preferred over other methods of creating GaHandle instances because
 * this method ensures all instances are being run from the same
 * python interpretter.
 *
 * @author Arlindo da Silva <dasilva@alum.mit.edu>
 */
public class GaHandleFactory {
    private PyObject jyGaHandleClass;

    /**
     * Class constructor, starts the factory-wide python interpretter.
     */
    public GaHandleFactory() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from grads import *");
        jyGaHandleClass = interpreter.get("GaHandle");
    }
    
    /**
     * Create a GaHandleObject
     * @param name Name to instantiate GaHandleObject with
     * @return new GaHandleObject instance
     */
    public GaHandleObject create(String name) {

        PyObject h = jyGaHandleClass.__call__(new PyString(name));

        return (GaHandleObject)h.__tojava__(GaHandleObject.class);
    }



}