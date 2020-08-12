//package course;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import student.TestCase;
//
///**
// * SectionTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kylegg7 Kyle Galindo
// * @version 2019-10-20
// */
//public class SectionTest extends TestCase {
//    private Section s1;
//    private Section s2;
//
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        s1 = new Section();
//        s2 = new Section(2);
//        s1.insertStudent(new Student(977159896, new Name("Naomi", "Cote")), 65,
//            0);
//        s1.insertStudent(new Student(394691224, new Name("Aubrey",
//            "Williamson")), 100, 1);
//        s2.insertStudent(new Student(67964700, new Name("Fritz", "Hudson")), 78,
//            2);
//        s2.insertStudent(new Student(248476061, new Name("Winter", "Hodge")),
//            31, 3);
//        s2.insertStudent(new Student(291935757, new Name("Brynne", "Myers")), 4,
//            4);
//        s2.insertStudent(new Student(792704751, new Name("Leroy", "Sherman")),
//            65, 5);
//        s1.insertStudent(new Student(20380028, new Name("Sage", "Forbes")), 4,
//            6);
//        s2.insertStudent(new Student(256593948, new Name("Sandra", "Duncan")),
//            26, 7);
//        s2.insertStudent(new Student(317397180, new Name("Nigel", "Gonzales")),
//            37, 8);
//        // s1.insertStudent(new Student(977159896, new Name("Naomi", "Cote")),
//        // 97, 0);
//    }
//
//    /**
//     * Tests the number() method
//     */
//    public void testNumber() {
//        int n = s1.number();
//        assertEquals(n, 1);
//        n = s2.number();
//        assertEquals(n, 2);
//    }
//
//    /**
//     * Tests the isActive() method
//     */
//    public void testIsActive() {
//        boolean a = s1.isActive();
//        assertTrue(a);
//    }
//
//    /**
//     * Tests the setActive() method
//     */
//    public void testSetActive() {
//        boolean a = s1.setActive(false);
//        assertFalse(a);
//    }
//
//    /**
//     * Tests the size() method
//     */
//    public void testSize() {
//        int s = s1.size();
//        assertEquals(s, 3);
//        s = s2.size();
//        assertEquals(s, 6);
//    }
//
//    /**
//     * Tests the isEmpty() method
//     */
//    public void testIsEmpty() {
//        boolean ie = s1.isEmpty();
//        assertFalse(ie);
//    }
//
//    /**
//     * Tests the insertStudent() method
//     */
//    public void testInsertStudent() {
//        s1.insertStudent(new Student(239721905, new Name("Aileen", "Ford")), 0,
//            9);
//        int i = s1.searchByPID(239721905);
//        assertEquals(i, 9);
//    }
//
//    /**
//     * Tests the searchByPID() method
//     */
//    public void testSearchByPID() {
//        int i = s1.searchByPID(394691224);
//        assertEquals(i, 1);
//        Integer j = s1.searchByPID(291935757);
//        assertNull(j);
//    }
//
//    /**
//     * Tests the searchByName() method
//     */
//    public void testSearchByName() {
//        ArrayList<Integer> il = s1.searchByName(new Name("Mostafa", "Kamel"));
//        assertEquals(il.size(), 0);
//        il = s1.searchByName(new Name("Sage", "Forbes"));
//        assertEquals(il.size(), 1);
//    }
//
//    /**
//     * Tests the updateStudentScore() method
//     */
//    public void testUpdateStudentScore() {
//        s1.updateStudentScore(65, 97, 0);
//        ArrayList<Integer> il = s1.searchForScoresInRange(97, 97);
//        assertEquals(il.size(), 1);
//        s1.insertStudent(new Student(239721905, new Name("Aileen", "Ford")), 0,
//            9);
//        s1.updateStudentScore(0, 91, 9);
//        il = s1.searchForScoresInRange(91, 91);
//        assertEquals(il.size(), 1);
//    }
//
//    /**
//     * Tests the removeStudentPID() method
//     */
//    public void testRemoveStudentPID() {
//        int i = s2.removeStudentPID(256593948);
//        assertEquals(i, 7);
//    }
//
//    /**
//     * Tests the clear() method
//     */
//    public void testClear() {
//        s1.clear();
//        boolean ie = s1.isEmpty();
//        assertTrue(ie);
//        s2.clear();
//        ie = s2.isEmpty();
//        assertTrue(ie);
//    }
//
//    /**
//     * Tests the searchForScoresInRange() method
//     */
//    public void testSearchForScoresInRange() {
//        ArrayList<Integer> il = s2.searchForScoresInRange(58, 69);
//        assertEquals(il.size(), 1);
//    }
//
//    /**
//     * Tests the iterateByPID() method
//     */
//    public void testIterateByPID() {
//        Iterator<Integer> itr = s1.iterateByPID();
//        int i = itr.next();
//        assertEquals(i, 6);
//        i = itr.next();
//        assertEquals(i, 1);
//        i = itr.next();
//        assertEquals(i, 0);
//    }
//
//    /**
//     * Tests the iterateByName() method
//     */
//    public void testIterateByName() {
//        Iterator<Integer> itr = s1.iterateByName();
//        int i = itr.next();
//        assertEquals(i, 0);
//        i = itr.next();
//        assertEquals(i, 6);
//        i = itr.next();
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Tests the iterateByScore() method
//     */
//    public void testIterateByScore() {
//        Iterator<Integer> itr = s1.iterateByScore();
//        int i = itr.next();
//        assertEquals(i, 6);
//        i = itr.next();
//        assertEquals(i, 0);
//        i = itr.next();
//        assertEquals(i, 1);
//    }
//}
