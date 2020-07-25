import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import project1.Name;
import project1.Student;

/**
 * StudentTest Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-25
 */
public class StudentTest {
    private Student r1;
    private Student r2;
    private Student r3;

    @Before
    public void setUp() {
        r1 = new Student();
        r2 = new Student(new Name("Kyle", "Galindo"), "010002");
        r3 = new Student(new Name("Angel", "Isiadinso"), "010003", 95);
    }

    @Test
    public void testGetName() {
        String s = r2.getName().toString();
        assertEquals(s, "Kyle Galindo");
    }

    @Test
    public void testGetID() {
        String s = r2.getID();
        assertEquals(s, "010002");
    }

    @Test
    public void testGetScore() {
        int i = r3.getScore();
        assertEquals(i, 95);
    }

    @Test
    public void testSetName() {
        String s = r1.setName(new Name("Young", "Cao")).toString();
        assertEquals(s, "Young Cao");
    }

    @Test
    public void testSetID() {
        String s = r1.setID("010001");
        assertEquals(s, "010001");
    }

    @Test
    public void testSetScore() {
        int i = r1.setScore(100);
        assertEquals(i, 100);
    }

    @Test
    public void testToString() {
        String s = r3.toString();
        assertEquals(s, "010003, Angel Isiadinso, score = 95");
    }
}
