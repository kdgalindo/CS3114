//package student;
//
//import static org.junit.Assert.*;
//import org.junit.Before;
//import org.junit.Test;
//
//import data.FullName;
//import data.Student;
//
///**
// * StudentTest Class
// * 
// * @author kyleg997 Kyle Galindo
// * @version 2020-08-13
// */
//public class StudentTest {
//    private Student s1;
//    private Student s2;
//    private Student s3;
//    
//    @Before
//    public void setUp() {
//        s1 = new Student(248476061, new FullName("Winter", "Hodge"));
//        s2 = new Student(256593948, new FullName("Sandra", "Lee", "Duncan"));
//        s3 = new Student(465830040, new FullName("Cain", "Buckner"));
//    }
//    
//    @Test
//    public void testGetPersonalID() {
//        long pid = s2.getPersonalID();
//        assertEquals(pid, 256593948);
//    }
//    
//    @Test
//    public void testCompareTo() {
//        int result = s3.compareTo(s2);
//        assertTrue(result > 0);
//        
//        result = s2.compareTo(s2);
//        assertEquals(result, 0);
//        
//        result = s1.compareTo(s2);
//        assertTrue(result < 0);
//    }
//    
//    @Test
//    public void testToString() {
//        String student = s2.toString();
//        assertEquals(student, "256593948, Sandra Duncan");
//    }
//}
