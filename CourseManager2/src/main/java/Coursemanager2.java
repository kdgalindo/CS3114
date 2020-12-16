import interpreter.CmdParser;

/**
 * Coursemanager2 Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-08
 */
public class Coursemanager2 {
    
	/**
     * Main method
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String fn = args[0];
        CmdParser cmdParser = new CmdParser();
        cmdParser.parse(fn);
    }
}
