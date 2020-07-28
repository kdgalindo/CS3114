package project1;

/**
 * Coursemanager1 Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-28
 */
public class Coursemanager1 {
    /**
     * Main
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
    	if (args.length > 0) {
	        String fn = args[0];
//	        Parser p = new Parser();
//	        p.parse(fn);
	        CmdInterpreter cmdInterpreter = new CmdInterpreter();
	        cmdInterpreter.eval(fn);
    	}
    	else {
    		System.out.println("What's up?");
    	}
    }
}
