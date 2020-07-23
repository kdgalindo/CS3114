import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import project1.Parser;

/**
 * ParserTest Class
 *
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-19
 */
public class ParserTest {
    private Parser p;

    @Before
    public void setUp() {
        p = new Parser();
    }

    @Test
    public void testSectionHelp() {
        assertFalse(p.sectionhelp(0));
        assertTrue(p.sectionhelp(1));
        assertFalse(p.sectionhelp(4));
    }

    @Test
    public void testInsertHelp() {
        assertTrue(p.inserthelp("Kyle", "Galindo"));
        assertTrue(p.inserthelp("Angel", "Isiadinso"));
        assertFalse(p.inserthelp("kyle", "galindo"));
    }

    @Test
    public void testSimpleSearchHelp() {
        p.inserthelp("Kyle", "Galindo");
        p.inserthelp("Angel", "Isiadinso");
        assertTrue(p.simplesearchhelp("kyle", "galindo"));
        assertFalse(p.simplesearchhelp("Young", "Cao"));
    }

    @Test
    public void testScoreHelp() {
        p.inserthelp("Kyle", "Galindo");
        assertTrue(p.scorehelp(95));
        p.inserthelp("Angel", "Isiadinso");
        assertFalse(p.scorehelp(-95));
        assertFalse(p.scorehelp(95));
        p.inserthelp("Young", "Cao");
        assertFalse(p.scorehelp(195));
    }

    @Test
    public void testRemoveHelp() {
        p.inserthelp("Kyle", "Galindo");
        p.inserthelp("Angel", "Isiadinso");
        p.inserthelp("Young", "Cao");
        assertTrue(p.removehelp("kyle", "galindo"));
        assertFalse(p.removehelp("John", "Smith"));
    }

    @Test
    public void testRemoveSectionHelp() {
        assertFalse(p.removesectionhelp(0));
        assertTrue(p.removesectionhelp(1));
        assertFalse(p.removesectionhelp(4));
    }

    @Test
    public void testFuzzySearchHelp() {
        p.inserthelp("Kyle", "Galindo");
        p.inserthelp("Noele", "Galindo");
        p.inserthelp("Angel", "Isiadinso");
        p.inserthelp("Young", "Cao");
        assertTrue(p.fuzzysearchhelp("galindo"));
        assertFalse(p.fuzzysearchhelp("galin"));
        assertTrue(p.fuzzysearchhelp("kyle"));
    }
}
