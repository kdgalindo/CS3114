package project1;

import java.util.ArrayList;

/**
 * CourseManager Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-10
 */
public class CourseManager {
	private final int MAX_SECTION_NUMBER = 3;
    private CourseSection[] sections;
    private CourseSection currentSection;
    private Student currentStudent;
    private boolean studentScorable;
    
    public CourseManager() {
        sections = new CourseSection[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new CourseSection(sectionNumber);
        }
        currentSection = sections[0];
        currentStudent = null;
        studentScorable = false;
    }
    
    /**
     * 
     * @return
     */
    public int getSection() {
    	return currentSection.getNumber();
    }
    
    public void setSection(int sectionNumber) {
    	clearStudentScorable();
    	if (isValidSection(sectionNumber)) {
    		currentSection = sections[sectionNumber - 1];
    	}
    }
    
    /**
     * 
     */
    private void clearStudentScorable() {
    	studentScorable = false;
    }
    
    /**
     * 
     * @param sectionNumber
     * @return
     */
    public boolean isValidSection(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    public void insertStudent(String firstName, String lastName) {
    	Student student = currentSection.insertStudent(firstName, lastName);
    	setStudentScorable(student);
    }
    
    /**
     * 
     * @param student
     */
    private void setStudentScorable(Student student) {
    	currentStudent = student;
    	studentScorable = true;
    }
    
    public Student findStudent(String firstName, String lastName) {
    	Student student = currentSection.findStudent(firstName, lastName);
    	setStudentScorable(student);
    	return student;
    }
    
    public Student[] findStudents(String name) {
    	Student[] students = currentSection.findStudents(name);
    	if (students.length == 1) {
    		setStudentScorable(students[0]);
    	}
    	else {
    		clearStudentScorable();
    	}
    	return students;
    }
    
    public Student scoreStudent(int scorePercent) {
    	if (isStudentScorable()) {
    		if (isValidScorePercent(scorePercent)) {
    			currentSection.score(currentStudent, scorePercent);
    		}
    	}
    	clearStudentScorable();
    	return currentStudent;
    }
    
    /**
     * 
     * @return
     */
    public boolean isStudentScorable() {
    	return studentScorable;
    }
    
    /**
     * 
     * @param scorePercent
     * @return
     */
    public static boolean isValidScorePercent(int scorePercent) {
    	return ((scorePercent > -1) && (scorePercent < 101));
    }
    
    public Student removeStudent(String firstName, String lastName) {
    	clearStudentScorable();
    	return currentSection.removeStudent(firstName, lastName);
    }
    
    public void clearSection(int sectionNumber) {
    	clearStudentScorable();
    	if (isValidSection(sectionNumber)) {
    		sections[sectionNumber - 1].removeAllStudents();
    	}
    }
    
    public int dumpSection() {
    	clearStudentScorable();
    	return currentSection.dump();
    }
    
    public void gradeSection() {
    	clearStudentScorable();
    	currentSection.gradeAllStudents();
    }
    
    public StudentPair[] findStudentPairs(int scorePercentDiff) {
    	clearStudentScorable();
    	return currentSection.findStudentPairs(scorePercentDiff);
    }
}
