package identity.file;

import java.io.IOException;
import java.io.RandomAccessFile;

import identity.FullName;
import identity.Identity;

/**
 * IdentityBinaryFile Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-11-05
 */
public class IdentityBinaryFile {
	private final static String HEADER = "VTSTUDENTS";
	private final static String DELIMITER = "GOHOKIES";
	
	public static Identity[] readFrom(String filename) {
		Identity[] identities = null;
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
        return identities;
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
	
    public static void writeTo(Identity[] identities, String filename) {
    	try {
			RandomAccessFile raf = new RandomAccessFile(filename, "rw");
			raf.writeBytes(HEADER);
			int numOfIdentities = identities.length;
			raf.writeInt(numOfIdentities);
			for (int i = 0; i < numOfIdentities; ++i) {
				writeIdentityTo(identities[i], raf);
				raf.writeBytes(DELIMITER);
			}
			raf.close();
		} catch (IOException e) {
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
