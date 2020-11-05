package course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import bst.BST;
import data.FullName;
import data.SectionEnrollment;
import data.Student;

/**
 * Section Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class Section2 {
    private BST<Long, Integer> idIndexDB;
    private BST<FullName, Integer> fnIndexDB;
    private BST<Integer, Integer> pgIndexDB;
    private ArrayList<Student> studentDB;
    
    private int number;
    private boolean isActive; // Active State
    
    Section2(int sectionNumber) {
    	idIndexDB = new BST<Long, Integer>();
        fnIndexDB = new BST<FullName, Integer>();
        pgIndexDB = new BST<Integer, Integer>();
        studentDB = new ArrayList<Student>();
        
        number = sectionNumber;
        isActive = true;
    }
    
    public int getNumber() {
        return number;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        this.isActive = active;
    }
    
    public boolean hasEnrollment() {
    	return (isActive && (idIndexDB.size() != 0));
    }
    
    /**
     * Get the size of the Section
     * 
     * @return size
     */
    public int size() {
        return idIndexDB.size();
    }
    
    /**
     * Check if the Section is empty
     * 
     * @return TRUE if the Section
     * is empty, FALSE otherwise
     */
    public boolean isEmpty() {
        return (idIndexDB.size() == 0);
    }
    
    public void insert(Student student) {
    	int index = studentDB.size();
    	idIndexDB.insert(student.getPersonalID(), index);
    	fnIndexDB.insert(student.getFullName(), index);
    	pgIndexDB.insert(student.getPercentageGrade(), index);
    	studentDB.add(student);
    }
    
    public Student findStudent(long personalID) {
    	Student student = null;
    	Integer index = idIndexDB.find(personalID);
    	if (index != null) {
    		student = studentDB.get(index);
    	}
    	return student;
    }
    
    public Student[] findStudents(FullName fullName) {
    	ArrayList<Student> withFullName = new ArrayList<Student>();
    	int[] indices = toIntArray(fnIndexDB.findall(fullName));
    	for (int i = 0; i < indices.length; i++) {
    		withFullName.add(studentDB.get(indices[i]));
    	}
    	return toStudentArray(withFullName);
    }
    
    private static int[] toIntArray(ArrayList<Integer> oldIndices) {
        int[] indices = new int[oldIndices.size()];
        for (int i = 0; i < indices.length; i++) {
        	indices[i] = oldIndices.get(i).intValue();
        }
        return indices;
    }
    
    private Student[] toStudentArray(ArrayList<Student> oldStudents) {
        Student[] students = new Student[oldStudents.size()];
        students = oldStudents.toArray(students);
        return students;
    }
    
    public int[] findIndices(FullName fullName) {
        return toIntArray(fnIndexDB.findall(fullName));
    }
    
    public Student[] findStudents(String name) {
        ArrayList<Student> withName = new ArrayList<Student>();
        Iterator<Integer> it = fnIndexDB.iterator();
        while (it.hasNext()) {
            int index = it.next();
            Student student = studentDB.get(index);
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	withName.add(student);
            }
        }
        return toStudentArray(withName);
    }
    
    public void setGrade(Student student) {
    	int index = idIndexDB.find(student.getPersonalID());
    	Student oldStudent = studentDB.get(index);
        pgIndexDB.remove(oldStudent.getPercentageGrade(), index);
        pgIndexDB.insert(student.getPercentageGrade(), index);
        oldStudent.setGrade(student.getGrade());
    }
    
    public void score(Student student, int percentageGrade) {
    	int index = idIndexDB.find(student.getPersonalID());
        pgIndexDB.remove(student.getPercentageGrade(), index);
        pgIndexDB.insert(percentageGrade, index);
        student.setPercentageGrade(percentageGrade);
        studentDB.get(index).setGrade(student.getGrade());
    }
    
    public Student removeStudent(long personalID) {
        int index = idIndexDB.remove(personalID);
        Student student = studentDB.get(index);
        fnIndexDB.remove(student.getFullName(), index);
        pgIndexDB.remove(student.getPercentageGrade(), index);
        student.clrActive();
        return student;
    }
    
    public Student removeStudent(FullName fullName) {
        int index = fnIndexDB.remove(fullName);
        Student student = studentDB.get(index);
        idIndexDB.remove(student.getPersonalID());
        pgIndexDB.remove(student.getPercentageGrade(), index);
        student.clrActive();
        return student;
    }
    
    public void clear() {
    	idIndexDB.clear();
        fnIndexDB.clear();
        pgIndexDB.clear();
        // studentDB TODO
        isActive = true;
    }
    
    public void printStudentsByPersonalID() {
    	printStudents(idIndexDB.iterator());
    }
    
    public void printStudentsByFullName() {
    	printStudents(fnIndexDB.iterator());
    }
    
    public void printStudentsByPercentageGrade() {
    	printStudents(pgIndexDB.iterator());
    }
    
    public void printStudents(Iterator<Integer> it) {
    	while (it.hasNext()) {
    		System.out.println(studentDB.get(it.next()));
    	}
    }
    
    public void gradeAllStudents() {
    	Grader.gradeStudents(getStudents());
    }
    
    public Student[] getStudents() {
		Student[] students = new Student[idIndexDB.size()];
		Arrays.fill(students, null);
		Iterator<Integer> it = idIndexDB.iterator();
		int i = 0;
		while (it.hasNext()) {
			students[i++] = studentDB.get(it.next());
		}
		return students;
    }
    
    public void statAllStudents() {
    	Grader.statStudents(getStudents());
    }
    
    public Student[] listAllStudents(String letter) {
        ArrayList<Student> students = new ArrayList<Student>();
        int lower = Grader.getLowerPercentageGrade(letter);
        int upper = Grader.getUpperPercentageGrade(letter);
        ArrayList<Integer> indices = pgIndexDB.findrange(lower, upper);
        for (int i = 0; i < indices.size(); i++) {
        	students.add(studentDB.get(indices.get(i)));
        }
        return toStudentArray(students);
    }
    
    public ArrayList<String> findStudentPairs(int scorePercentageDiff) {
        ArrayList<String> studentPairsWithinDiff = new ArrayList<String>();
        int studentsToSkip = 0;
        Iterator<Integer> itOuter = pgIndexDB.iterator();
        while (itOuter.hasNext()) {
        	Student first = studentDB.get(itOuter.next());
        	Iterator<Integer> itInner = pgIndexDB.iterator();
            skipStudents(itInner, ++studentsToSkip);
            while (itInner.hasNext()) {
            	Student second = studentDB.get(itInner.next());
            	int diff = Math.abs(first.getPercentageGrade() - second.getPercentageGrade());
                if (diff <= scorePercentageDiff) {
                	studentPairsWithinDiff.add(first.getFullName() + ", " + second.getFullName());
                }
            }
        }
        return studentPairsWithinDiff;
    }
    
    private void skipStudents(Iterator<Integer> it, int studentsToSkip) {
    	for (int i = 0; i < studentsToSkip; i++) {
    		if (it.hasNext()) {
    			it.next();
    		}
    	}
    }
    
    public SectionEnrollment getEnrollment() {
    	return new SectionEnrollment(number, getStudents());
    }
}
