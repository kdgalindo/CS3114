//package course;
//
//import java.util.ArrayList;
//import student.TestCase;
//
///**
// * CourseManagerTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kyleg997 Kyle Galindo
// * @version 2019-10-20
// */
//public class CourseManagerTest extends TestCase {
//    private CourseManager cmanager;
//    private StudentRecord sr1;
//    private StudentRecord sr2;
//
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        cmanager = new CourseManager();
//        Name n = new Name("Kyle", "Galindo");
//        Student s = new Student(0, n);
//        sr1 = new StudentRecord(s);
//        n = new Name("Angel", "Isiadinso");
//        s = new Student(100000000, n);
//        sr2 = new StudentRecord(s, 100, "A ");
//    }
//
//    /**
//     * Tests currentSectionNumber() method
//     */
//    public void testCurrentSectionNumber() {
//        assertEquals(1, cmanager.currentSectionNumber());
//        cmanager.section(2);
//        assertEquals(2, cmanager.currentSectionNumber());
//        assertTrue(cmanager.isCurrentSectionActive());
//    }
//
//    /**
//     * Tests isAStudentGradeable() method
//     */
//    public void testIsAStudentGradeable() {
//        cmanager.loadstudentdata();
//        assertEquals(false, cmanager.isAStudentGradable());
//    }
//
//    /**
//     * Tests the loadcoursedata()
//     */
//    public void testLoadCourseData() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(2, sr2));
//    }
//
//    /**
//     * Tests the clearcoursedata() method
//     */
//    public void testClearCourseData() {
//        sr1.setGrade("A ");
//        sr1.setScore(96);
//        sr1.setActive(true);
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        cmanager.insert(newStudent);
//        assertEquals(true, cmanager.isAStudentGradable());
//        assertEquals(3, cmanager.dumpNames());
//        assertEquals(3, cmanager.dumpPIDs());
//        assertEquals(3, cmanager.dumpScores());
//        assertNotNull(cmanager.searchid(940938457));
//        cmanager.clearcoursedata();
//        assertEquals(0, cmanager.dumpNames());
//        assertEquals(0, cmanager.dumpPIDs());
//        assertEquals(0, cmanager.dumpScores());
//        assertEquals(false, cmanager.isAStudentGradable());
//        assertNull(cmanager.searchid(940938457));
//    }
//
//    /**
//     * Tests the clearstudentrecord() method
//     */
//    public void testClearStudentRecord() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        cmanager.insert(newStudent);
//        assertEquals(3, cmanager.dumpNames());
//        assertEquals(3, cmanager.dumpPIDs());
//        assertEquals(3, cmanager.dumpScores());
//        cmanager.clearsection();
//        assertEquals(0, cmanager.dumpNames());
//        assertEquals(0, cmanager.dumpPIDs());
//        assertEquals(0, cmanager.dumpScores());
//
//    }
//
//    /**
//     * Tests all three dump methods
//     */
//    public void testDump() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        cmanager.insert(newStudent);
//        assertEquals(3, cmanager.dumpNames());
//        assertEquals(3, cmanager.dumpPIDs());
//        assertEquals(3, cmanager.dumpScores());
//    }
//
//    /**
//     * Tests the grade method
//     */
//    public void testGrade() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(95);
//        cmanager.grade();
//        assertEquals("A ", nsr.getGrade());
//    }
//
//    /**
//     * Tests the search by name method
//     */
//    public void testSearchByName() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(95);
//
//        ArrayList<StudentRecord> recordlist = cmanager.search(n);
//        ArrayList<StudentRecord> recordlist2 = cmanager.search("John");
//        assertEquals(1, recordlist.size());
//        assertEquals("Smith", recordlist.get(0).getLast());
//        assertEquals(1, recordlist2.size());
//        assertEquals("Smith", recordlist2.get(0).getLast());
//    }
//
//    /**
//     * Tests the savecoursedata() command
//     */
//    public void testSaveCourseData() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        byte[] data = cmanager.savecoursedata();
//        assertEquals(53, data.length);
//    }
//
//    /**
//     * Tests score function
//     */
//    public void testScore() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(95);
//        cmanager.score(60);
//        ArrayList<StudentRecord> recordlist = cmanager.search(n);
//        assertEquals(60, recordlist.get(0).getScore());
//
//    }
//
//    /**
//     * Tests both remove functions
//     */
//    public void testRemove() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(95);
//        Name n2 = new Name("Angel", "Isiadinso");
//        cmanager.remove(n2);
//
//        ArrayList<StudentRecord> recordlist = cmanager.search(n2);
//        assertEquals(0, recordlist.size());
//
//        cmanager.remove(940938457);
//        ArrayList<StudentRecord> recordlist2 = cmanager.search(n2);
//        assertEquals(0, recordlist2.size());
//
//    }
//
//    /**
//     * Tests stat function
//     */
//    public void testStat() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(95);
//        cmanager.grade();
//        Name n2 = new Name("Kyle", "Galindo");
//        ArrayList<StudentRecord> recordlist = cmanager.search(n2);
//        assertEquals("F ", recordlist.get(0).getGrade());
//        assertEquals("A ", nsr.getGrade());
//        int[] stats = cmanager.stat();
//        assertEquals(2, stats[0]);
//        assertEquals(1, stats[11]);
//
//    }
//
//    /**
//     * Tests the list function
//     */
//    public void testList() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        cmanager.score(100);
//        cmanager.grade();
//        assertEquals("A ", nsr.getGrade());
//        ArrayList<StudentRecord> recordlist = cmanager.list("F");
//        assertEquals(1, recordlist.size());
//        assertEquals("Kyle", recordlist.get(0).getFirst());
//
//    }
//
//    /**
//     * Tests the findpair method
//     */
//    public void testFindPair() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(1, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(100);
//        cmanager.grade();
//        ArrayList<String> recordlist = cmanager.findpair(0);
//        assertEquals("John Smith, Angel Isiadinso", recordlist.get(0));
//        ArrayList<String> recordlist2 = cmanager.findpair(-1);
//        assertEquals(0, recordlist2.size());
//    }
//
//    /**
//     * Tests the merge method
//     */
//    public void testmerge() {
//        assertEquals(sr1, cmanager.loadcoursedata(1, sr1));
//        assertEquals(sr2, cmanager.loadcoursedata(2, sr2));
//        Name n = new Name("John", "Smith");
//        Student newStudent = new Student(940938457, n);
//        StudentRecord nsr = cmanager.insert(newStudent);
//        nsr.setScore(100);
//        cmanager.grade();
//        cmanager.section(3);
//        assertTrue(cmanager.merge());
//        assertEquals(3, cmanager.currentSectionNumber());
//    }
//}
