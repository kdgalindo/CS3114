package bin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * BinFileHelper Class
 * Binary File Helper
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-10-20
 */
public class BinFileHelper {
    private final String[] letters = { "A ", "A-", "B+", "B ", "B-", "C+", "C ",
        "C-", "D+", "D ", "D-", "F " };

    /**
     * Converts contents of a binary
     * file into a byte array
     * 
     * @param bfn binary filename
     * @return byte array
     */
    public byte[] binFileToByteArray(String bfn) {
        byte[] ba = null;
        try {
            File f = new File(bfn);
            InputStream is = new FileInputStream(f);
            ba = new byte[(int)f.length()];
            is.read(ba);
            is.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ba;
    }

    /**
     * Converts byte array into
     * contents of a binary file
     * 
     * @param ba byte array
     * @param bfn binary filename
     */
    public void byteArrayToBinFile(byte[] ba, String bfn) {
        try {
            OutputStream os = new FileOutputStream(new File(bfn));
            os.write(ba);
            os.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Convert next four bytes, starting
     * at offset, to an integer
     * 
     * @param ba byte array
     * @param off offset
     * @return integer
     */
    public int byteArrayToInt(byte[] ba, int off) {
        int i = 0;
        for (int j = off; j < (off + 4); j++) {
            i <<= 8;
            i |= (ba[j] & 0xFF);
        }
        return i;
    }

    /**
     * Convert an integer to a four
     * byte byte array
     * 
     * @param i integer
     * @return byte array
     */
    public byte[] intToByteArray(int i) {
        byte[] ba = new byte[4];
        for (int j = 3; j >= 0; j--) {
            ba[j] = (byte)(i & 0xFF);
            i >>= 8;
        }
        return ba;
    }

    /**
     * Convert next eight bytes, starting
     * at offset, to a long
     * 
     * @param ba byte array
     * @param off offset
     * @return long
     */
    public long byteArrayToLong(byte[] ba, int off) {
        long l = 0;
        for (int i = off; i < (off + 8); i++) {
            l <<= 8;
            l |= (ba[i] & 0xFF);
        }
        return l;
    }

    /**
     * Convert a long to an eight
     * byte byte array
     * 
     * @param l long
     * @return byte array
     */
    public byte[] longToByteArray(long l) {
        byte[] ba = new byte[8];
        for (int i = 7; i >= 0; i--) {
            ba[i] = (byte)(l & 0xFF);
            l >>= 8;
        }
        return ba;
    }

    /**
     * Convert all bytes, until $
     * is found, to a string
     * 
     * @param ba byte array
     * @param off offset
     * @return string
     */
    public String byteArrayToName(byte[] ba, int off) {
        StringBuilder sb = new StringBuilder();
        for (int i = off; (char)ba[i] != '$'; i++) {
            sb.append((char)ba[i]);
        }
        return sb.toString();
    }

    /**
     * Checks if the byte array
     * contains both a score and
     * a grade
     * 
     * @param ba byte array
     * @param off offset
     * @return TRUE if the byte array
     * contains both a score and a grade,
     * FALSE otherwise
     */
    public boolean byteArrayHasScoreGrade(byte[] ba, int off) {
        boolean retval = false;
        StringBuilder sb = new StringBuilder();
        sb.append((char)ba[off]);
        sb.append((char)ba[off + 1]);
        for (int i = 0; i < letters.length; i++) {
            if (sb.toString().contentEquals(letters[i])) {
                retval = true;
                break;
            }
        }
        return retval;
    }

    /**
     * Convert next two bytes to a grade
     * 
     * @param ba byte array
     * @param off offset
     * @return string
     */
    public String byteArrayToGrade(byte[] ba, int off) {
        StringBuilder sb = new StringBuilder();
        sb.append((char)ba[off]);
        sb.append((char)ba[off + 1]);
        return sb.toString();
    }
}
