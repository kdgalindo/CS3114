package identity.file;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import identity.FullName;
import identity.Identity;

/**
 * IdentityBinaryFile Class
 * 
 * Identity binary files are files that contain identity data (binary format)
 * and can be read from or written to.
 * 
 * TODO Improve exception handling and try-catch blocks.
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-16
 */
public final class IdentityBinaryFile {
	private final static String HEADER = "VTSTUDENTS";
	private final static String DELIMITER = "GOHOKIES";
	
	private IdentityBinaryFile() {
		// Empty
	}
	
	/**
	 * 
	 * @param filename Identity binary data file name
	 * @return An immutable list of identities parsed from the identity binary
	 * data file if no reading errors occur, otherwise an empty list.
	 */
	public static List<Identity> readFrom(String filename) {
		Identity[] identities = new Identity[0];
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "r");
			raf.seek(HEADER.length());
			identities = new Identity[raf.readInt()];
			for (int i = 0; i < identities.length; ++i) {
				identities[i] = readIdentityFrom(raf);
				raf.seek(raf.getFilePointer() + DELIMITER.length());
			}
			raf.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
        return Collections.unmodifiableList(Arrays.asList(identities));
	}
	
	private static Identity readIdentityFrom(RandomAccessFile raf) throws IOException {
		return new Identity(raf.readLong(), readFullNameFrom(raf));
	}
	
	private static FullName readFullNameFrom(RandomAccessFile raf) throws IOException {
        return new FullName(readNameFrom(raf), readNameFrom(raf), readNameFrom(raf));
	}
	
	private static String readNameFrom(RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		char letter;
		while ((letter = (char)raf.readByte()) != '$') {
			sb.append(letter);
		}
		return sb.toString();
	}
	
	/**
	 * Generates an identity binary data file from the given filename and list
	 * of identities if no writing errors occur, otherwise ...
	 * 
	 * @param identities List of identities
	 * @param filename Identity binary data file name
	 */
    public static void writeTo(List<Identity> identities, String filename) {
    	try {
			RandomAccessFile raf = new RandomAccessFile(filename, "rw");
			raf.writeBytes(HEADER);
			int numOfIdentities = identities.size();
			raf.writeInt(numOfIdentities);
			for (int i = 0; i < numOfIdentities; ++i) {
				writeIdentityTo(identities.get(i), raf);
				raf.writeBytes(DELIMITER);
			}
			raf.close();
		}
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static void writeIdentityTo(Identity identity, RandomAccessFile raf) throws IOException {
    	raf.writeLong(identity.getPersonalID());
    	writeFullNameTo(identity.getFullName(), raf);
    }
    
    private static void writeFullNameTo(FullName fullName, RandomAccessFile raf) throws IOException {
    	writeNameTo(fullName.getFirstName(), raf);
    	writeNameTo(fullName.getMiddleName(), raf);
    	writeNameTo(fullName.getLastName(), raf);
    }
    
    private static void writeNameTo(String name, RandomAccessFile raf) throws IOException {
    	raf.writeBytes(name + "$");
    }
}
