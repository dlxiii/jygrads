

package org.opengrads.factory;

import org.opengrads.interfaces.*;
import org.python.core.*;
import org.python.util.*;


//                          F a c t o r i e s

/**
 * Class for creating GrADSObject instances. This should be
 * preferred over other methods of creating GrADSObject instances because
 * this method ensures all instances are being run from the same
 * python interpretter.
 *
 * @author Arlindo da Silva <dasilva@alum.mit.edu>
 */
public class GrADSFactory {

    /**
     * Class constructor, starts the factory-wide python interpretter.
     */
    public GrADSFactory() {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from grads import *");
        jyGrADSClass = interpreter.get("GrADS");
    }


    /**
     * Create a GrADSObject with default quietness and GrADS binary
     * @return GrADSObject object for interacting with grads
     * @see org.opengrads.interfaces.GrADSObject
     */
    public GrADSObject create() {

           PyObject pyga = jyGrADSClass.__call__();
        GrADSObject   ga = (GrADSObject) pyga.__tojava__(GrADSObject.class);

        return ga;
    }

    /**
     * Create a GrADSObject with a default quietness and custom GrADS binary
     * @param Bin GrADS binary name
     * @return GrADSObject object for interacting with grads
     * @see org.opengrads.interfaces.GrADSObject
     */
    public GrADSObject create(String Bin) {

           PyObject pyga = jyGrADSClass.__call__(new PyString(Bin));
        GrADSObject   ga = (GrADSObject) pyga.__tojava__(GrADSObject.class);

        return ga;
    }

    /**
     * Create a GrADSObject with a custom quietness and custom GrADS binary
     * @param Bin GrADS binary name
     * @param Echo 0 for quietness, 1 to echo back GrADS output
     * @return GrADSObject object for interacting with grads
     * @see org.opengrads.interfaces.GrADSObject
     */
    public GrADSObject create(String Bin, int Echo) {

           PyObject pyga = jyGrADSClass.__call__(new PyString(Bin),
                                                 new PyInteger(Echo));
        GrADSObject   ga = (GrADSObject) pyga.__tojava__(GrADSObject.class);

        return ga;
    }

    private PyObject jyGrADSClass;

}
