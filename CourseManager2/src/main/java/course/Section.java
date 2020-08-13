package course;

import java.util.ArrayList;
import java.util.Iterator;

import bst.BST;
import student.FullName;
import student.Student;

/**
 * Section Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-13
 */
public class Section {
    private BST<Long, Integer> personalIDDB; // Index value
    private BST<FullName, Integer> fullNameDB; // Index value
    private BST<Integer, Integer> scorePercentageDB; // Index value
    private int number;
    private boolean active; // Active State
    
    Section(int sectionNumber) {
        personalIDDB = new BST<Long, Integer>();
        fullNameDB = new BST<FullName, Integer>();
        scorePercentageDB = new BST<Integer, Integer>();
        number = sectionNumber;
        active = true;
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    /**
     * Get the size of the Section
     * 
     * @return size
     */
    public int size() {
        return personalIDDB.size();
    }
    
    /**
     * Check if the Section is empty
     * 
     * @return TRUE if the Section
     * is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (personalIDDB.size() == 0);
    }
    
    /**
     * Inserts a Student into the Section
     * 
     * @param st Student
     * @param sc Student Score
     * @param i StudentRecord Index
     */
    public void insertStudent(Student st, int sc, int i) {
        personalIDDB.insert(st.getPersonalID(), i);
        fullNameDB.insert(st.getFullName(), i);
        scorePercentageDB.insert(sc, i);
    }
    
    public Integer findStudent(long personalID) {
        return personalIDDB.find(personalID);
    }
    
    /**
     * Searches for all StudentRecord Indices
     * in the Section given a Name
     * 
     * @param n Name
     * @return StudentRecord Index
     */
    public ArrayList<Integer> searchByName(FullName n) {
        return fullNameDB.findall(n);
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
        scorePercentageDB.remove(cs, i);
        scorePercentageDB.insert(ns, i);
    }
    
    /**
     * Remove a Student's PID
     * 
     * @param p Student PID
     * @return StudentRecord Index
     */
    public Integer removeStudentPID(long p) {
        return personalIDDB.remove(p);
    }
    
    /**
     * Remove a Student's Name
     * 
     * @param n Student Name
     * @return StudentRecord Index
     */
    public Integer removeStudentName(FullName n) {
        return fullNameDB.remove(n);
    }
    
    /**
     * Remove a Student's Name
     * 
     * @param n Student Name
     * @param i StudentRecord Index
     */
    public void removeStudentName(FullName n, int i) {
        fullNameDB.remove(n, i);
    }
    
    /**
     * Remove a Student's Score
     * 
     * @param s Student Score
     * @param i StudentRecord Index
     */
    public void removeStudentScore(int s, int i) {
        scorePercentageDB.remove(s, i);
    }
    
    /**
     * Clear all Students from the
     * Section
     */
    public void clear() {
        personalIDDB.clear();
        fullNameDB.clear();
        scorePercentageDB.clear();
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
        return scorePercentageDB.findrange(s1, s2);
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all Student PID keys
     * in the Section
     * 
     * @return PID Iterator
     */
    public Iterator<Integer> iterateByPID() {
        return personalIDDB.iterator();
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all Student Name keys
     * in the Section
     * 
     * @return Name Iterator
     */
    public Iterator<Integer> iterateByName() {
        return fullNameDB.iterator();
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all Student Score keys
     * in the Section
     * 
     * @return Score Iterator
     */
    public Iterator<Integer> iterateByScore() {
        return scorePercentageDB.iterator();
    }
}
