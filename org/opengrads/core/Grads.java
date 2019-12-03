package org.opengrads.core;

import java.util.*;
import org.opengrads.interfaces.*;
import org.python.core.*;
import org.python.util.*;


/**
 * A GrADS client class for interacting with GrADS from java. 
 *
 * @author Arlindo da Silva <dasilva@alum.mit.edu>\
 */
public class Grads {
    public  GrADSObject ga;
    private PyObject jyGrADSClass;

    // Constructor
    /**
     * Start the GrADS process.
     * Instantiation with this constructor implies non-quiet behavior, meaning
     * that things GrADS would have printed to stdout gets printed to stdout.
     */
    public Grads () {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from grads import *");
        jyGrADSClass = interpreter.get("GrADS");
        PyObject pyga = jyGrADSClass.__call__();
        ga = (GrADSObject) pyga.__tojava__(GrADSObject.class);
    }
    
    /**
     * Start the GrADS process, with a custom GrADS binary name.
     * Instantiation with this constructor implies non-quiet behavior, meaning
     * that things GrADS would have printed to stdout get printed to stdout.
     *
     * @param Bin name of GrADS binary (need not be full path)
     */
    public Grads (String Bin) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from grads import *");
        jyGrADSClass = interpreter.get("GrADS");
        PyObject pyga = jyGrADSClass.__call__( new PyString(Bin) );
        ga = (GrADSObject) pyga.__tojava__(GrADSObject.class);
    }

    /**
     * Start the GrADS process, with a custom GrADS binary name and quietness
     * setting.
     * @param Bin name of GrADS binary (need not be full path)
     * @param Echo 1 for quiet behavior, 0 for non-quiet behavior. Quiet
     *             behavior determines whether the things GrADS prints will 
     *             be echoed to stdout.
     */
    public Grads (String Bin, int Echo ) {
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec("from grads import *");
        jyGrADSClass = interpreter.get("GrADS");
        PyObject pyga = jyGrADSClass.__call__( new PyString(Bin),
                                               new PyInteger(Echo) );
        ga = (GrADSObject) pyga.__tojava__(GrADSObject.class);
    }

    // Public Methods - Wrapper around 
    
    /**
     * Sends a command to GrADS. The output will be stored internally and
     * can be accessed through the <code>rline()</code>, <code>rline(int i)</code>,
     * and <code>rword(int i, int j)</code> methods.
     *
     * @param cmd Command to send to GrADS.
     *
     * @see org.opengrads.core.Grads#rline()
     * @see org.opengrads.core.Grads#rline(int i)
     * @see org.opengrads.core.Grads#rword(int i, int j)
     */
    public void   cmd (String cmd)                
             { ga.cmd (       cmd); }

    /**
     * Sends a command to GrADS. The output will be stored internally and
     * can be accessed through the <code>rline()</code>, <code>rline(int i)</code>,
     * and <code>rword(int i, int j)</code> methods.
     *
     * This method allows for an additional argument override whether
     * the output will be echoed to stdout.
     *
     * @param cmd Command to send to GrADS.
     * @param Quiet 0 to echo of GrADS output to stdout, 1 to restrict it.
     *
     * @see org.opengrads.core.Grads#rline()
     * @see org.opengrads.core.Grads#rline(int i)
     * @see org.opengrads.core.Grads#rword(int i, int j)
     */
    public void   cmd (String cmd, int Quiet)
             { ga.cmd (       cmd,     Quiet); }
    
    /**
     * Flushes the GrADS pipes. This is primarily an internal 
     * method that sometimes can be useful to the end-user. If for
     * some reason the echo you see on the screen seems out of sync
     * with the command you just issued, a call to *flush()* may be your
     * ticket. (This situation is usually a symptom of an internal
     * error.)
     */
    public void   flush ()
             { ga.flush (); }


    /**
     * Returns the number of lines available in the most recent GrADS output.
     * @return number of lines available in most recent GrADS output.
     */
    public int rline ()                   
      { return ga.rline (); }

    /**
     * Returns the ith line of the most recent GrADS output.
     * Note: first line <-> i=1
     * @param i line number
     * @return ith line from the most recent GrADS output
     */
    public String rline (int i) 
      { return ga.rline (    i); }

    /**
     * Returns the jth word of the ith line of the most recent GrADS output.
     * Note: first line <-> i=1, and 
     *       first word <-> j=1
     * @param i line number
     * @param j word number
     * @return jth word of the ith line of the most recent GrADS output.
     */
    public String rword (int i, int j)
      { return ga.rword (    i,     j); }


    /**
     * Queries GrADS internal state and returns a GaHandle object
     * with the results of the query.
     *
     * <pre>
     * HashMap qh = ga.query(what);
     *
     * where *what* is the name of the properpty being queried.
     * When Quiet=True echo of GrADS commands is temporarily 
     * suppressed. The following properties are implemented:
     *
     *
     * HashMap qh = ga.query ( "file #" );
     *
     * Returns information about the file number "#". If the file number
     * is ommitted the default file is used. The following *qh* attributes
     * are defined:
     *
     *    fid        - the file Id number
     *    title      - the title of the dataset
     *    desc       - the description of the dataset
     *    bin        - the binary file name
     *    type       - file type
     *    nx         - number of longitudinal points
     *    ny         - number of latitudinal points
     *    nz         - number of vertical levels
     *    nt         - number of times on file
     *    nvars      - number of variables on file
     *    vars       - list of variable names in the file
     *    var_levs   - list of number of levels for each variable
     *    var_titles - list of long names for each variable
     *    var_info   - list of tuplet triplets (var,levs,title) for each variable
     *
     * 
     * Hashmap qh = ga.query ( "dims" );
     * 
     *  Returns information about the dimension environment. The following
     *             *qh* attributes are define:
     *                                                                       
     *     dfile   - default file number
     *     x_state - x-coordinate state: "fixed" or "varying"
     *     lon     - longitudinal range
     *     x       - x-index range
     *     y_state - y-coordinate state: "fixed" or "varying"
     *     lat     - latitudinal range
     *     y       - y-index range
     *     z_state - z-coordinate state: "fixed" or "varying"
     *     lev     - level range
     *     z       - z-index range
     *     t_state - time-coordinate state: "fixed" or "varying"
     *     time    - time range
     *     t       - t-index range
     * </pre>
     */
    public HashMap query (String Args)          
      { return ga.jquery (       Args); }
    
    /**
     * Queries GrADS internal state and returns a GaHandle object with the results
     * of the query; this method override allows for you to specify quietness.
     * @see org.opengrads.core.Grads#query(String)
     */
    public HashMap query (String Args, int Quiet)  
      { return ga.jquery (       Args,     Quiet); }


    /**
     * Opens a GrADS file, returning the relevant metadata. 
     * @param fname Name of file to open
     * @return HashMap with query info and id of the opened file
     * @see org.opengrads.core.Grads#open(String fname, String ftype)
     * @see org.opengrads.core.Grads#open(String fname, String ftype, int Quiet)
     */
    public HashMap open (String fname)     
      { return ga.jopen (       fname); }

    /**
     * Opens a GrADS file with specified file type, returning relevant metadata.
     * @param fname Name of file to open
     * @param ftype Type of file to open. Should be one of: <pre>
     * ctl      the classic GrADS control file; uses the "open" command
     * sdf      NetCDF, HDF or a DODS URL; uses the "sdfopen" command
     * xdf      NetCDF, HDF or a DODS URL; uses the "xdfopen" command
     * default  Open command is Guessed from the file name; the heuristic
     *          algorithm works pretty well but is not perfect.
     *</pre>
     * @return HashMap with query info and id of opened file
     * @see org.opengrads.core.Grads#open(String fname,)
     * @see org.opengrads.core.Grads#open(String fname, String ftype, int Quiet)
     */
    public HashMap open (String fname, String ftype)     
      { return ga.jopen (       fname,        ftype); }

    /**
     * Opens a GrADS file with specified file type and quietness, returning
     * relevant metadata.
     * @param fname Name of file to open
     * @param ftype Type of file to open. Should be one of: <pre>
     * ctl      the classic GrADS control file; uses the "open" command
     * sdf      NetCDF, HDF or a DODS URL; uses the "sdfopen" command
     * xdf      NetCDF, HDF or a DODS URL; uses the "xdfopen" command
     * default  Open command is Guessed from the file name; the heuristic
     *          algorithm works pretty well but is not perfect.
     *</pre>
     * @param Quiet 0 to allow echo of GrADS ouput, 1 to restrict
     * @return HashMap with query info and id of opened file
     * @see org.opengrads.core.Grads#open(String fname)
     * @see org.opengrads.core.Grads#open(String fname, String ftype)
     */
    public HashMap open (String fname, String ftype, int Quiet)     
      { return ga.jopen (       fname,        ftype,     Quiet); }


    /**
     * Returns a HashMap with attributes defining the following
     * coordinate variables associated with the current dimension
     * environment
     * <pre>
     * name  ---  set to "coords"
     * undef ---  based on default file
     * dims  ---  list with names of varying dimensions
     * denv  ---  dimension environment: result of query("dims")
     * time  ---  list with time, e.g., ["00Z01JAN1987", "12Z01JAN1987" ]
     * lev   ---  float[] with vertical levels
     * lat   ---  float[] with latitudes
     * lon   ---  float[] with longitudes
     * </pre>
     * @return HashMap with attributes assosciated with current
     * dimension environment.
     */
    public HashMap coords () { return ga.jcoords(); }



    /**
     * Exports GrADS expression *expr*, returning a flat float array.
     *
     * @param expr GrADS expression to export.
     * @return flat float[] of exported values.
     */
    public float [] eval ( String expr ) { return ga.eval(expr); }

}
