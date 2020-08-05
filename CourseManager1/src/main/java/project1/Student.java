package project1;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-04
 */
public class Student {
    private FullName fullName;
    private String idNumber;
    private int scorePercent;

    public Student(FullName fullName, String idNumber) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        scorePercent = 0;
    }

    public Student(FullName fullName, String idNumber, int scorePercent) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.scorePercent = scorePercent;
    }

    public FullName getFullName() {
        return fullName;
    }

    public String getID() {
        return idNumber;
    }

    public int getScore() {
        return scorePercent;
    }

    public void setScore(int scorePercent) {
        this.scorePercent = scorePercent;
    }

    @Override
    public String toString() {
        return idNumber + ", " + fullName + ", score = " + scorePercent;
    }
}
