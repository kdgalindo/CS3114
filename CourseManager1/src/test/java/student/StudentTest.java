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
 * @version 2020-08-12
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
    public void testGetIDNumber() {
        String id = s1.getIDNumber();
        assertEquals(id, "010001");
    }

    @Test
    public void testGetScorePercentage() {
        int score = s1.getScorePercentage();
        assertEquals(score, 0);
        score = s2.getScorePercentage();
        assertEquals(score, 100);
    }

    @Test
    public void testSetScorePercentage() {
        s1.setScorePercentage(100);
        int score = s1.getScorePercentage();
        assertEquals(score, 100);
    }

    @Test
    public void testToString() {
        String student = s2.toString();
        assertEquals(student, "010002, Angel Isiadinso, score = 100");
    }
}
