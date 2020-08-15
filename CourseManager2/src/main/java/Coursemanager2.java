import interpreter.Parser;
import interpreter.CmdParser;

/**
 * Coursemanager2 Class
 *
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-10-20
 */
public class Coursemanager2 {
    /**
     * Main method
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String fn = args[0];
//        Parser p = new Parser();
//        p.parse(fn);
        CmdParser cmdParser = new CmdParser();
        cmdParser.parse(fn);
    }
}
