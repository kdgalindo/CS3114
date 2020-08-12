package student;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import student.FullName;
import student.Student;

/**
 * StudentTest Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-05
 */
public class StudentTest {
    private Student s1;
    private Student s2;

    @Before
    public void setUp() {
        s1 = new Student(new FullName("Kyle", "Galindo"), "010001");
        s2 = new Student(new FullName("Angel", "Isiadinso"), "010002", 100);
    }

    @Test
    public void testGetName() {
        String fullName = s1.getFullName().toString();
        assertEquals(fullName, "Kyle Galindo");
    }

    @Test
    public void testGetID() {
        String id = s1.getID();
        assertEquals(id, "010001");
    }

    @Test
    public void testGetScore() {
        int score = s1.getScore();
        assertEquals(score, 0);
        score = s2.getScore();
        assertEquals(score, 100);
    }

    @Test
    public void testSetScore() {
        s1.setScore(100);
        int score = s1.getScore();
        assertEquals(score, 100);
    }

    @Test
    public void testToString() {
        String student = s2.toString();
        assertEquals(student, "010002, Angel Isiadinso, score = 100");
    }
}
