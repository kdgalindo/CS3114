package course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import data.FullName;
import data.SectionEnrollment;
import data.Student;
import util.BST;

public class SectionManager {
    private int sectionNumber;
    private boolean isSectionActive;
    
    private BST<Long, Integer> idIndexDB;
    private BST<FullName, Integer> fnIndexDB;
    private BST<Integer, Integer> pgIndexDB;
    private ArrayList<Student> studentDB;
    
    public SectionManager(int sectionNumber) {
        this.sectionNumber = sectionNumber;
        this.isSectionActive = true;
        
    	idIndexDB = new BST<Long, Integer>();
        fnIndexDB = new BST<FullName, Integer>();
        pgIndexDB = new BST<Integer, Integer>();
        studentDB = new ArrayList<Student>();
    }
    
    public int getSectionNumber() {
        return sectionNumber;
    }
    
    public boolean isSectionActive() {
        return isSectionActive;
    }
    
    public void setActive(boolean active) {
        this.isSectionActive = active;
    }
    
    public boolean hasEnrollment() {
    	return (isSectionActive && (idIndexDB.size() != 0));
    }
    
    public boolean isEmpty() {
        return idIndexDB.size() == 0;
    }
    
    public int size() {
        return idIndexDB.size();
    }
    
    public Student find(long personalID) {
    	Integer index = idIndexDB.find(personalID);
    	if (index == null) {
    		return null;
    	}
    	
    	return studentDB.get(index);
    }
    
    public Student[] find(FullName fullName) {
    	ArrayList<Student> students = new ArrayList<Student>();
    	for (Integer index : fnIndexDB.findAll(fullName)) {
    		students.add(studentDB.get(index));
    	}
    	return students.toArray(new Student[students.size()]);
    }
    
    public Student[] find(String name) {
        ArrayList<Student> students = new ArrayList<Student>();
        Iterator<Integer> it = fnIndexDB.iterator();
        while (it.hasNext()) {
            int index = it.next();
            Student student = studentDB.get(index);
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	students.add(student);
            }
        }
        return students.toArray(new Student[students.size()]);
    }
    
    public void insert(Student student) {
    	int index = studentDB.size();
    	idIndexDB.insert(student.getPersonalID(), index);
    	fnIndexDB.insert(student.getFullName(), index);
    	pgIndexDB.insert(student.getPercentageGrade(), index);
    	studentDB.add(student);
    }
    
    public Student remove(long personalID) {
        int index = idIndexDB.remove(personalID);
        Student student = studentDB.get(index);
        fnIndexDB.remove(student.getFullName(), index);
        pgIndexDB.remove(student.getPercentageGrade(), index);
        student.clrActive();
        return student;
    }
    
    public Student remove(FullName fullName) {
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
        isSectionActive = true;
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
        int lower = Grader.getPercentageGradeLowerBound(letter);
        int upper = Grader.getPercentageGradeUpperBound(letter);
        for (Integer index : pgIndexDB.findRange(lower, upper)) {
        	students.add(studentDB.get(index));
        }
        return students.toArray(new Student[students.size()]);
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
    	return new SectionEnrollment(sectionNumber, getStudents());
    }
}
