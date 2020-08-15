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
 * @version 2020-08-14
 */
public class Section2 {
    private BST<Long, Integer> studentPIDDB; // Index value
    private BST<FullName, Integer> studentFNDB; // Index value
    private BST<Integer, Integer> studentPGDB; // Index value
    private int number;
    private boolean active; // Active State
    
    Section2(int sectionNumber) {
        studentPIDDB = new BST<Long, Integer>();
        studentFNDB = new BST<FullName, Integer>();
        studentPGDB = new BST<Integer, Integer>();
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
        return studentPIDDB.size();
    }
    
    /**
     * Check if the Section is empty
     * 
     * @return TRUE if the Section
     * is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (studentPIDDB.size() == 0);
    }
    
    /**
     * Inserts a Student into the Section
     * 
     * @param st Student
     * @param sc Student Score
     * @param i StudentRecord Index
     */
    public void insertStudent(Student st, int sc, int i) {
        studentPIDDB.insert(st.getPersonalID(), i);
        studentFNDB.insert(st.getFullName(), i);
        studentPGDB.insert(sc, i);
    }
    
    public void insert(Student student, int index) {
        studentPIDDB.insert(student.getPersonalID(), index);
        studentFNDB.insert(student.getFullName(), index);
        studentPGDB.insert(student.getPercentageGrade(), index);
    }
    
    public Integer findStudent(long personalID) {
        return studentPIDDB.find(personalID);
    }
    
    /**
     * Searches for all StudentRecord Indices
     * in the Section given a Name
     * 
     * @param n Name
     * @return StudentRecord Index
     */
    public ArrayList<Integer> searchByName(FullName fullName) {
        return studentFNDB.findall(fullName);
    }
    
    public int[] findStudents(FullName fullName) {
        return toIntArray(studentFNDB.findall(fullName));
    }
    
    private static int[] toIntArray(ArrayList<Integer> oldIndices) {
        int[] indices = new int[oldIndices.size()];
        for (int i = 0; i < indices.length; i++) {
        	indices[i] = oldIndices.get(i).intValue();
        }
        return indices;
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
    public void updateStudentScore(int cs, int ns, int index) {
        studentPGDB.remove(cs, index);
        studentPGDB.insert(ns, index);
    }
    
    /**
     * Remove a Student's PID
     * 
     * @param p Student PID
     * @return StudentRecord Index
     */
    public Integer removeStudentPID(long p) {
        return studentPIDDB.remove(p);
    }
    
    /**
     * Remove a Student's Name
     * 
     * @param n Student Name
     * @return StudentRecord Index
     */
    public Integer removeStudentName(FullName n) {
        return studentFNDB.remove(n);
    }
    
    /**
     * Remove a Student's Name
     * 
     * @param n Student Name
     * @param i StudentRecord Index
     */
    public void removeStudentName(FullName n, int i) {
        studentFNDB.remove(n, i);
    }
    
    /**
     * Remove a Student's Score
     * 
     * @param s Student Score
     * @param i StudentRecord Index
     */
    public void removeStudentScore(int s, int i) {
        studentPGDB.remove(s, i);
    }
    
    /**
     * Clear all Students from the
     * Section
     */
    public void clear() {
        studentPIDDB.clear();
        studentFNDB.clear();
        studentPGDB.clear();
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
        return studentPGDB.findrange(s1, s2);
    }
    
    public Iterator<Integer> studentPIDIndexIterator() {
        return studentPIDDB.iterator();
    }
    
    public Iterator<Integer> studentFNIndexIterator() {
        return studentFNDB.iterator();
    }
    
    public Iterator<Integer> studentPGIndexIterator() {
        return studentPGDB.iterator();
    }
}
