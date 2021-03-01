package course.section;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import grade.*;
import identity.FullName;
import student.Student;
import util.BST;

/** 
 * SectionManager Class
 * 
 * 
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2021-02-22
 */
public class SectionManager {
    private final Section section;
    private final BST<Long, Integer> pIdentifierIndices;
    private final BST<FullName, Integer> fNameIndices;
    private final BST<Integer, Integer> pGradeIndices;
    private final ArrayList<Enrollment> enrollments;
    
    private static class Enrollment {
    	private final Student student;
    	private boolean tombstone;
    	 
    	public Enrollment(Student student) {
    		this.student = student;
    		this.tombstone = false;
    	}
    	
    	public Student getStudent() {
    		return student;
    	}
    	
    	public boolean isRemoved() {
    		return tombstone;
    	}
    	
    	public void remove() {
    		tombstone = true;
    	}
    }
    
    public SectionManager(Section section) {
        this.section = section;
    	this.pIdentifierIndices = new BST<Long, Integer>();
        this.fNameIndices = new BST<FullName, Integer>();
        this.pGradeIndices = new BST<Integer, Integer>();
        this.enrollments = new ArrayList<Enrollment>();
    }
    
    public Section getSection() {
    	return section;
    }
    
    public SectionEnrollment getEnrollment() { // TODO
    	List<Student> students = getStudents();
    	return new SectionEnrollment(section.getNumber(), students.toArray(new Student[students.size()]));
    }
    
    /**
     * Returns true if no students are enrolled in the section.
     * 
     * @return true if no students are enrolled in the section.
     */
    public boolean isSectionEmpty() {
        return pIdentifierIndices.size() == 0;
    }
    
    /**
     * Returns the number of students enrolled in the section.
     * 
     * @return the number of students enrolled in the section.
     */
    public int sectionSize() {
        return pIdentifierIndices.size();
    }
    
    /**
     * Returns a list of all students enrolled in the section.
     * 
     * @return a list of all students enrolled in the section.
     */
    public List<Student> getStudents() {
    	List<Student> students = new ArrayList<Student>();
    	for (Enrollment enrollment : enrollments) {
    		if (!enrollment.isRemoved()) {
    			students.add(enrollment.getStudent());
    		}
    	}
    	return students;
    }
    
    /**
     * Returns the student associated with the specified personal identifier
     * (PID) if the student is enrolled in the section.
     * 
     * @param pIdentifier a student's PID
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the section.
     */
    public Student find(long pIdentifier) {
    	Integer index = pIdentifierIndices.find(pIdentifier);
    	if (index == null) {
    		return null;
    	}
    	
    	return getStudent(index);
    }
    
    /**
     * Returns the student at the specified index in enrollments.
     * 
     * @param index an enrollment index in enrollments
     * @return the student at the index in enrollments.
     */
    private Student getStudent(int index) {
    	return enrollments.get(index).getStudent();
    }
    
    /**
     * Returns a list of all students associated with the specified full name
     * enrolled in the section.
     * 
     * @param fName a student's full name
     * @return a list of all students associated with the full name enrolled
     * in the section.
     */
    public List<Student> find(FullName fName) {
    	List<Student> students = new ArrayList<Student>();
    	for (Integer index : fNameIndices.findAll(fName)) {
    		students.add(getStudent(index));
    	}
    	return students;
    }
    
    /**
     * Returns a list of all students containing the specified name enrolled
     * in the section.
     * 
     * @param name a name
     * @return a list of all students containing the name enrolled in the
     * section.
     */
    public List<Student> find(String name) {
        List<Student> students = new ArrayList<Student>();
        for (int index : fNameIndices) {
            Student student = getStudent(index);
            FullName fName = student.getFullName();
            if (fName.containsName(name)) {
            	students.add(student);
            }
        }
        return students;
    }
    
    /**
     * Enrolls the specified student into the section.
     * 
     * @param student a student
     * @exception IllegalArgumentException if the student is already enrolled
     * in the section.
     */
    public void insert(Student student) {
    	if (pIdentifierIndices.find(student.getPersonalID()) != null) {
    		 // TODO // Keep/fix message or change
    		throw new IllegalArgumentException("Message");
    	}
    	
    	int index = enrollments.size();
    	pIdentifierIndices.insert(student.getPersonalID(), index);
    	fNameIndices.insert(student.getFullName(), index);
    	pGradeIndices.insert(student.getPercentageGrade(), index);
    	enrollments.add(new Enrollment(student));
    }
    
    /**
     * Updates the course grade of the student (associated with the specified
     * personal identifier (PID)) with the specified grade if the student is
     * enrolled in the section.
     * 
     * @param pIdentifier a student's PID
     * @param grade a grade for the course
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the section.
     */
    public Student updateGrade(long pIdentifier, Grade grade) {
    	Integer index = pIdentifierIndices.find(pIdentifier);
    	if (index == null) {
    		return null;
    	}
    	
    	Student student = getStudent(index);
        pGradeIndices.remove(student.getPercentageGrade(), index);
        pGradeIndices.insert(grade.getPercentage(), index);
        Grader.setGrade(student, grade);
        return student;
    }
    
    /**
     * Updates the percentage grade of the student (associated with the
     * specified personal identifier (PID)) with the specified percentage
     * grade if the student is enrolled in the section.
     * 
     * @param pIdentifier a student's PID
     * @param pGrade a percentage grade for the course
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the section.
     */
    public Student updatePercentageGrade(long pIdentifier, int pGrade) {
    	Integer index = pIdentifierIndices.find(pIdentifier);
    	if (index == null) {
    		return null;
    	}
    	
    	Student student = getStudent(index);
        pGradeIndices.remove(student.getPercentageGrade(), index);
        pGradeIndices.insert(pGrade, index);
        Grader.setPercentageGrade(student, pGrade);
        return student;
    }
    
    /**
     * Updates the letter grades of all students in the section to correspond
     * with their percentage grades.
     */
    public void updateLetterGrades() {
    	for (Student student : getStudents()) {
    		Grader.setLetterGrade(student);
    	}
    }
    
    /**
     * Unenrolls the student associated with the specified personal
     * identifier (PID) from the section if the student is enrolled in the
     * section.
     * 
     * @param pIdentifier a student's PID
     * @return the student associated with the PID, or null if the student is
     * not enrolled in the section.
     */
    public Student remove(long pIdentifier) {
        Integer index = pIdentifierIndices.remove(pIdentifier);
        if (index == null) {
        	return null;
        }
        
        Enrollment enrollment = enrollments.get(index);
        enrollment.remove();
        
        Student student = enrollment.getStudent();
        fNameIndices.remove(student.getFullName(), index);
        pGradeIndices.remove(student.getPercentageGrade(), index);
        return student;
    }
    
    /**
     * Unenrolls the student associated with the specified full name from the
     * section if only one such student is enrolled in the section.
     * 
     * @param fName a student's full name
     * @return the student associated with the full name, or null if the
     * student is not enrolled in the section or more than one student
     * associated with the full name is in the section.
     */
    public Student remove(FullName fName) {
    	List<Student> students = find(fName);
    	if (students.size() != 1) {
    		return null;
    	}
    	
        Integer index = fNameIndices.remove(fName);
        
        Enrollment enrollment = enrollments.get(index);
        enrollment.remove();
        
        Student student = enrollment.getStudent();
        pIdentifierIndices.remove(student.getPersonalID());
        pGradeIndices.remove(student.getPercentageGrade(), index);
        return student;
    }
    
    /**
     * Unenrolls all students from the section.
     */
    public void clear() {
    	for (int index : pIdentifierIndices) {
    		enrollments.get(index).remove();
    	}
    	pIdentifierIndices.clear();
        fNameIndices.clear();
        pGradeIndices.clear();
        section.setModifiable(true); // TODO maybe // Comment or move
    }
    
    public List<Student> listInPersonalIDOrder() {
    	return listInOrder(pIdentifierIndices.iterator());
    }
    
    public List<Student> listInFullNameOrder() {
    	return listInOrder(fNameIndices.iterator());
    }
    
    public List<Student> listInPercentageGradeOrder() {
    	return listInOrder(pGradeIndices.iterator());
    }
    
    private List<Student> listInOrder(Iterator<Integer> i) {
    	List<Student> students = new ArrayList<Student>();
    	while (i.hasNext()) {
    		students.add(getStudent(i.next()));
    	}
    	return students;
    }
    
    // TODO // Move Grader method call to CmdEvaluator
    public List<String> listGradeLevelStats() {
    	return Grader.listGradeLevelStats(getStudents());
    }
    
    public List<Student> listInGradeLevel(String lGrade) {
        List<Student> students = new ArrayList<Student>();
        int lBound = Grader.getPercentageGradeLB(lGrade);
        int uBound = Grader.getPercentageGradeUB(lGrade);
        for (Integer index : pGradeIndices.findRange(lBound, uBound)) {
        	students.add(getStudent(index));
        }
        return students;
    }
    
    // TODO maybe // Try to make return type hold a student/students
    public List<String> listStudentPairsWithin(int pGradeDiff) {
        List<String> pairsWithinDiff = new ArrayList<String>();
        int i = 0;
        for (Iterator<Integer> j = pGradeIndices.iterator(); j.hasNext();) {
        	Student first = getStudent(j.next());
        	for (Iterator<Integer> k = initPGIndexDbIterator(++i); k.hasNext();) {
            	Student second = getStudent(k.next());
            	int diff = Math.abs(first.getPercentageGrade() - second.getPercentageGrade());
                if (diff <= pGradeDiff) {
                	pairsWithinDiff.add(first.getFullName() + ", " + second.getFullName());
                }
            }
        }
        return pairsWithinDiff;
    }
    
    private Iterator<Integer> initPGIndexDbIterator(int pos) {
    	Iterator<Integer> i = pGradeIndices.iterator();
    	for (int j = 0; (j < pos) && i.hasNext(); ++j) {
    		i.next();
    	}
    	return i;
    }
}
