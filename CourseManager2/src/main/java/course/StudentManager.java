package course;

import java.util.Iterator;

import bst.BST;
import student.Student;

/**
 * StudentManager Class
 * StudentManager Info
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-10-20
 */
public class StudentManager {
    private BST<Long, Student> sbst; // BST w/ PID key & Student value
    
    /**
     * StudentManager default constructor
     */
    public StudentManager() {
        sbst = new BST<Long, Student>();
    }
    
    /**
     * Get the size of the StudentManager
     * 
     * @return size
     */
    public int size() {
        return sbst.size();
    }
    
    /**
     * Check if the StudentManager is empty
     * 
     * @return TRUE if the StudentManager is
     * empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (sbst.size() == 0);
    }
    
    /**
     * Insert into a Student into the
     * StudentManager
     * 
     * @param p Student PID
     * @param s Student
     */
    public void insert(long p, Student s) {
        sbst.insert(p, s);
    }
    
    /**
     * Search for a Student in the
     * StudentManager given a PID
     * 
     * @param p PID
     * @return Student
     */
    public Student search(long p) {
        return sbst.find(p);
    }
    
    /**
     * Clear the StudentManager
     */
    public void clear() {
        sbst.clear();
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all PID keys in the
     * StudentManager
     * 
     * @return PID Iterator
     */
    public Iterator<Student> iterator() {
        return sbst.iterator();
    }
}
