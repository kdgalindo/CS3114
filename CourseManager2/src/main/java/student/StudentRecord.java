package student;

/**
 * StudentRecord Class
 * Student Record Info
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-10-20
 */
public class StudentRecord {
    private boolean active; // Tombstone
    private Student student; // Student
    private int score; // Student score
    private String grade; // Student grade

    /**
     * StudentRecord Student constructor
     * 
     * @param st Student
     */
    public StudentRecord(Student st) {
        active = true;
        student = st;
        score = 0;
        grade = "F ";
    }

    /**
     * StudentRecord Student, score, grade constructor
     * 
     * @param st Student
     * @param sc Student score
     * @param gr Student grade
     */
    public StudentRecord(Student st, int sc, String gr) {
        active = true;
        student = st;
        score = sc;
        grade = gr;
    }
    
    /**
     * Get Active
     * 
     * @return active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Get Student
     * 
     * @return student
     */
    public Student getStudent() {
        return student;
    }
    
    /**
     * Get PID
     * 
     * @return pid
     */
    public long getPID() {
        return student.getPID();
    }
    
    /**
     * Get Name
     * 
     * @return name
     */
    public FullName getName() {
        return student.getName();
    }
    
    /**
     * Get First Name
     * 
     * @return first
     */
    public String getFirst() {
        return student.getFirst();
    }
    
    /**
     * Get Last Name
     * 
     * @return last
     */
    public String getLast() {
        return student.getLast();
    }

    /**
     * Get Student's Score
     * 
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Get Student's Grade
     * 
     * @return grade
     */
    public String getGrade() {
        return grade;
    }
    
    /**
     * Set Active
     * 
     * @param a Active
     * @return active
     */
    public boolean setActive(boolean a) {
        active = a;
        return active;
    }

    /**
     * Set Student's score
     * 
     * @param sc Student score
     * @return score
     */
    public int setScore(int sc) {
        score = sc;
        return score;
    }
    
    /**
     * Set Student's grade
     * 
     * @param g Student grade
     * @return grade
     */
    public String setGrade(String g) {
        grade = g;
        return grade;
    }

    /**
     * toString override for Record Object
     * 
     * @return Record string
     */
    @Override
    public String toString() {
        return String.format(student + ", score = " + score);
    }
}
