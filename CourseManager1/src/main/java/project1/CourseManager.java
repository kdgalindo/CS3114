package project1;

import java.util.ArrayList;

/**
 * CourseManager Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-28
 */
public class CourseManager {
	private final int MAX_SECTION_NUMBER = 3;
	
    private Section[] sections;
    private Section currentSection;
    
    private Student currentStudent;
    private boolean studentScorable;
    
    public CourseManager() {
        sections = new Section[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new Section(sectionNumber);
        }
        currentSection = sections[0];
        currentStudent = null;
        studentScorable = false;
    }
    
    public int getCurrentSection() {
    	return currentSection.getNumber();
    }
    
    public void setCurrentSection(int sectionNumber) {
    	if (isValidSectionNumber(sectionNumber)) {
    		currentSection = sections[sectionNumber - 1];
    	}
    	clearStudentScorable();
    }
    
    private void clearStudentScorable() {
    	studentScorable = false;
    }
    
    public boolean isValidSectionNumber(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    public int dumpCurrentSection() {
    	clearStudentScorable();
    	return currentSection.dumpSection();
    }
    
    public void gradeCurrentSection() {
    	currentSection.grade();
    	clearStudentScorable();
    }
    
    public void clearSection(int sectionNumber) {
    	if (isValidSectionNumber(sectionNumber)) {
    		sections[sectionNumber - 1].removeSection();
    	}
    	clearStudentScorable();
    }
    
    public void insertStudent(String firstName, String lastName) {
    	Student student = currentSection.insert(firstName, lastName);
    	setStudentScorable(student);
    }
    
    private void setStudentScorable(Student student) {
    	currentStudent = student;
    	studentScorable = true;
    }
    
    public Student findStudent(String firstName, String lastName) {
    	Student student = currentSection.search(firstName, lastName);
    	setStudentScorable(student);
    	return student;
    }
    
    public ArrayList<Student> findStudents(String name) {
    	ArrayList<Student> students = currentSection.search(name);
    	if (students.size() == 1) {
    		setStudentScorable(students.get(0));
    	}
    	else {
    		clearStudentScorable();
    	}
    	return students;
    }
    
    public void findStudentPairs(int scorePercentDiff) {
    	currentSection.findPair(scorePercentDiff);
    	clearStudentScorable();
    }
    
    public Student scoreStudent(int scorePercent) {
    	if (isStudentScorable()) {
    		if (isValidScorePercent(scorePercent)) {
    			currentSection.score(scorePercent, currentStudent);
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
    	return currentSection.remove(firstName, lastName);
    }
}
