//package bin;
//
//import java.io.ByteArrayOutputStream;
//import student.TestCase;
//
///**
// * BinFileHelperTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class BinFileHelperTest extends TestCase {
//    private BinFileHelper bfh;
//    
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        bfh = new BinFileHelper();
//    }
//    
//    /**
//     * Tests the byteArrayToInt() method
//     */
//    public void testByteArrayToInt() {
//        int i = 65;
//        byte[] ba = { 0x00, 0x00, 0x00, 0x41 };
//        int j = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, j);
//    }
//    
//    /**
//     * Tests the intToByteArray() method
//     */
//    public void testIntToByteArray() {
//        int i = 65;
//        byte[] ba = bfh.intToByteArray(i);
//        int j = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, j);
//    }
//    
//    /**
//     * Tests the byteArrayToLong() method
//     */
//    public void testByteArrayToLong() {
//        long i = 977159896;
//        byte[] ba = { 0x00, 0x00, 0x00, 0x00, 0x3A, 0x3E, 0x46, (byte)0xD8 };
//        long j = bfh.byteArrayToLong(ba, 0);
//        assertEquals(i, j);
//    }
//    
//    /**
//     * Tests the longToByteArray() method
//     */
//    public void testLongToByteArray() {
//        long i = 977159896;
//        byte[] ba = bfh.longToByteArray(i);
//        long j = bfh.byteArrayToLong(ba, 0);
//        assertEquals(i, j);
//    }
//    
//    /**
//     * Tests the byteArrayToName() method
//     */
//    public void testByteArrayToName() {
//        byte[] ba = "Winter$".getBytes();
//        String n = bfh.byteArrayToName(ba, 0);
//        assertEquals(n, "Winter");
//        ba = "$".getBytes();
//        n = bfh.byteArrayToName(ba, 0);
//        assertEquals(n, "");
//        ba = "Hodge$".getBytes();
//        n = bfh.byteArrayToName(ba, 0);
//        assertEquals(n, "Hodge");
//    }
//    
//    /**
//     * Tests the byteArrayHasScoreGrade() method
//     */
//    public void testByteArrayHasScoreGrade() {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            baos.write("Winter$".getBytes());
//            baos.write(bfh.intToByteArray(31));
//            baos.write("F GOHOKIES".getBytes());
//            byte[] ba = baos.toByteArray();
//            boolean hsg = bfh.byteArrayHasScoreGrade(ba, 11);
//            assertTrue(hsg);
//            ba = "Winter$GOHOKIES".getBytes();
//            hsg = bfh.byteArrayHasScoreGrade(ba, 7);
//            assertFalse(hsg);
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
//    /**
//     * Tests the byteArrayToGrade() method
//     */
//    public void testByteArrayToGrade() {
//        byte[] ba = "A ".getBytes();
//        String g = bfh.byteArrayToGrade(ba, 0);
//        assertEquals(g, "A ");
//        ba = "C+".getBytes();
//        g = bfh.byteArrayToGrade(ba, 0);
//        assertEquals(g, "C+");
//    }
//}
