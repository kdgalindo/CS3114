package project1;
/**
 * On my honor:
 *
 * - I have not used source code obtained from another student,
 * or any other unauthorized source, either modified or
 * unmodified.
 *
 * - All source code and documentation used in my program is
 * either my original work, or was derived by me from the
 * source code published in the textbook for this course.
 *
 * - I have not discussed coding details about this project with
 * anyone other than my partner (in the case of a joint
 * submission), instructor, ACM/UPE tutors or the TAs assigned
 * to this course. I understand that I may discuss the concepts
 * of this program with other students, and that another student
 * may help me debug my program so long as neither of us writes
 * anything during the discussion or modifies any computer file
 * during the discussion. I have violated neither the spirit nor
 * letter of this restriction.
 */

/**
 * Coursemanager1 Class
 *
 * @author aisiadinso Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-19
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
	        Parser p = new Parser();
	        p.parse(fn);
    	}
    	else {
    		System.out.println("What's up?");
    	}
    }
}
