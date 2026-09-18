import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import org.atpfivt.ljv.*;

public class DisplayDS{

    public static void browse(LJV ljv, Object obj) {
        try {
            var dot = URLEncoder.encode(
                    ljv.drawGraph(obj), "UTF8").replaceAll("\\+", "%20");
            Desktop.getDesktop().browse(
                    new URI("https://dreampuf.github.io/GraphvizOnline/#"
                            + dot));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public static void main( String [] args ) {
        System.out.println("START TestingLJV");

        DisplayDS.browse(new LJV(), new String[] {"abc", "cat"});
        browse(new LJV().setTreatAsPrimitive(String.class), new String[] {"abc", "cat"});

        System.out.println("END TestingLJV");

        
    }
}
