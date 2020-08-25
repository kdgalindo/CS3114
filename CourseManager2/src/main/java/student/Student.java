package student;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class Student implements Comparable<Student> {
    private final long personalID;
    private final FullName fullName;
    private Grade grade;
    private boolean isActive;

    public Student(long personalID, FullName fullName) {
    	this.personalID = personalID;
        this.fullName = fullName;
        this.grade = null;
        this.isActive = true;
    }
    
    public Student(long personalID, FullName fullName, Grade grade) {
    	this.personalID = personalID;
        this.fullName = fullName;
        this.grade = grade;
        this.isActive = true;
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
    
    public void setPercentageGrade(int percentage) {
    	grade.setPercentage(percentage);
    }
    
    public String getLetterGrade() {
    	return grade.getLetter();
    }
    
    public void setLetterGrade(String letter) {
    	grade.setLetter(letter);
    }
    
    public boolean isActive() {
    	return isActive;
    }
    
    public void clrActive() {
    	this.isActive = false;
    }
    
    @Override
    public int compareTo(Student student) {
        return Long.compare(personalID, student.getPersonalID());
    }

    @Override
    public String toString() {
    	String identity = String.format("%09d, %s", personalID, fullName);
    	if (grade != null) {
    		return String.format("%s, score = %d", identity, grade.getPercentage());
    	}
        return identity;
    }
}
