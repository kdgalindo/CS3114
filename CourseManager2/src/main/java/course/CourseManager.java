package course;

import java.util.ArrayList;

import data.CourseEnrollment;
import data.FullName;
import data.SectionEnrollment;
import data.Student;
import util.BST;

public class CourseManager {
	private final int MAX_SECTION_NUMBER = 21;
    private SectionManager[] sections;
    private SectionManager currentSection;
    private Student currentStudent;
    private boolean studentScorable;
    private BST<Long, Integer> pidSectionDB;

    public CourseManager() {
        sections = new SectionManager[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new SectionManager(sectionNumber);
        }
        currentSection = sections[0];
        currentStudent = null;
        studentScorable = false;
        pidSectionDB = new BST<Long, Integer>();
    }

    public int getSectionNumber() {
        return currentSection.getSectionNumber();
    }
    
    public boolean isSectionActive() {
        return currentSection.isSectionActive();
    }

    /**
     * Grade command cannot come after
     * load student data command
     */
    public void loadstudentdata() {
    	clearStudentScorable();
    }
    
    private void clearStudentScorable() {
    	studentScorable = false;
    }
    
    public Student loadCourseData(int sectionNumber, Student newStudent) {
    	clearStudentScorable();
        Student student = null;
        long personalID = newStudent.getPersonalID(); // student pid
        Integer cSectionNumber = findStudentSection(personalID); // current section number
        if (cSectionNumber == null) { // Check if in section
        	student = newStudent;
        	addToActiveSection(student, sectionNumber);
        }
        else if (cSectionNumber == sectionNumber) { // Check if in current section
        	student = newStudent;
            sections[sectionNumber - 1].setGrade(student);
        }
        return student;
    }
    
    public Integer findStudentSection(long personalID) {
        return pidSectionDB.find(personalID);
    }
    
    private void addToActiveSection(Student student, int sectionNumber) {
    	addToSection(student, sectionNumber);
    	pidSectionDB.insert(student.getPersonalID(), sectionNumber);
    }
    
    private void addToSection(Student student, int sectionNumber) {
    	sections[sectionNumber - 1].insert(student);
    }

    /**
     * 
     * @param sectionNumber
     */
    public void setSection(int sectionNumber) {
    	clearStudentScorable();
        if (isValidSection(sectionNumber)) {
        	currentSection = sections[sectionNumber - 1];
        }
    }
    
    public boolean isValidSection(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    /**
     * 
     * @param student
     * @return
     */
    public Student insert(Student student) {
    	clearStudentScorable();
        if (!isStudentEnrolled(student.getPersonalID())) {
        	addToActiveSection(student);
            setStudentScorable(student);
            return student;
        }
        return null;
    }
    
    private boolean isStudentEnrolled(long personalID) {
    	return (findStudentSection(personalID) != null);
    }
    
    public void addToActiveSection(Student student) {
    	addToSection(student);
    	pidSectionDB.insert(student.getPersonalID(), currentSection.getSectionNumber());
    }
    
    private void addToSection(Student student) {
    	currentSection.insert(student);
    }
    
    private void setStudentScorable(Student student) {
    	currentStudent = student;
    	studentScorable = true;
    }
    
    /**
     * 
     * @param personalID
     * @return
     */
    public Student findStudent(long personalID) {
    	clearStudentScorable();
        Student student = currentSection.find(personalID);
        if (student != null) {
        	setStudentScorable(student);
        }
        return student;
    }
    
    public Student[] findStudents(FullName fullName) {
    	clearStudentScorable();
        Student[] students = currentSection.find(fullName);
        if (students.length == 1) {
            setStudentScorable(students[0]);
        }
        return students;
    }
    
    public Student[] findStudents(String name) {
    	clearStudentScorable();
        Student[] students = currentSection.find(name);
        if (students.length == 1) {
            setStudentScorable(students[0]);
        }
        return students;
    }
    
    public Student scoreStudent(int percentageGrade) {
        if (isStudentScorable()) {
        	currentSection.score(currentStudent, percentageGrade);
        }
        clearStudentScorable();
        return currentStudent;
    }
    
    public boolean isStudentScorable() {
    	return studentScorable;
    }
    
    public static boolean isValidPercentageGrade(int percentageGrade) {
    	return ((percentageGrade > -1) && (percentageGrade < 101));
    }
    
    public Student removeStudent(long personalID) {
    	clearStudentScorable();
        Integer sectionNumber = pidSectionDB.find(personalID);
        if ((sectionNumber != null) && (sectionNumber == currentSection.getSectionNumber())) {
        	return currentSection.remove(personalID);
        }
        return null;
    }
    
    public Student removeStudent(FullName fullName) {
    	clearStudentScorable();
        Student student = null;
        Student[] students = currentSection.find(fullName);
        if (students.length == 1) { // Check if name unique
        	student = currentSection.remove(fullName);
        }
        return student;
    }

    /**
     * 
     */
    public void clearSection() {
    	clearStudentScorable();
        currentSection.clear();
    }
    
    /**
     * 
     * @return
     */
    public int dumpPIDs() {
    	clearStudentScorable();
    	currentSection.printStudentsByPersonalID();
        return currentSection.size();
    }
    
    public int dumpNames() {
    	clearStudentScorable();
    	currentSection.printStudentsByFullName();
        return currentSection.size();
    }
    
    public int dumpScores() {
    	clearStudentScorable();
    	currentSection.printStudentsByPercentageGrade();
        return currentSection.size();
    }
    
    public void gradeStudents() {
        clearStudentScorable();
        currentSection.gradeAllStudents();
    }
    
    public void statStudents() {
        clearStudentScorable();
        currentSection.statAllStudents();
    }
    
    public Student[] listStudents(String letter) {
    	clearStudentScorable();
        return currentSection.listAllStudents(letter);
    }
    
    public ArrayList<String> findpair(int scorePercentageDiff) {
    	clearStudentScorable();
    	return currentSection.findStudentPairs(scorePercentageDiff);
    }
    
    public boolean mergeSections() {
        clearStudentScorable();
        boolean result = false;
        if (currentSection.isEmpty()) {
            currentSection.setActive(false);
            for (int i = 0; i < sections.length; i++) {
                if (sections[i].isSectionActive()) {
                	Student[] students = sections[i].getStudents();
                	for (int j = 0; j < students.length; j++) {
                		currentSection.insert(students[j]);
                	}
                }
            }
            result = true;
        }
        return result;
    }
    
    public CourseEnrollment getEnrollment() {
    	int size = getNumberOfActiveSections();
    	SectionEnrollment[] sEnrollments = new SectionEnrollment[size];
    	for (int i = 0; i < sEnrollments.length; ++i) {
    		sEnrollments[i] = sections[i].getEnrollment();
    	}
    	return new CourseEnrollment(sEnrollments);
    }
    
    private int getNumberOfActiveSections() {
    	int i = 0;
    	while ((i < sections.length) && sections[i].hasEnrollment()) {
    		++i;
    	}
    	return i;
    }

    /**
     * Clears all student records
     * from CourseManager
     */
    public void clearcoursedata() {
        clearStudentScorable();
        for (int i = 0; i < 21; i++) {
            sections[i].clear();
        }
        pidSectionDB.clear();
    }
}
