//package interpreter;
//
//import student.TestCase;
//
///**
// * TopLevelTest Class
// * 
// * @author ati Angel Isiadinso
// * @author kylegg7 Kyle Galindo
// * @version 2019-10-20
// */
//public class TopLevelTest extends TestCase {
//    private TopLevel toplevel;
//    private BinFileHelper bfh;
//
//    /**
//     * Setup for tests
//     */
//    public void setUp() {
//        toplevel = new TopLevel();
//        bfh = new BinFileHelper();
//        toplevel.loadstudentdatahelper(new Student(248476061, new Name("Winter",
//            "Hodge")));
//        toplevel.loadstudentdatahelper(new Student(256593948, new Name("Sandra",
//            "Duncan")));
//        toplevel.loadstudentdatahelper(new Student(465830040, new Name("Cain",
//            "Buckner")));
//        toplevel.loadstudentdatahelper(new Student(239721905, new Name("Aileen",
//            "Ford")));
//        toplevel.loadstudentdatahelper(new Student(924954712, new Name("Caleb",
//            "Foley")));
//        toplevel.loadstudentdatahelper(new Student(369295480, new Name("Uriah",
//            "Gutierrez")));
//        toplevel.loadstudentdatahelper(new Student(674233010, new Name("Reece",
//            "Conner")));
//        toplevel.loadstudentdatahelper(new Student(291935757, new Name("Brynne",
//            "Myers")));
//        toplevel.loadstudentdatahelper(new Student(349195701, new Name("Gloria",
//            "Chavez")));
//        toplevel.loadstudentdatahelper(new Student(20380028, new Name("Sage",
//            "Forbes")));
//        toplevel.loadstudentdatahelper(new Student(896832193, new Name("Omar",
//            "Dillard")));
//        toplevel.loadstudentdatahelper(new Student(792704751, new Name("Leroy",
//            "Sherman")));
//        toplevel.loadstudentdatahelper(new Student(645652207, new Name("Veda",
//            "Doyle")));
//        toplevel.loadstudentdatahelper(new Student(977159896, new Name("Naomi",
//            "Cote")));
//        toplevel.loadstudentdatahelper(new Student(317397180, new Name("Nigel",
//            "Gonzales")));
//        toplevel.loadstudentdatahelper(new Student(923966162, new Name("Chloe",
//            "Wright")));
//        toplevel.loadstudentdatahelper(new Student(265104158, new Name("Joshua",
//            "Hall")));
//        toplevel.loadstudentdatahelper(new Student(983623406, new Name("Audra",
//            "Hull")));
//        toplevel.loadstudentdatahelper(new Student(211957170, new Name("Oscar",
//            "Knight")));
//        toplevel.loadstudentdatahelper(new Student(475219135, new Name(
//            "Kiayada", "Meyer")));
//        toplevel.loadstudentdatahelper(new Student(115783706, new Name("Colton",
//            "Morrison")));
//        toplevel.loadstudentdatahelper(new Student(427085238, new Name("Leo",
//            "Saunders")));
//        toplevel.loadstudentdatahelper(new Student(67964700, new Name("Fritz",
//            "Hudson")));
//        toplevel.loadstudentdatahelper(new Student(394691224, new Name("Aubrey",
//            "Williamson")));
//        toplevel.loadstudentdatahelper(new Student(635043110, new Name(
//            "Ishmael", "Carlson")));
//        toplevel.loadcoursedatahelper(1, new StudentRecord(new Student(
//            977159896, new Name("Naomi", "Cote")), 65, "C+"));
//        toplevel.loadcoursedatahelper(1, new StudentRecord(new Student(
//            394691224, new Name("Aubrey", "Williamson")), 100, "A"));
//        toplevel.loadcoursedatahelper(2, new StudentRecord(new Student(67964700,
//            new Name("Fritz", "Hudson")), 78, "B"));
//        toplevel.loadcoursedatahelper(2, new StudentRecord(new Student(
//            248476061, new Name("Winter", "Hodge")), 31, "F"));
//        toplevel.loadcoursedatahelper(2, new StudentRecord(new Student(
//            291935757, new Name("Brynne", "Myers")), 4, "F"));
//        toplevel.loadcoursedatahelper(2, new StudentRecord(new Student(
//            792704751, new Name("Leroy", "Sherman")), 65, "C+"));
//        toplevel.loadcoursedatahelper(1, new StudentRecord(new Student(20380028,
//            new Name("Sage", "Forbes")), 4, "F"));
//        toplevel.loadcoursedatahelper(2, new StudentRecord(new Student(
//            256593948, new Name("Sandra", "Duncan")), 26, "F"));
//        toplevel.loadcoursedatahelper(2, new StudentRecord(new Student(
//            317397180, new Name("Nigel", "Gonzales")), 37, "F"));
//        toplevel.loadcoursedatahelper(1, new StudentRecord(new Student(
//            977159896, new Name("Naomi", "Cote")), 97, "A"));
//    }
//
//    /**
//     * Test the section() method
//     */
//    public void testSection() {
//        toplevel.section(1);
//        toplevel.section(2);
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the insert() method
//     */
//    public void testInsert() {
//        toplevel.section(1);
//        toplevel.insert(977159896, "Naomi", "Cote");
//        toplevel.insert(256593948, "Sandra", "Duncan");
//        toplevel.insert(239721904, "Aileen", "Ford");
//        toplevel.insert(239721905, "Caleb", "Foley");
//        toplevel.insert(239721905, "Aileen", "Ford");
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the searchid() method
//     */
//    public void testSearchID() {
//        toplevel.section(1);
//        toplevel.searchid(394691224);
//        toplevel.searchid(291935757);
//        toplevel.searchid(239721905);
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the search() method
//     */
//    public void testSearchName() {
//        toplevel.section(1);
//        toplevel.search("Mostafa", "Kamel");
//        toplevel.search("Sage", "Forbes");
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the search() method
//     */
//    public void testSearchString() {
//        toplevel.section(1);
//        toplevel.search("Naomi");
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the score() method
//     */
//    public void testScore() {
//        toplevel.section(1);
//        toplevel.insert(239721905, "Aileen", "Ford");
//        toplevel.score(91);
//        toplevel.searchid(394691224);
//        toplevel.score(100);
//        toplevel.searchid(394691224);
//        toplevel.score(-1);
//        toplevel.searchid(394691224);
//        toplevel.score(101);
//        toplevel.searchid(291935757);
//        toplevel.score(100);
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the remove() method
//     */
//    public void testRemoveID() {
//        toplevel.section(2);
//        toplevel.remove(256593948);
//        toplevel.remove(789123456);
//        toplevel.remove(394691224);
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the remove() method
//     */
//    public void testRemoveName() {
//        toplevel.section(1);
//        toplevel.remove("Mostafa", "Kamel");
//        toplevel.remove("Naomi", "Cote");
//        toplevel.search("Naomi");
//        toplevel.remove("Naomi", "Cote");
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the clearsection() method
//     */
//    public void testClearSection() {
//        toplevel.section(3);
//        toplevel.dumpsection();
//        toplevel.insert(349195701, "Gloria", "Chavez");
//        toplevel.score(100);
//        toplevel.clearsection();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the dumpsection() method
//     */
//    public void testDumpSection() {
//        toplevel.section(1);
//        toplevel.dumpsection();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the grade() method
//     */
//    public void testGrade() {
//        toplevel.section(3);
//        toplevel.dumpsection();
//        toplevel.insert(349195701, "Gloria", "Chavez");
//        toplevel.score(100);
//        toplevel.grade();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the stat() method
//     */
//    public void testStat() {
//        toplevel.section(1);
//        toplevel.insert(239721905, "Aileen", "Ford");
//        toplevel.score(91);
//        toplevel.searchid(394691224);
//        toplevel.score(100);
//        toplevel.grade();
//        toplevel.stat();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the list() method
//     */
//    public void testList() {
//        toplevel.section(1);
//        toplevel.insert(239721905, "Aileen", "Ford");
//        toplevel.score(91);
//        toplevel.searchid(394691224);
//        toplevel.score(100);
//        toplevel.section(3);
//        toplevel.insert(349195701, "Gloria", "Chavez");
//        toplevel.score(100);
//        toplevel.grade();
//        toplevel.section(1);
//        toplevel.grade();
//        toplevel.stat();
//        toplevel.merge();
//        toplevel.section(4);
//        toplevel.merge();
//        toplevel.insert(635043110, "Ishmael", "Carlson");
//        toplevel.searchid(394691224);
//        toplevel.search("Mostafa", "Kamel");
//        toplevel.search("Naomi");
//        toplevel.score(91);
//        toplevel.remove(256593948);
//        toplevel.remove("Mostafa", "Kamel");
//        toplevel.grade();
//        toplevel.stat();
//        toplevel.list("C*");
//        toplevel.section(2);
//        toplevel.list("C*");
//        toplevel.list("F");
//        toplevel.list("C+");
//        toplevel.list("C-");
//        toplevel.list("C");
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the findpair() method
//     */
//    public void testFindPair() {
//        toplevel.section(2);
//        toplevel.remove(256593948);
//        toplevel.findpair(10);
//        toplevel.dumpsection();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//
//    /**
//     * Test the merge() method
//     */
//    public void testMerge() {
//        toplevel.section(1);
//        toplevel.insert(239721905, "Aileen", "Ford");
//        toplevel.score(91);
//        toplevel.searchid(394691224);
//        toplevel.score(100);
//        toplevel.section(3);
//        toplevel.insert(349195701, "Gloria", "Chavez");
//        toplevel.score(100);
//        toplevel.grade();
//        toplevel.section(1);
//        toplevel.grade();
//        toplevel.stat();
//        toplevel.merge();
//        toplevel.section(4);
//        toplevel.merge();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//    
//    /**
//     * Test the clearcoursedata() method
//     */
//    public void clearCourseData() {
//        toplevel.section(1);
//        toplevel.clearcoursedata();
//        byte[] ba = { 0x00, 0x00, 0x00, 0x01 };
//        int i = bfh.byteArrayToInt(ba, 0);
//        assertEquals(i, 1);
//    }
//}
