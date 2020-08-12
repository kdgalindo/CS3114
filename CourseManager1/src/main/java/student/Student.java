package student;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-12
 */
public class Student {
    private final FullName fullName;
    private final String idNumber;
    private int scorePercentage;

    public Student(FullName fullName, String idNumber) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        scorePercentage = 0;
    }

    public Student(FullName fullName, String idNumber, int scorePercentage) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.scorePercentage = scorePercentage;
    }

    public FullName getFullName() {
        return fullName;
    }

    public String getIDNumber() {
        return idNumber;
    }

    public int getScorePercentage() {
        return scorePercentage;
    }

    public void setScorePercentage(int scorePercentage) {
        this.scorePercentage = scorePercentage;
    }

    @Override
    public String toString() {
        return idNumber + ", " + fullName + ", score = " + scorePercentage;
    }
}
