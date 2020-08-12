//package student;
//
//import student.TestCase;
//
///**
// * StudentRecordTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class StudentRecordTest extends TestCase {
//    private StudentRecord sr1;
//    private StudentRecord sr2;
//
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        Name n = new Name("Kyle", "Galindo");
//        Student s = new Student(0, n);
//        sr1 = new StudentRecord(s);
//        n = new Name("Angel", "Isiadinso");
//        s = new Student(100000000, n);
//        sr2 = new StudentRecord(s, 100, "A ");
//    }
//    
//    /**
//     * Tests the getActive() method
//     */
//    public void testIsActive() {
//        boolean a = sr1.isActive();
//        assertTrue(a);
//    }
//    
//    /**
//     * Tests the getStudent() method
//     */
//    public void testGetStudent() {
//        String s = sr1.getStudent().toString();
//        assertEquals(s, "000000000, Kyle Galindo");
//    }
//    
//    /**
//     * Tests the getPID() method
//     */
//    public void testGetPID() {
//        long p = sr1.getPID();
//        assertEquals(p, 0);
//        p = sr2.getPID();
//        assertEquals(p, 100000000);
//    }
//
//    /**
//     * Tests the getName() method
//     */
//    public void testGetName() {
//        String n = sr2.getName().toString();
//        assertEquals(n, "Angel Isiadinso");
//    }
//    
//    /**
//     * Tests the getFirst() method
//     */
//    public void testGetFirst() { 
//        String f = sr2.getFirst();
//        assertEquals(f, "Angel");
//    }
//    
//    /**
//     * Tests the getLast() method
//     */
//    public void testGetLast() {
//        String l = sr2.getLast();
//        assertEquals(l, "Isiadinso");
//    }
//    
//    /**
//     * Tests the getScore() method
//     */
//    public void testGetScore() {
//        int s = sr1.getScore();
//        assertEquals(s, 0);
//        s = sr2.getScore();
//        assertEquals(s, 100);
//    }
//    
//    /**
//     * Tests the getGrade() method
//     */
//    public void testGetGrade() {
//        String g = sr1.getGrade();
//        assertEquals(g, "F ");
//        g = sr2.getGrade();
//        assertEquals(g, "A ");
//    }
//    
//    /**
//     * Tests the setActive() method
//     */
//    public void testSetActive() {
//        boolean a = sr1.setActive(false);
//        assertFalse(a);
//    }
//
//    /**
//     * Tests the setScore() method
//     */
//    public void testSetScore() {
//        int s = sr1.setScore(89);
//        assertEquals(s, 89);
//    }
//    
//    /**
//     * Tests the setGrade() method
//     */
//    public void testSetGrade() {
//        String g = sr1.setGrade("B+");
//        assertEquals(g, "B+");
//    }
//
//    /**
//     * Tests the toString() method
//     */
//    public void testToString() {
//        String s = sr2.toString();
//        assertEquals(s, "100000000, Angel Isiadinso, score = 100");
//    }
//}
