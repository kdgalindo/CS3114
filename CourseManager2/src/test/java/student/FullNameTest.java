package student;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import data.FullName;

/**
 * FullNameTest Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-13
 */
public class FullNameTest {
    private FullName fn1;
    private FullName fn2;
    private FullName fn3;

    @Before
    public void setUp() {
        fn1 = new FullName("Young", "Cao");
        fn2 = new FullName("Kyle", "David", "Galindo");
        fn3 = new FullName("Angel", "Tehilla", "Isiadinso");
    }

    @Test
    public void testGetFirst() {
        String firstName = fn2.getFirstName();
        assertEquals(firstName, "Kyle");
    }
    
    @Test
    public void testGetMiddle() {
        String middleName = fn2.getMiddleName();
        assertEquals(middleName, "David");
    }

    @Test
    public void testGetLast() {
        String lastName = fn2.getLastName();
        assertEquals(lastName, "Galindo");
    }

    @Test
    public void testCompareTo() {
        int result = fn3.compareTo(fn2);
        assertTrue(result > 0);
        
        result = fn2.compareTo(fn2);
        assertEquals(result, 0);
        
        result = fn1.compareTo(fn2);
        assertTrue(result < 0);
    }

    @Test
    public void testToString() {
        String firstLastName = fn2.toString();
        assertEquals(firstLastName, "Kyle Galindo");
    }
}
