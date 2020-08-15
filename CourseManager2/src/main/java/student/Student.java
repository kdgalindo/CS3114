package student;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-14
 */
public class Student implements Comparable<Student> {
    private final long personalID;
    private final FullName fullName;
    private Grade grade;

    public Student(long personalID, FullName fullName) {
    	this.personalID = personalID;
        this.fullName = fullName;
        this.grade = null;
    }

    public long getPersonalID() {
        return personalID;
    }

    public FullName getFullName() {
        return fullName;
    }
    
    public String getFirstName() {
        return fullName.getFirstName();
    }
    
    public String getMiddleName() {
        return fullName.getMiddleName();
    }
    
    public String getLastName() {
        return fullName.getLastName();
    }
    
    public Grade getGrade() {
    	return grade;
    }
    
    public int getPercentageGrade() {
    	return grade.getPercentage();
    }
    
    public String getLetterGrade() {
    	return grade.getLetter();
    }
    
    @Override
    public int compareTo(Student student) {
        return Long.compare(personalID, student.getPersonalID());
    }

    @Override
    public String toString() {
        return String.format("%09d, %s", personalID, fullName);
    }
}
