import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import project1.FullName;

/**
 * FullNameTest Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-04
 */
public class FullNameTest {
    private FullName fn1;

    @Before
    public void setUp() {
    	fn1 = new FullName("Kyle", "Galindo");
    }

    @Test
    public void testGetFirst() {
        String firstName = fn1.getFirstName();
        assertEquals(firstName, "Kyle");
    }

    @Test
    public void testGetLast() {
        String lastName = fn1.getLastName();
        assertEquals(lastName, "Galindo");
    }

    @Test
    public void testCompareTo() {
    	FullName fn2 = new FullName("Angel", "Isiadinso");
        int result = fn1.compareTo(fn2);
        assertTrue(result < 0);
        
        result = fn1.compareTo(fn1);
        assertEquals(result, 0);
        
        FullName fn3 = new FullName("Young", "Cao");
        result = fn1.compareTo(fn3);
        assertTrue(result > 0);
    }

    @Test
    public void testToString() {
        String fullName = fn1.toString();
        assertEquals(fullName, "Kyle Galindo");
    }
}
