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
    private Name n2;

    @Before
    public void setUp() {
        n1 = new Name();
        n2 = new Name("Kyle", "Galindo");
    }

    @Test
    public void testGetFirst() {
        String f = n2.getFirst();
        assertEquals(f, "Kyle");
    }

    @Test
    public void testGetLast() {
        String l = n2.getLast();
        assertEquals(l, "Galindo");
    }

    @Test
    public void testSetFirst() {
        String f = n1.setFirst("Angel");
        assertEquals(f, "Angel");
    }

    @Test
    public void testSetLast() {
        String l = n1.setLast("Isiadinso");
        assertEquals(l, "Isiadinso");
    }

    @Test
    public void testCompareTo() {
        n1.setFirst("Angel");
        n1.setLast("Isiadinso");
        int i = n1.compareTo(n2);
        assertTrue(i > 0);
        n1.setFirst("Kyle");
        n1.setLast("Galindo");
        i = n1.compareTo(n2);
        assertEquals(i, 0);
        n1.setFirst("Young");
        n1.setLast("Cao");
        i = n1.compareTo(n2);
        assertTrue(i < 0);
    }

    @Test
    public void testToString() {
        String s = n2.toString();
        assertEquals(s, "Kyle Galindo");
    }
}
