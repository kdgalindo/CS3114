package course;

import data.Identity;

public class IdentityFile {
	public static Identity[] readFrom(String filename) {
		Identity[] identities = null;
		switch (getFileType(filename)) {
			case ".csv":
				identities = IdentityTextFile.readFrom(filename);
				break;
			case ".data":
				identities = IdentityBinaryFile.readFrom(filename);
				break;
			default:
				identities = null;
				break;
		}
        return identities;
	}
	
	public static void writeTo(Identity[] identities, String filename) {
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