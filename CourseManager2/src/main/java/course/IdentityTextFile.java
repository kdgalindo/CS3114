package course;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import data.FullName;
import data.Identity;

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
		Identity identity = null;
        Scanner s = new Scanner(line);
        s.useDelimiter("\\s*,\\s*");
        long personalID = s.nextLong();
        identity = new Identity(personalID, readFullNameFrom(s));
        s.close();
        return identity;
	}
	
	private static FullName readFullNameFrom(Scanner s) {
		String firstName = s.next();
        String middleName = s.next();
        String lastName = s.next();
        return new FullName(firstName, middleName, lastName);
	}
}
