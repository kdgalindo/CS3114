package course;

import java.util.ArrayList;

import course.enrollment.CourseEnrollment;
import course.enrollment.EnrollmentManager;
import course.section.*;
import data.*;
import identity.FullName;

public class CourseManager {
	private final int MAX_SECTION_NUM = 21;
    private SectionManager[] sManagers;
    private SectionManager sManager;
    private EnrollmentManager eManager;
    private Student currentStudent;
    private boolean studentScorable;

    public CourseManager() {
        sManagers = new SectionManager[MAX_SECTION_NUM];
        for (int i = 0; i < sManagers.length; ++i) {
            sManagers[i] = new SectionManager(new Section(i + 1));
        }
        sManager = sManagers[0];
        eManager = new EnrollmentManager();
        currentStudent = null;
        studentScorable = false;
    }
    
    public Section getCommandableSection() {
    	return sManager.getSection();
    }
    
    /**
     * 
     * @param sectionNumber
     */
    public void setCommandableSection(int sectionNum) {
    	clearStudentScorable();
        if (isValidSectionNum(sectionNum)) {
        	sManager = sManagers[sectionNum - 1];
        }
    }
    
    public void clearStudentScorable() {
    	studentScorable = false;
    }
    
    public boolean isValidSectionNum(int sectionNum) {
    	return (sectionNum > 0) && (sectionNum < MAX_SECTION_NUM + 1);
    }
    
    /**
     * 
     * @param personalID
     * @return
     */
    public Student find(long personalID) {
    	clearStudentScorable();
        Student student = sManager.find(personalID);
        if (student != null) {
        	setStudentScorable(student);
        }
        return student;
    }
    
    /**
     * 
     * @param fullName
     * @return
     */
    public Student[] find(FullName fullName) {
    	clearStudentScorable();
        Student[] students = sManager.find(fullName);
        if (students.length == 1) {
            setStudentScorable(students[0]);
        }
        return students;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Student[] find(String name) {
    	clearStudentScorable();
        Student[] students = sManager.find(name);
        if (students.length == 1) {
            setStudentScorable(students[0]);
        }
        return students;
    }
    
    /**
     * 
     * @param student
     * @return
     */
    public Student insert(Student student) {
    	clearStudentScorable();
        if (eManager.isEnrolled(student.getPersonalID())) {
        	return null;
        }
        
        insertIntoModifiableSection(student);
        setStudentScorable(student);
        return student;
    }
    
    private void insertIntoModifiableSection(Student student) {
    	sManager.insert(student);
    	eManager.enroll(student.getPersonalID(), getCommandableSection().getNumber());
    }
    
    public int getSectionNum(Section section) {
    	return section.getNumber();
    }
    
    private void setStudentScorable(Student student) {
    	currentStudent = student;
    	studentScorable = true;
    }
    
    /**
     * 
     * @param student
     * @param sectionNum
     * @return
     */
    public Student insert(Student student, int sectionNum) {
    	clearStudentScorable();
        Integer eSectionNum = eManager.findEnrollment(student.getPersonalID());
        if (eSectionNum == null) {
        	insertIntoModifiableSection(student, sectionNum);
        }
        else if (eSectionNum == sectionNum) {
        	updateInModifiableSection(student, sectionNum);
        }
        else {
        	return null;
        }
        return student;
    }
    
    public Integer findEnrollment(long personalID) {
    	return eManager.findEnrollment(personalID);
    }
    
    private void insertIntoModifiableSection(Student student, int sectionNum) {
    	sManagers[sectionNum - 1].insert(student);
    	eManager.enroll(student.getPersonalID(), sectionNum);
    }
    
    private void updateInModifiableSection(Student student, int sectionNum) {
    	sManagers[sectionNum - 1].updateGrade(student.getPersonalID(), student.getGrade());
    }
    
    /**
     * 
     * @param percentageGrade
     * @return
     */
    public Student scoreStudent(int percentageGrade) {
        if (isStudentScorable()) {
        	sManager.updatePercentageGrade(currentStudent.getPersonalID(), percentageGrade);
        }
        clearStudentScorable();
        return currentStudent;
    }
    
    public boolean isStudentScorable() {
    	return studentScorable;
    }
    
    public static boolean isValidPercentageGrade(int percentageGrade) {
    	return (percentageGrade > -1) && (percentageGrade < 101);
    }
    
    /**
     * 
     * @param personalID
     * @return
     */
    public Student remove(long personalID) {
    	clearStudentScorable();
        Integer eSectionNum = eManager.findEnrollment(personalID);
        if ((eSectionNum == null) || (eSectionNum != getCommandableSection().getNumber())) {
        	return null;
        }
        
        return sManager.remove(personalID);
    }
    
    /**
     * 
     * @param fullName
     * @return
     */
    public Student remove(FullName fullName) {
    	clearStudentScorable();
        Student[] students = sManager.find(fullName);
        if (students.length != 1) {
        	return null;
        }
        
        return sManager.remove(fullName);
    }

    /**
     * 
     */
    public void clear() {
    	clearStudentScorable();
        sManager.clear();
    }
    
    /**
     * 
     * @return
     */
    public int dumpPIDs() {
    	clearStudentScorable();
    	sManager.printStudentsByPersonalID();
        return sManager.size();
    }
    
    public int dumpNames() {
    	clearStudentScorable();
    	sManager.printStudentsByFullName();
        return sManager.size();
    }
    
    public int dumpScores() {
    	clearStudentScorable();
    	sManager.printStudentsByPercentageGrade();
        return sManager.size();
    }
    
    public void gradeStudents() {
        clearStudentScorable();
        sManager.gradeAllStudents();
    }
    
    public void statStudents() {
        clearStudentScorable();
        sManager.statAllStudents();
    }
    
    public Student[] listStudents(String letter) {
    	clearStudentScorable();
        return sManager.listAllStudents(letter);
    }
    
    public ArrayList<String> findpair(int scorePercentageDiff) {
    	clearStudentScorable();
    	return sManager.findStudentPairs(scorePercentageDiff);
    }
    
    public boolean mergeSections() {
        clearStudentScorable();
        boolean result = false;
        if (sManager.isEmpty()) {
            setUnmodifiable(sManager.getSection());
            for (int i = 0; i < sManagers.length; i++) {
                if (isModifiable(sManagers[i].getSection())) {
                	Student[] students = sManagers[i].getStudents();
                	for (int j = 0; j < students.length; j++) {
                		sManager.insert(students[j]);
                	}
                }
            }
            result = true;
        }
        return result;
    }
    
    private void setUnmodifiable(Section section) {
    	section.setModifiable(false);
    }
    
    public boolean isModifiable(Section section) {
    	return section.isModifiable();
    }
    
    public CourseEnrollment getEnrollment() {
    	int size = getNumberOfActiveSections();
    	SectionEnrollment[] sEnrollments = new SectionEnrollment[size];
    	for (int i = 0; i < sEnrollments.length; ++i) {
    		sEnrollments[i] = sManagers[i].getEnrollment();
    	}
    	return new CourseEnrollment(sEnrollments);
    }
    
    private int getNumberOfActiveSections() {
    	int i = 0;
    	while ((i < sManagers.length) && sManagers[i].hasEnrollment()) {
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
            sManagers[i].clear();
        }
        eManager.clear();
    }
}
