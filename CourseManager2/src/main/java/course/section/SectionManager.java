package course.section;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import grade.*;
import identity.FullName;
import student.Student;
import util.BST;

/** 
 * SectionManager Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-08
 */
public class SectionManager {
    private Section section;
    private BST<Long, Integer> idIndexDb;
    private BST<FullName, Integer> fnIndexDb;
    private BST<Integer, Integer> pgIndexDb;
    private ArrayList<Student> studentDb;
    
    public SectionManager(Section section) {
        this.section = section;
    	idIndexDb = new BST<Long, Integer>();
        fnIndexDb = new BST<FullName, Integer>();
        pgIndexDb = new BST<Integer, Integer>();
        studentDb = new ArrayList<Student>();
    }
    
    public Section getSection() {
    	return section;
    }
    
    public SectionEnrollment getEnrollment() {
    	return new SectionEnrollment(section.getNumber(), getStudents());
    }
    
    public Student[] getStudents() { // TODO
		Student[] students = new Student[idIndexDb.size()];
		Arrays.fill(students, null);
		Iterator<Integer> it = idIndexDb.iterator();
		int i = 0;
		while (it.hasNext()) {
			students[i++] = studentDb.get(it.next());
		}
		return students;
    }
    
    public boolean hasEnrollment() { // TODO
    	return section.isModifiable() && (idIndexDb.size() != 0);
    }
    
    public boolean isEmpty() {
        return idIndexDb.size() == 0;
    }
    
    public int size() {
        return idIndexDb.size();
    }
    
    /**
     * 
     * @param personalID Student PID
     * @return
     */
    public Student find(long personalID) {
    	Integer index = idIndexDb.find(personalID);
    	if (index == null) {
    		return null;
    	}
    	
    	return studentDb.get(index);
    }
    
    /**
     * 
     * @param fullName Student full name
     * @return
     */
    public List<Student> find(FullName fullName) {
    	List<Student> students = new ArrayList<Student>();
    	for (Integer index : fnIndexDb.findAll(fullName)) {
    		students.add(studentDb.get(index));
    	}
    	return students;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public List<Student> find(String name) {
        List<Student> students = new ArrayList<Student>();
        for (Iterator<Integer> i = fnIndexDb.iterator(); i.hasNext();) {
            Student student = studentDb.get(i.next());
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) { // TODO
            	students.add(student);
            }
        }
        return students;
    }
    
    /**
     * 
     * @param student Student
     */
    public void insert(Student student) {
    	int index = studentDb.size();
    	idIndexDb.insert(student.getPersonalID(), index);
    	fnIndexDb.insert(student.getFullName(), index);
    	pgIndexDb.insert(student.getPercentageGrade(), index);
    	studentDb.add(student);
    }
    
    /**
     * 
     * @param personalID Student PID
     * @param grade
     * @return
     */
    public Student updateGrade(long personalID, Grade grade) {
    	Integer index = idIndexDb.find(personalID);
    	if (index == null) {
    		return null;
    	}
    	
    	Student student = studentDb.get(index);
        pgIndexDb.remove(student.getPercentageGrade(), index);
        pgIndexDb.insert(grade.getPercentage(), index);
        Grader.setGrade(student, grade);
        return student;
    }
    
    /**
     * 
     */
    public void updateGrades() {
    	for (Student student : getActiveStudents()) {
    		Grader.setLetterGrade(student);
    	}
    }
    
    private List<Student> getActiveStudents() {
    	List<Student> students = new ArrayList<Student>();
    	for (Student student : studentDb) {
    		if (student.isActive()) {
    			students.add(student);
    		}
    	}
    	return students;
    }
    
    /**
     * 
     * @param personalID
     * @param pGrade
     * @return
     */
    public Student updatePercentageGrade(long personalID, int pGrade) {
    	Integer index = idIndexDb.find(personalID);
    	if (index == null) {
    		return null;
    	}
    	
    	Student student = studentDb.get(index);
        pgIndexDb.remove(student.getPercentageGrade(), index);
        pgIndexDb.insert(pGrade, index);
        Grader.setPercentageGrade(student, pGrade);
        return student;
    }
    
    /**
     * 
     * @param personalID
     * @return
     */
    public Student remove(long personalID) {
        Integer index = idIndexDb.remove(personalID);
        if (index == null) {
        	return null;
        }
        
        Student student = studentDb.get(index);
        fnIndexDb.remove(student.getFullName(), index);
        pgIndexDb.remove(student.getPercentageGrade(), index);
        student.clrActive();
        return student;
    }
    
    /**
     * 
     * @param fullName
     * @return
     */
    public Student remove(FullName fullName) {
    	List<Student> students = find(fullName);
    	if (students.size() != 1) {
    		return null;
    	}
    	
        Integer index = fnIndexDb.remove(fullName);
        
        Student student = studentDb.get(index);
        idIndexDb.remove(student.getPersonalID());
        pgIndexDb.remove(student.getPercentageGrade(), index);
        student.clrActive();
        return student;
    }
    
    /**
     * 
     */
    public void clear() {
    	idIndexDb.clear();
        fnIndexDb.clear();
        pgIndexDb.clear();
        // studentDb TODO
        section.setModifiable(true);
    }
    
    public void printStudentsByPersonalID() {
    	printStudents(idIndexDb.iterator());
    }
    
    public void printStudentsByFullName() {
    	printStudents(fnIndexDb.iterator());
    }
    
    public void printStudentsByPercentageGrade() {
    	printStudents(pgIndexDb.iterator());
    }
    
    public void printStudents(Iterator<Integer> i) {
    	while (i.hasNext()) {
    		System.out.println(studentDb.get(i.next()));
    	}
    }
    
    /**
     * 
     * @return
     */
    public List<String> listGradeLevelStats() {
    	return Grader.listGradeLevelStats(getActiveStudents());
    }
    
    /**
     * 
     * @param lGradeLevel Letter grade level
     * @return
     */
    public List<Student> listStudentsIn(String lGradeLevel) {
        List<Student> students = new ArrayList<Student>();
        int lower = Grader.getPercentageGradeLB(lGradeLevel);
        int upper = Grader.getPercentageGradeUB(lGradeLevel);
        for (Integer index : pgIndexDb.findRange(lower, upper)) {
        	students.add(studentDb.get(index));
        }
        return students;
    }
    
    /**
     * 
     * @param pGradeDiff
     * @return
     */
    public List<String> listStudentPairsWithin(int pGradeDiff) {
        List<String> pairsWithinDiff = new ArrayList<String>();
        int i = 0;
        for (Iterator<Integer> j = pgIndexDb.iterator(); j.hasNext();) {
        	Student first = studentDb.get(j.next());
        	for (Iterator<Integer> k = initPGIndexDbIterator(++i); k.hasNext();) {
            	Student second = studentDb.get(k.next());
            	int diff = Math.abs(first.getPercentageGrade() - second.getPercentageGrade());
                if (diff <= pGradeDiff) {
                	pairsWithinDiff.add(first.getFullName() + ", " + second.getFullName());
                }
            }
        }
        return pairsWithinDiff;
    }
    
    private Iterator<Integer> initPGIndexDbIterator(int pos) {
    	Iterator<Integer> i = pgIndexDb.iterator();
    	for (int j = 0; (j < pos) && i.hasNext(); ++j) {
    		i.next();
    	}
    	return i;
    }
}
