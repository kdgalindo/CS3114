package identity.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import identity.FullName;
import identity.Identity;

/**
 * IdentityTextFile Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-11-05
 */
public class IdentityTextFile {
	public static Identity[] readFrom(String filename) {
		ArrayList<Identity> identities = new ArrayList<Identity>();
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
        return identities.toArray(new Identity[identities.size()]);
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
