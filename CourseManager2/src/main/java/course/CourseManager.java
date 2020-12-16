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
    private boolean isStudentGradable;

    public CourseManager() {
        sManagers = new SectionManager[MAX_SECTION_NUM];
        for (int i = 0; i < sManagers.length; ++i) {
            sManagers[i] = new SectionManager(new Section(i + 1));
        }
        sManager = sManagers[0];
        eManager = new EnrollmentManager();
        currentStudent = null;
        isStudentGradable = false;
    }
    
    /**
     * 
     * @return
     */
    public Section getCmdSection() {
    	return sManager.getSection();
    }
    
    /**
     * 
     * @param sectionNum Section number
     */
    public void setCmdSection(int sectionNum) {
    	makeStudentUngradable();
        if (isValidSectionNum(sectionNum)) {
        	sManager = sManagers[sectionNum - 1];
        }
    }
    
    public void makeStudentUngradable() {
    	isStudentGradable = false;
    }
    
    /**
     * 
     * @param sectionNum
     * @return
     */
    public boolean isValidSectionNum(int sectionNum) {
    	return (sectionNum > 0) && (sectionNum < MAX_SECTION_NUM + 1);
    }
    
    public CourseEnrollment getEnrollment() { // TODO
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
     * 
     * @param personalID Student PID
     * @return
     */
    public Student find(long personalID) {
    	makeStudentUngradable();
        Student student = sManager.find(personalID);
        if (student != null) {
        	makeStudentGradable(student);
        }
        return student;
    }
    
    /**
     * 
     * @param fullName Student full name
     * @return
     */
    public List<Student> find(FullName fullName) {
    	makeStudentUngradable();
        List<Student> students = sManager.find(fullName);
        if (students.size() == 1) {
        	makeStudentGradable(students.get(0));
        }
        return students;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public List<Student> find(String name) {
    	makeStudentUngradable();
    	List<Student> students = sManager.find(name);
        if (students.size() == 1) {
        	makeStudentGradable(students.get(0));
        }
        return students;
    }
    
    /**
     * 
     * @param student Student
     * @return
     */
    public Student insert(Student student) {
    	makeStudentUngradable();
        if (eManager.isEnrolled(student.getPersonalID())) {
        	return null;
        }
        
        insertIntoModifiableSection(student);
        makeStudentGradable(student);
        return student;
    }
    
    private void insertIntoModifiableSection(Student student) {
    	sManager.insert(student);
    	eManager.enroll(student.getPersonalID(), getCmdSection().getNumber());
    }
    
    public int getSectionNum(Section section) {
    	return section.getNumber();
    }
    
    private void makeStudentGradable(Student student) { // TODO
    	currentStudent = student;
    	isStudentGradable = true;
    }
    
    /**
     * 
     * @param student Student
     * @param sectionNum Section number
     * @return
     */
    public Student insert(Student student, int sectionNum) {
    	makeStudentUngradable();
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
    
    /**
     * 
     * @param personalID
     * @return
     */
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
     * @param pGrade Percentage grade
     * @return
     */
    public Student updatePercentageGrade(int pGrade) {
        if (isStudentGradable()) {
        	sManager.updatePercentageGrade(currentStudent.getPersonalID(), pGrade);
        }
        makeStudentUngradable();
        return currentStudent;
    }
    
    public boolean isStudentGradable() {
    	return isStudentGradable;
    }
    
    /**
     * 
     */
    public void updateGrades() {
        makeStudentUngradable();
        sManager.updateGrades();
    }
    
    /**
     * 
     * @param personalID Student PID
     * @return
     */
    public Student remove(long personalID) {
    	makeStudentUngradable();
        Integer eSectionNum = eManager.findEnrollment(personalID);
        // TODO
        if ((eSectionNum == null) || (eSectionNum != getCmdSection().getNumber())) {
        	return null;
        }
        
        return sManager.remove(personalID);
    }
    
    /**
     * 
     * @param fullName Student full name
     * @return
     */
    public Student remove(FullName fullName) {
    	makeStudentUngradable();
        
        return sManager.remove(fullName);
    }

    /**
     * 
     */
    public void clear() {
    	makeStudentUngradable();
        sManager.clear();
    }
    
    /**
     * 
     * @return
     */
    public List<Student> listInPersonalIDOrder() {
    	makeStudentUngradable();
    	return sManager.listInPersonalIDOrder();
    }
    
    /**
     * 
     * @return
     */
    public List<Student> listInFullNameOrder() {
    	makeStudentUngradable();
    	return sManager.listInFullNameOrder();
    }
    
    /**
     * 
     * @return
     */
    public List<Student> listInPercentageGradeOrder() {
    	makeStudentUngradable();
    	return sManager.listInPercentageGradeOrder();
    }
    
    /**
     * 
     * @return
     */
    public List<String> listGradeLevelStats() {
        makeStudentUngradable();
        return sManager.listGradeLevelStats();
    }
    
    /**
     * 
     * @param lGrade Letter grade
     * @return
     */
    public List<Student> listInGradeLevel(String lGrade) {
    	makeStudentUngradable();
        return sManager.listInGradeLevel(lGrade);
    }
    
    /**
     * 
     * @param pGradeDiff Percentage grade difference
     * @return
     */
    public List<String> listStudentPairsWithin(int pGradeDiff) {
    	makeStudentUngradable();
    	return sManager.listStudentPairsWithin(pGradeDiff);
    }
    
    /**
     * 
     * @return
     */
    public boolean mergeSections() {
        makeStudentUngradable();
        
        if (!sManager.isEmpty()) {
        	return false;
        }
        
        setUnmodifiable(sManager.getSection());
        for (int i = 0; i < sManagers.length; ++i) {
        	if (isModifiable(sManagers[i].getSection())) {
        		for (Student student : sManagers[i].getActiveStudents()) {
        			this.sManager.insert(student); // TODO
        		}
        	}
        }
        
        return true;
    }
    
    private void setUnmodifiable(Section section) {
    	section.setModifiable(false);
    }
    
    public boolean isModifiable(Section section) {
    	return section.isModifiable();
    }
    
    /**
     * 
     */
    public void clearSections() {
        makeStudentUngradable();
        for (int i = 0; i < sManagers.length; ++i) {
            sManagers[i].clear();
        }
        eManager.clear();
    }
}
