package course;

import java.util.List;

import course.enrollment.*;
import course.section.*;
import grade.Grade;
import identity.FullName;
import student.*;

/** 
 * CourseManager Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2021-01-05
 */
public class CourseManager {
	private final int MAX_SECTION_NUMBER = 21;
	private final Section[] sections;
    private final SectionManager[] sManagers;
    private final EnrollmentManager eManager;
    
    private Section section;
    private SectionManager sManager;

    public CourseManager() {
    	sections = new Section[MAX_SECTION_NUMBER];
        sManagers = new SectionManager[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; ++i) {
        	sections[i] = new Section(i + 1);
            sManagers[i] = new SectionManager(sections[i]);
        }
        eManager = new EnrollmentManager();
        
        section = sections[0];
        sManager = sManagers[0];
    }
    
    public Section getCmdSection() {
    	return section;
    }
    
    public void setCmdSection(int sectionNumber) {
        if (!isValidSectionNumber(sectionNumber)) {
        	throw new IllegalArgumentException("Message"); // TODO
        }
        
        section = sections[sectionNumber - 1];
    	sManager = sManagers[sectionNumber - 1];
    }
    
    public boolean isValidSectionNumber(int sNumber) {
    	return (sNumber >= 1) && (sNumber <= MAX_SECTION_NUMBER);
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
    	int i;
    	for (i = 0; i < MAX_SECTION_NUMBER; ++i) {
    		if (!sections[i].isModifiable() || sManagers[i].isSectionEmpty()) {
    			break;
    		}
    	}
    	return i;
    }
    
    /**
     * Returns the student associated with the specified personal identifier
     * (PID) if the student is enrolled in the current section.
     * 
     * @param pIdentifier a student's PID
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the current section.
     */
    public Student find(long pIdentifier) {
        return sManager.find(pIdentifier);
    }
    
    /**
     * Returns a list of all students associated with the specified full name
     * enrolled in the current section.
     * 
     * @param fName a student's full name
     * @return a list of all students associated with the full name enrolled
     * in the current section.
     */
    public List<Student> find(FullName fName) {
        return sManager.find(fName);
    }
    
    /**
     * Returns a list of all students containing the specified name enrolled
     * in the current section.
     * 
     * @param name a name
     * @return a list of all students containing the name enrolled in the
     * current section.
     */
    public List<Student> find(String name) {
        return sManager.find(name);
    }
    
    /**
     * Enrolls the given student into the current section.
     * 
     * @param student A student
     * @exception IllegalArgumentException if student is already enrolled in
     * the course.
     */
    public void insert(Student student) {
        if (eManager.isEnrolled(student.getPersonalID())) {
        	// TODO // Keep/fix message or change
        	throw new IllegalArgumentException("Message");
        }
        
        sManager.insert(student);
    	eManager.enroll(student.getPersonalID(), section.getNumber());
    }
    
    public Integer findEnrollment(long personalID) {
    	return eManager.findEnrollment(personalID);
    }
    
    /**
     * Updates the course grade of the student (associated with the specified
     * personal identifier (PID)) with the specified grade if the student is
     * enrolled in the current section.
     * 
     * @param pIdentifier a student's PID
     * @param grade a grade for the course
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the current section.
     */
    public Student updateGrade(long pIdentifier, Grade grade) {
    	return sManager.updateGrade(pIdentifier, grade);
    }
    
    /**
     * Updates the percentage grade of the student (associated with the
     * specified personal identifier (PID)) with the specified percentage
     * grade if the student is enrolled in the current section.
     * 
     * @param pIdentifier a student's PID
     * @param pGrade a percentage grade for the course
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the current section.
     */
    public Student updatePercentageGrade(long pIdentifier, int pGrade) {
    	return sManager.updatePercentageGrade(pIdentifier, pGrade);
    }
    
    /**
     * Updates the letter grades of all students in the current section to
     * correspond with their percentage grades.
     */
    public void updateLetterGrades() {
        sManager.updateLetterGrades();
    }
    
    /**
     * Unenrolls the student associated with the specified personal
     * identifier (PID) from the current section if the student is enrolled in
     * the section.
     * 
     * @param pIdentifier a student's PID
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the section.
     */
    public Student remove(long pIdentifier) {
    	Student student = sManager.remove(pIdentifier);
    	if (student != null) {
    		eManager.unenroll(pIdentifier);
    	}
        return student;
    }
    
    /**
     * Unenrolls the student associated with the specified full name from the
     * current section if only one such student is enrolled in the current
     * section.
     * 
     * @param fName a student's full name
     * @return the student associated with the full name, or null if the
     * student is not enrolled in the current section or more than one student
     * associated with the full name is in the current section.
     */
    public Student remove(FullName fName) {
    	Student student = sManager.remove(fName);
    	if (student != null) {
    		eManager.unenroll(student.getPersonalID());
    	}
        return student;
    }

    /**
     * Unenrolls all students from the current section.
     */
    public void clear() {
        sManager.clear();
    }
    
    public List<Student> listInPersonalIDOrder() {
    	return sManager.listInPersonalIDOrder();
    }
    
    public List<Student> listInFullNameOrder() {
    	return sManager.listInFullNameOrder();
    }

    public List<Student> listInPercentageGradeOrder() {
    	return sManager.listInPercentageGradeOrder();
    }

    public List<String> listGradeLevelStats() {
        return sManager.listGradeLevelStats();
    }

    public List<Student> listInGradeLevel(String lGrade) {
        return sManager.listInGradeLevel(lGrade);
    }

    public List<String> listStudentPairsWithin(int pGradeDiff) {
    	return sManager.listStudentPairsWithin(pGradeDiff);
    }

    public boolean mergeSections() {
        if (!sManager.isSectionEmpty()) {
        	return false;
        }
        
        section.setModifiable(false);
        for (int i = 0; i < MAX_SECTION_NUMBER; ++i) {
        	if (sections[i].isModifiable()) {
        		for (Student student : sManagers[i].getStudents()) {
        			sManager.insert(student);
        		}
        	}
        }
        
        return true;
    }
    
    public boolean isModifiable(Section section) {
    	return section.isModifiable();
    }

    public void clearSections() {
        for (int i = 0; i < MAX_SECTION_NUMBER; ++i) {
            sManagers[i].clear();
        }
        eManager.clear();
    }
}
