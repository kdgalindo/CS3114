package project1;

import java.util.ArrayList;

/**
 * CourseManager Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-05
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
    
    public int getCurrentSection() {
    	return currentSection.getNumber();
    }
    
    public void setCurrentSection(int sectionNumber) {
    	clearStudentScorable();
    	if (isValidSectionNumber(sectionNumber)) {
    		currentSection = sections[sectionNumber - 1];
    	}
    }
    
    private void clearStudentScorable() {
    	studentScorable = false;
    }
    
    public boolean isValidSectionNumber(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    public int dumpCurrentSection() {
    	clearStudentScorable();
    	return currentSection.dump();
    }
    
    public int[] gradeCurrentSection() {
    	clearStudentScorable();
    	return currentSection.gradeAllStudents();
    }
    
    public void clearSection(int sectionNumber) {
    	clearStudentScorable();
    	if (isValidSectionNumber(sectionNumber)) {
    		sections[sectionNumber - 1].removeAllStudents();
    	}
    }
    
    public void insertStudent(String firstName, String lastName) {
    	Student student = currentSection.insertStudent(firstName, lastName);
    	setStudentScorable(student);
    }
    
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
    
    public StudentPair[] findStudentPairs(int scorePercentDiff) {
    	clearStudentScorable();
    	return currentSection.findStudentPairs(scorePercentDiff);
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
    
    public boolean isStudentScorable() {
    	return studentScorable;
    }
    
    public static boolean isValidScorePercent(int scorePercent) {
    	return ((scorePercent > -1) && (scorePercent < 101));
    }
    
    public Student removeStudent(String firstName, String lastName) {
    	clearStudentScorable();
    	return currentSection.removeStudent(firstName, lastName);
    }
}
