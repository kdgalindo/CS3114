import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import project1.Student;
import project1.Section;

/** 
 * SectionTest Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-25
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
        Student r1 = s1.insert("Kyle", "Galindo");
        assertEquals(r1.getName().toString(), "Kyle Galindo");
        Student r2 = s1.insert("Angel", "Isiadinso");
        assertEquals(r2.getID(), "010002");
        Student r3 = s1.insert("Young", "Cao");
        assertEquals(r3.getScore(), 0);
    }

    @Test
    public void testSimpleSearch() {
        s1.insert("Kyle", "Galindo");
        s1.insert("Angel", "Isiadinso");
        s1.insert("Young", "Cao");
        Student r1 = s1.search("Kyle", "Galindo");
        assertEquals(r1.getName().toString(), "Kyle Galindo");
        Student r2 = s1.search("John", "Smith");
        assertNull(r2);
    }

    @Test
    public void testScore() {
        Student r1 = s1.insert("Kyle", "Galindo");
        s1.score(75, r1);
        assertEquals(r1.getScore(), 75);
        Student r2 = s1.insert("Angel", "Isiadinso");
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
        Student r1 = s1.remove("John", "Smith");
        assertEquals(r1.getName().toString(), "John Smith");
        Student r2 = s1.remove("X", "Y");
        assertNull(r2);
    }
    
    @Test
    public void testRemove2() {
        s1.insert("John", "Obi");
        s1.insert("Susan", "Obi");
        s1.insert("Susan", "Ibrahim");
        s1.insert("Nancy", "Henry");
        s1.insert("Virtual", "Student");
        Student r1 = s1.remove("John", "Obi");
        assertEquals(r1.getName().toString(), "John Obi");
        Student r2 = s1.remove("Virtual", "Student");
        assertEquals(r2.getName().toString(), "Virtual Student");
        Student r3 = s1.remove("X", "Y");
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
        Student r1 = s1.insert("X", "Y");
        assertEquals(r1.getID(), "010001");
    }

    @Test
    public void testFuzzySearch() {
        s1.insert("Kyle", "Jackson");
        s1.insert("Jackson", "Isiadinso");
        s1.insert("Young", "Jackson");
        s1.insert("John", "Smith");
        ArrayList<Student> l = s1.search("Jackson");
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
        Student r1 = s1.insert("Kyle", "Galindo");
        s1.score(90, r1);
        Student r2 = s1.insert("Angel", "Isiadinso");
        s1.score(85, r2);
        Student r3 = s1.insert("Young", "Cao");
        s1.score(80, r3);
        Student r4 = s1.insert("John", "Smith");
        s1.score(75, r4);
        Student r5 = s1.insert("A", "B");
        s1.score(70, r5);
        Student r6 = s1.insert("C", "D");
        s1.score(65, r6);
        Student r7 = s1.insert("E", "F");
        s1.score(60, r7);
        Student r8 = s1.insert("G", "H");
        s1.score(58, r8);
        Student r9 = s1.insert("I", "J");
        s1.score(55, r9);
        Student r10 = s1.insert("K", "L");
        s1.score(53, r10);
        Student r11 = s1.insert("M", "N");
        s1.score(50, r11);
        Student r12 = s1.insert("X", "Y");
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
        Student r1 = s1.insert("Kyle", "Galindo");
        s1.score(90, r1);
        Student r2 = s1.insert("Angel", "Isiadinso");
        s1.score(85, r2);
        Student r3 = s1.insert("Young", "Cao");
        s1.score(80, r3);
        Student r4 = s1.insert("John", "Smith");
        s1.score(75, r4);
        Student r5 = s1.insert("A", "B");
        s1.score(70, r5);
        Student r6 = s1.insert("C", "D");
        s1.score(65, r6);
        Student r7 = s1.insert("E", "F");
        s1.score(60, r7);
        Student r8 = s1.insert("G", "H");
        s1.score(58, r8);
        Student r9 = s1.insert("I", "J");
        s1.score(55, r9);
        Student r10 = s1.insert("K", "L");
        s1.score(53, r10);
        Student r11 = s1.insert("M", "N");
        s1.score(50, r11);
        Student r12 = s1.insert("X", "Y");
        s1.score(25, r12);
        int pairs = s1.findPair(3);
        assertEquals(pairs, 4);
        pairs = s1.findPair(5);
        assertEquals(pairs, 13);
        pairs = s2.findPair(0);
        assertEquals(pairs, 0);
    }
}
