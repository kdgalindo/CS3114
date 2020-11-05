package data;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class Student implements Comparable<Student> {
    private final Identity identity;
    private Grade grade;
    private boolean isActive;

    public Student(Identity identity) {
    	this.identity = identity;
        this.grade = null;
        this.isActive = true;
    }
    
    public Student(Identity identity, Grade grade) {
    	this.identity = identity;
        this.grade = grade;
        this.isActive = true;
    }
    
    public Identity getIdentity() {
    	return identity;
    }

    public long getPersonalID() {
        return identity.getPersonalID();
    }

    public FullName getFullName() {
        return identity.getFullName();
    }
    
    public String getFirstName() {
        return identity.getFirstName();
    }
    
    public String getMiddleName() {
        return identity.getMiddleName();
    }
    
    public String getLastName() {
        return identity.getLastName();
    }
    
    public Grade getGrade() {
    	return grade;
    }
    
    public void setGrade(Grade grade) {
    	this.grade = grade;
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
    	return identity.compareTo(student.getIdentity());
    }

    @Override
    public String toString() {
    	if (grade != null) {
    		return String.format("%s, score = %d", identity, grade.getPercentage());
    	}
        return identity.toString();
    }
}