package identity.file;

import java.util.Collections;
import java.util.List;

import identity.Identity;

/**
 * IdentityFile Class
 * 
 * Identity files are files that contain identity data and can be read from or
 * written to. Able to read from .csv and .data file types. Able to write to
 * .data file type.
 * 
 * TODO Improve visibility of file types able to be read from and written to.
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-16
 */
public final class IdentityFile {
	private IdentityFile() {
		// Empty
	}
	
	/**
	 * 
	 * @param filename Identity data file name
	 * @return An immutable list of identities parsed from the identity
	 * data file if no reading errors occur, otherwise an empty list.
	 */
	public static List<Identity> readFrom(String filename) {
		List<Identity> identities = null;
		switch (getFileType(filename)) {
			case ".csv":
				identities = IdentityTextFile.readFrom(filename);
				break;
			case ".data":
				identities = IdentityBinaryFile.readFrom(filename);
				break;
			default:
				identities = Collections.emptyList();
				break;
		}
        return identities;
	}
	
	/**
	 * Generates an identity data file from the given filename and list of
	 * identities if no writing errors occur, otherwise ...
	 * 
	 * @param identities List of identities
	 * @param filename Identity data file name
	 */
	public static void writeTo(List<Identity> identities, String filename) {
		switch (getFileType(filename)) {
			case ".data":
				IdentityBinaryFile.writeTo(identities, filename);
				break;
			default:
				break;
		}
	}
	
	private static String getFileType(String filename) {
		return filename.substring(filename.lastIndexOf('.'));
	}
}
