package course;

import java.util.List;

import course.enrollment.*;
import course.section.*;
import identity.FullName;
import student.*;

/** 
 * CourseManager Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-08
 */
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
    
    /**
     * 
     * @return
     */
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
    
    public void clearStudentScorable() { // TODO
    	studentScorable = false;
    }
    
    /**
     * 
     * @param sectionNum
     * @return
     */
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
    public List<Student> find(FullName fullName) {
    	clearStudentScorable();
        List<Student> students = sManager.find(fullName);
        if (students.size() == 1) {
            setStudentScorable(students.get(0));
        }
        return students;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public List<Student> find(String name) {
    	clearStudentScorable();
    	List<Student> students = sManager.find(name);
        if (students.size() == 1) {
            setStudentScorable(students.get(0));
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
    
    private void setStudentScorable(Student student) { // TODO
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
        sManager.updateGrades();
    }
    
    /**
     * 
     * @return
     */
    public List<String> statStudents() {
        clearStudentScorable();
        return sManager.listGradeLevelStats();
    }
    
    /**
     * 
     * @param lGradeLevel
     * @return
     */
    public List<Student> listStudents(String lGradeLevel) {
    	clearStudentScorable();
        return sManager.listStudentsIn(lGradeLevel);
    }
    
    /**
     * 
     * @param pGradeDiff
     * @return
     */
    public List<String> findpair(int pGradeDiff) {
    	clearStudentScorable();
    	return sManager.listStudentPairsWithin(pGradeDiff);
    }
    
    public boolean mergeSections() { // TODO
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
