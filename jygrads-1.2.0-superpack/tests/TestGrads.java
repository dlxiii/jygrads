import java.util.*;
import org.opengrads.core.*;

public class TestGrads {
    public static void main ( String[] argv ) {

        Grads ga = new Grads("grads -b",0);

        ga.cmd("q config");
	System.out.println("GrADS Version is "+ga.rword(1,2));

        HashMap fh = ga.open("data/model");
        System.out.println("File metadata:\n "+fh);

        HashMap qh = ga.query("dims");
        System.out.println("Dimension environment:\n "+qh);
        System.out.println("  x = "+qh.get("xx"));

    }
}
