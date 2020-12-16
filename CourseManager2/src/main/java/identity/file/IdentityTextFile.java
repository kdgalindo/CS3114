package identity.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import identity.FullName;
import identity.Identity;

/**
 * IdentityTextFile Class
 * 
 * Identity text files are files that contain identity data (text format)
 * and can be read from.
 * 
 * TODO Improve exception handling and try-catch blocks.
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-16
 */
public final class IdentityTextFile {
	private IdentityTextFile() {
		// Empty
	}
	
	/**
	 * 
	 * @param filename Identity text data file name
	 * @return An immutable list of identities parsed from the identity text
	 * data file if no reading errors occur, otherwise an empty list.
	 */
	public static List<Identity> readFrom(String filename) {
		List<Identity> identities = new ArrayList<Identity>();
		try {
			Scanner s = new Scanner(new File(filename));
	        while (s.hasNextLine()) {
	        	identities.add(readIdentityFrom(s.nextLine()));
	        }
	        s.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
        return Collections.unmodifiableList(identities);
	}
	
	private static Identity readIdentityFrom(String line) {
        Scanner s = new Scanner(line);
        s.useDelimiter("\\s*,\\s*");
        Identity identity = new Identity(s.nextLong(), readFullNameFrom(s));
        s.close();
        return identity;
	}
	
	private static FullName readFullNameFrom(Scanner s) {
        return new FullName(s.next(), s.next(), s.next());
	}
}
