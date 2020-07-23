import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import project1.Record;
import project1.Section;

/** 
 * SectionTest Class
 *
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-19
 */
public class SectionTest {
    private Section s1;
    private Section s2;

    @Before
    public void setUp() {
        s1 = new Section();
        s2 = new Section(2);
    }

    @Test
    public void testGetNumber() {
        int i = s1.getNumber();
        assertEquals(i, 1);
        i = s2.getNumber();
        assertEquals(i, 2);
    }

    @Test
    public void testInsert() {
        Record r1 = s1.insert("Kyle", "Galindo");
        assertEquals(r1.getName().toString(), "Kyle Galindo");
        Record r2 = s1.insert("Angel", "Isiadinso");
        assertEquals(r2.getID(), "010002");
        Record r3 = s1.insert("Young", "Cao");
        assertEquals(r3.getScore(), 0);
    }

    @Test
    public void testSimpleSearch() {
        s1.insert("Kyle", "Galindo");
        s1.insert("Angel", "Isiadinso");
        s1.insert("Young", "Cao");
        Record r1 = s1.search("Kyle", "Galindo");
        assertEquals(r1.getName().toString(), "Kyle Galindo");
        Record r2 = s1.search("John", "Smith");
        assertNull(r2);
    }

    @Test
    public void testScore() {
        Record r1 = s1.insert("Kyle", "Galindo");
        s1.score(75, r1);
        assertEquals(r1.getScore(), 75);
        Record r2 = s1.insert("Angel", "Isiadinso");
        s1.score(100, r2);
        assertEquals(r2.getScore(), 100);
    }

    @Test
    public void testRemove1() {
        s1.insert("Kyle", "Galindo");
        s1.insert("Angel", "Isiadinso");
        s1.insert("Young", "Cao");
        s1.insert("John", "Smith");
        s1.insert("A", "B");
        Record r1 = s1.remove("John", "Smith");
        assertEquals(r1.getName().toString(), "John Smith");
        Record r2 = s1.remove("X", "Y");
        assertNull(r2);
    }
    
    @Test
    public void testRemove2() {
        s1.insert("John", "Obi");
        s1.insert("Susan", "Obi");
        s1.insert("Susan", "Ibrahim");
        s1.insert("Nancy", "Henry");
        s1.insert("Virtual", "Student");
        Record r1 = s1.remove("John", "Obi");
        assertEquals(r1.getName().toString(), "John Obi");
        Record r2 = s1.remove("Virtual", "Student");
        assertEquals(r2.getName().toString(), "Virtual Student");
        Record r3 = s1.remove("X", "Y");
        assertNull(r3);
    }

    @Test
    public void testRemoveSection() {
        s1.insert("Kyle", "Galindo");
        s1.insert("Angel", "Isiadinso");
        s1.insert("Young", "Cao");
        s1.insert("John", "Smith");
        s1.insert("A", "B");
        s1.removeSection();
        Record r1 = s1.insert("X", "Y");
        assertEquals(r1.getID(), "010001");
    }

    @Test
    public void testFuzzySearch() {
        s1.insert("Kyle", "Jackson");
        s1.insert("Jackson", "Isiadinso");
        s1.insert("Young", "Jackson");
        s1.insert("John", "Smith");
        ArrayList<Record> l = s1.search("Jackson");
        assertEquals(l.size(), 3);
        l = s1.search("X");
        assertEquals(l.size(), 0);
        l = s2.search("X");
        assertEquals(l.size(), 0);
    }

    @Test
    public void testDumpSection() {
        s1.insert("Kyle", "Galindo");
        s1.insert("Angel", "Isiadinso");
        s1.insert("Young", "Cao");
        s1.insert("John", "Smith");
        s1.insert("A", "B");
        s1.insert("X", "Y");
        int size = s1.dumpSection();
        assertEquals(size, 6);
    }

    @Test
    public void testGrade() {
        Record r1 = s1.insert("Kyle", "Galindo");
        s1.score(90, r1);
        Record r2 = s1.insert("Angel", "Isiadinso");
        s1.score(85, r2);
        Record r3 = s1.insert("Young", "Cao");
        s1.score(80, r3);
        Record r4 = s1.insert("John", "Smith");
        s1.score(75, r4);
        Record r5 = s1.insert("A", "B");
        s1.score(70, r5);
        Record r6 = s1.insert("C", "D");
        s1.score(65, r6);
        Record r7 = s1.insert("E", "F");
        s1.score(60, r7);
        Record r8 = s1.insert("G", "H");
        s1.score(58, r8);
        Record r9 = s1.insert("I", "J");
        s1.score(55, r9);
        Record r10 = s1.insert("K", "L");
        s1.score(53, r10);
        Record r11 = s1.insert("M", "N");
        s1.score(50, r11);
        Record r12 = s1.insert("X", "Y");
        s1.score(25, r12);
        s1.grade();
        int size = s1.dumpSection();
        assertEquals(size, 12);
        s2.grade();
        size = s2.dumpSection();
        assertEquals(size, 0);
    }

    @Test
    public void testFindPair() {
        Record r1 = s1.insert("Kyle", "Galindo");
        s1.score(90, r1);
        Record r2 = s1.insert("Angel", "Isiadinso");
        s1.score(85, r2);
        Record r3 = s1.insert("Young", "Cao");
        s1.score(80, r3);
        Record r4 = s1.insert("John", "Smith");
        s1.score(75, r4);
        Record r5 = s1.insert("A", "B");
        s1.score(70, r5);
        Record r6 = s1.insert("C", "D");
        s1.score(65, r6);
        Record r7 = s1.insert("E", "F");
        s1.score(60, r7);
        Record r8 = s1.insert("G", "H");
        s1.score(58, r8);
        Record r9 = s1.insert("I", "J");
        s1.score(55, r9);
        Record r10 = s1.insert("K", "L");
        s1.score(53, r10);
        Record r11 = s1.insert("M", "N");
        s1.score(50, r11);
        Record r12 = s1.insert("X", "Y");
        s1.score(25, r12);
        int pairs = s1.findPair(3);
        assertEquals(pairs, 4);
        pairs = s1.findPair(5);
        assertEquals(pairs, 13);
        pairs = s2.findPair(0);
        assertEquals(pairs, 0);
    }
}
