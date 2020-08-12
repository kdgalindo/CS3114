package course;

import java.util.ArrayList;
import java.util.Iterator;

import bst.BST;
import student.Name;
import student.Student;

/**
 * Section Class
 * Section Info
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-10-20
 */
public class Section {
    private BST<Long, Integer> pbst; // BST w/ PID key & Index value
    private BST<Name, Integer> nbst; // BST w/ Name key & Index value
    private BST<Integer, Integer> sbst; // BST w/ Score key & Index value
    private int number; // Section Number
    private boolean active; // Active State
    
    /**
     * Section default constructor
     */
    Section() {
        pbst = new BST<Long, Integer>();
        nbst = new BST<Name, Integer>();
        sbst = new BST<Integer, Integer>();
        number = 1;
        active = true;
    }
    
    /**
     * Section number constructor
     * 
     * @param n number
     */
    Section(int n) {
        pbst = new BST<Long, Integer>();
        nbst = new BST<Name, Integer>();
        sbst = new BST<Integer, Integer>();
        number = n;
        active = true;
    }
    
    /**
     * Returns the Section Number
     * 
     * @return Section Number
     */
    public int number() {
        return number;
    }
    
    /**
     * Check if the Section is
     * Active; Section is Active 
     * if it is not a Merged Section
     * 
     * @return TRUE if the Section
     * is Active, FALSE otherwise;
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * Set or Reset Active state
     * of the Section;
     * Section is Active if it is
     * not a Merged Section
     * 
     * @param a Active
     * @return active
     */
    public boolean setActive(boolean a) {
        active = a;
        return active;
    }
    
    /**
     * Get the size of the Section
     * 
     * @return size
     */
    public int size() {
        return pbst.size();
    }
    
    /**
     * Check if the Section is empty
     * 
     * @return TRUE if the Section
     * is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (pbst.size() == 0);
    }
    
    /**
     * Inserts a Student into the Section
     * 
     * @param st Student
     * @param sc Student Score
     * @param i StudentRecord Index
     */
    public void insertStudent(Student st, int sc, int i) {
        pbst.insert(st.getPID(), i);
        nbst.insert(st.getName(), i);
        sbst.insert(sc, i);
    }
    
    /**
     * Searches for a StudentRecord Index
     * in the Section given a PID
     * 
     * @param p PID
     * @return StudentRecord Index
     */
    public Integer searchByPID(long p) {
        return pbst.find(p);
    }
    
    /**
     * Searches for all StudentRecord Indices
     * in the Section given a Name
     * 
     * @param n Name
     * @return StudentRecord Index
     */
    public ArrayList<Integer> searchByName(Name n) {
        return nbst.findall(n);
    }
    
    /**
     * Updates a Student's Score given a
     * Current Score, New Score, and a
     * StudentRecord Index
     * 
     * @param cs Current Score
     * @param ns New Score
     * @param i StudentRecord Index
     */
    public void updateStudentScore(int cs, int ns, int i) {
        sbst.remove(cs, i);
        sbst.insert(ns, i);
    }
    
    /**
     * Remove a Student's PID
     * 
     * @param p Student PID
     * @return StudentRecord Index
     */
    public Integer removeStudentPID(long p) {
        return pbst.remove(p);
    }
    
    /**
     * Remove a Student's Name
     * 
     * @param n Student Name
     * @return StudentRecord Index
     */
    public Integer removeStudentName(Name n) {
        return nbst.remove(n);
    }
    
    /**
     * Remove a Student's Name
     * 
     * @param n Student Name
     * @param i StudentRecord Index
     */
    public void removeStudentName(Name n, int i) {
        nbst.remove(n, i);
    }
    
    /**
     * Remove a Student's Score
     * 
     * @param s Student Score
     * @param i StudentRecord Index
     */
    public void removeStudentScore(int s, int i) {
        sbst.remove(s, i);
    }
    
    /**
     * Clear all Students from the
     * Section
     */
    public void clear() {
        pbst.clear();
        nbst.clear();
        sbst.clear();
        active = true;
    }
    
    /**
     * Searches for all StudentRecord Indices
     * in the Section between a Lower Score
     * and an Upper Score
     * 
     * @param s1 Lower Score
     * @param s2 Upper Score
     * @return StudentRecord Indices
     */
    public ArrayList<Integer> searchForScoresInRange(int s1, int s2) {
        return sbst.findrange(s1, s2);
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all Student PID keys
     * in the Section
     * 
     * @return PID Iterator
     */
    public Iterator<Integer> iterateByPID() {
        return pbst.iterator();
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all Student Name keys
     * in the Section
     * 
     * @return Name Iterator
     */
    public Iterator<Integer> iterateByName() {
        return nbst.iterator();
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all Student Score keys
     * in the Section
     * 
     * @return Score Iterator
     */
    public Iterator<Integer> iterateByScore() {
        return sbst.iterator();
    }
}
