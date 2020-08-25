package course;

import java.util.ArrayList;
import java.util.Iterator;

import bst.BST;
import student.FullName;

/**
 * Section Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class Section2 {
    private BST<Long, Integer> pidIndexDB; // Index value
    private BST<FullName, Integer> fnIndexDB; // Index value
    private BST<Integer, Integer> pgIndexDB; // Index value
    private int number;
    private boolean isActive; // Active State
    
    Section2(int sectionNumber) {
        pidIndexDB = new BST<Long, Integer>();
        fnIndexDB = new BST<FullName, Integer>();
        pgIndexDB = new BST<Integer, Integer>();
        number = sectionNumber;
        isActive = true;
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    /**
     * Get the size of the Section
     * 
     * @return size
     */
    public int size() {
        return pidIndexDB.size();
    }
    
    /**
     * Check if the Section is empty
     * 
     * @return TRUE if the Section
     * is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (pidIndexDB.size() == 0);
    }
    
    public void insert(long personalID, int index) {
    	pidIndexDB.insert(personalID, index);
    }
    
    public void insert(FullName fullName, int index) {
    	fnIndexDB.insert(fullName, index);
    }
    
    public void insert(int percentageGrade, int index) {
    	pgIndexDB.insert(percentageGrade, index);
    }
    
    public Integer findIndex(long personalID) {
        return pidIndexDB.find(personalID);
    }
    
    public int[] findIndices(FullName fullName) {
        return toIntArray(fnIndexDB.findall(fullName));
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
        pgIndexDB.remove(cs, index);
        pgIndexDB.insert(ns, index);
    }
    
    public Integer remove(long personalID) {
        return pidIndexDB.remove(personalID);
    }
    
    public Integer remove(FullName fullName) {
        return fnIndexDB.remove(fullName);
    }
    
    public void remove(FullName fullName, int index) {
        fnIndexDB.remove(fullName, index);
    }
    
    public void remove(int percentage, int index) {
        pgIndexDB.remove(percentage, index);
    }
    
    /**
     * Clear all Students from the
     * Section
     */
    public void clear() {
        pidIndexDB.clear();
        fnIndexDB.clear();
        pgIndexDB.clear();
        isActive = true;
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
        return pgIndexDB.findrange(s1, s2);
    }
    
    public Iterator<Integer> studentPIDIndexIterator() {
        return pidIndexDB.iterator();
    }
    
    public Iterator<Integer> studentFNIndexIterator() {
        return fnIndexDB.iterator();
    }
    
    public Iterator<Integer> studentPGIndexIterator() {
        return pgIndexDB.iterator();
    }
}
