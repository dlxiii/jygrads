
import org.opengrads.interfaces.*;
import org.opengrads.factory.*;

public class TestJyGrADS {
    public static void main ( String[] argv ) {
        GrADSFactory factory = new GrADSFactory();
        GrADSObject ga = factory.create("grads -b", 0);
	ga.cmd("q");
        ga.cmd("q config");
	System.out.println("GrADS Version is "+ga.rword(1,2));
    }
}
