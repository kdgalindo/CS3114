import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import project1.Name;

/**
 * NameTest Class
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-18
 */
public class NameTest {
    private Name n1;

    @Before
    public void setUp() {
        n1 = new Name("Kyle", "Galindo");
    }

    @Test
    public void testGetFirst() {
        String firstName = n1.getFirstName();
        assertEquals(firstName, "Kyle");
    }

    @Test
    public void testGetLast() {
        String lastName = n1.getLastName();
        assertEquals(lastName, "Galindo");
    }

    @Test
    public void testCompareTo() {
    	Name n2 = new Name("Angel", "Isiadinso");
        int result = n1.compareTo(n2);
        assertTrue(result < 0);
        
        result = n1.compareTo(n1);
        assertEquals(result, 0);
        
        Name n3 = new Name("Young", "Cao");
        result = n1.compareTo(n3);
        assertTrue(result > 0);
    }

    @Test
    public void testToString() {
        String fullName = n1.toString();
        assertEquals(fullName, "Kyle Galindo");
    }
}
