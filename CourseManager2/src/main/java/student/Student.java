package student;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-13
 */
public class Student implements Comparable<Student> {
    private long pid;
    private FullName name;

    /**
     * Student id, Name constructor
     * 
     * @param p Student id
     * @param n Student Name
     */
    public Student(long p, FullName n) {
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
    public FullName getName() {
        return name;
    }
    
    /**
     * Get First Name
     * 
     * @return first
     */
    public String getFirst() {
        return name.getFirstName();
    }
    
    /**
     * Get Middle Name
     * 
     * @return middle
     */
    public String getMiddle() {
        return name.getMiddleName();
    }
    
    /**
     * Get Last Name
     * 
     * @return last
     */
    public String getLast() {
        return name.getLastName();
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
