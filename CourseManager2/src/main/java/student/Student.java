package student;

import grade.Grade;
import identity.FullName;
import identity.Identity;

/**
 * Student Class
 * 
 * Students have an identity and a grade. A student's identity cannot be
 * modified, but the Grader can modify a student's grade.
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-10
 */
public class Student implements Comparable<Student> {
    private final Identity identity;
    private final Grade grade;

    public Student(Identity identity) {
    	this.identity = identity;
        this.grade = null;
    }
    
    public Student(Identity identity, Grade grade) {
    	this.identity = identity;
        this.grade = grade;
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
