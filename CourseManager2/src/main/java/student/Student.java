package student;

/**
 * Student Class
 * Student Info
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-10-20
 */
public class Student implements Comparable<Student> {
    private long pid;
    private Name name;

    /**
     * Student id, Name constructor
     * 
     * @param p Student id
     * @param n Student Name
     */
    public Student(long p, Name n) {
        pid = p;
        name = n;
    }

    /**
     * Get ID
     * 
     * @return pid
     */
    public long getPID() {
        return pid;
    }

    /**
     * Get Name
     * 
     * @return name
     */
    public Name getName() {
        return name;
    }
    
    /**
     * Get First Name
     * 
     * @return first
     */
    public String getFirst() {
        return name.getFirst();
    }
    
    /**
     * Get Middle Name
     * 
     * @return middle
     */
    public String getMiddle() {
        return name.getMiddle();
    }
    
    /**
     * Get Last Name
     * 
     * @return last
     */
    public String getLast() {
        return name.getLast();
    }
    
    /**
     * compareTo override for Student Object
     * 
     * @param s Student
     * @return 0 if equal, less than 0 if Student is
     *         lexicographically less than n, greater
     *         than 0 if Student is lexicographically
     *         greater than n
     */
    @Override
    public int compareTo(Student s) {
        return Long.compare(pid, s.getPID());
    }

    /**
     * toString override for Student Object
     * 
     * @return Student string
     */
    @Override
    public String toString() {
        return String.format("%09d, %s", pid, name);
    }
}
