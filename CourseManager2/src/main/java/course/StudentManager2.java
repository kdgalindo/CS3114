package course;

import java.util.Iterator;

import bst.BST;
import student.Student;

/**
 * StudentManager Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class StudentManager2 {
    private BST<Long, Student> pidStudentDB;
    
    public StudentManager2() {
        pidStudentDB = new BST<Long, Student>();
    }
    
    /**
     * Get the size of the StudentManager
     * 
     * @return size
     */
    public int size() {
        return pidStudentDB.size();
    }
    
    /**
     * Check if the StudentManager is empty
     * 
     * @return TRUE if the StudentManager is
     * empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (pidStudentDB.size() == 0);
    }
    
    /**
     * Insert into a Student into the
     * StudentManager
     * 
     * @param p Student PID
     * @param s Student
     */
    public void insert(long p, Student s) {
        pidStudentDB.insert(p, s);
    }
    
    public void insert(Student student) {
    	pidStudentDB.insert(student.getPersonalID(), student);
    }
    
    /**
     * Search for a Student in the
     * StudentManager given a PID
     * 
     * @param p PID
     * @return Student
     */
    public Student search(long p) {
        return pidStudentDB.find(p);
    }
    
    public Student findStudent(long personalID) {
    	return pidStudentDB.find(personalID);
    }
    
    /**
     * Clear the StudentManager
     */
    public void clear() {
        pidStudentDB.clear();
    }
    
    /**
     * Returns an iterator for an inorder
     * traversal of all PID keys in the
     * StudentManager
     * 
     * @return PID Iterator
     */
    public Iterator<Student> iterator() {
        return pidStudentDB.iterator();
    }
}
