package course;

import java.util.ArrayList;
import java.util.Iterator;

import bst.BST;
import student.*;

/** 
 * Section Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-12
 */
public class Section {
    private BST<FullName, Student> studentDB;
    private int studentInsertOrder;
    private int number;

    public Section(int sectionNumber) {
    	studentDB = new BST<FullName, Student>();
    	studentInsertOrder = 0;
        number = sectionNumber;
    }

    public int getNumber() {
        return number;
    }

    public Student insertStudent(String firstName, String lastName) {
        FullName fullName = new FullName(firstName, lastName);
        Student student = new Student(fullName, newStudentIDNumber());
        studentDB.insert(fullName, student);
        return studentDB.find(fullName);
    }
    
    private String newStudentIDNumber() {
        return String.format("%02d%04d", number, ++studentInsertOrder);
    }

    public Student findStudent(String firstName, String lastName) {
        return studentDB.find(new FullName(firstName, lastName));
    }
    
    public Student[] findStudents(String name) {
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        Iterator<Student> it = studentDB.iterator();
        while (it.hasNext()) {
            Student student = it.next();
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	studentsWithName.add(student);
            }
        }
        return toStudentArray(studentsWithName);
    }
    
    private Student[] toStudentArray(ArrayList<Student> oldStudents) {
        Student[] students = new Student[oldStudents.size()];
        students = oldStudents.toArray(students);
        return students;
    }

    public void score(Student student, int scorePercentage) {
        student.setScorePercentage(scorePercentage);
    }

    public Student removeStudent(String firstName, String lastName) {
        return studentDB.remove(new FullName(firstName, lastName));
    }
    
    public void removeAllStudents() {
    	studentDB.clear();
        studentInsertOrder = 0;
    }
    
    public int dump() {
    	studentDB.inorder();
        return studentDB.size();
    }
    
    public void gradeAllStudents() {
    	Iterator<Student> it = studentDB.iterator();
    	Grader.gradeStudents(it);
    }
    
    public StudentPair[] findStudentPairs(int scorePercentageDiff) {
        ArrayList<StudentPair> studentPairsWithinDiff = new ArrayList<StudentPair>();
        int studentsToSkip = 0;
        Iterator<Student> itOuter = studentDB.iterator();
        while (itOuter.hasNext()) {
        	Student first = itOuter.next();
        	Iterator<Student> itInner = studentDB.iterator();
            skipStudents(itInner, ++studentsToSkip);
            while (itInner.hasNext()) {
            	StudentPair studentPair = new StudentPair(first, itInner.next());
                if (studentPair.scorePercentageDiff() <= scorePercentageDiff) {
                	studentPairsWithinDiff.add(studentPair);
                }
            }
        }
        return toStudentPairArray(studentPairsWithinDiff);
    }
    
    private void skipStudents(Iterator<Student> it, int studentsToSkip) {
    	for (int i = 0; i < studentsToSkip; i++) {
    		if (it.hasNext()) {
    			it.next();
    		}
    	}
    }
    
    private StudentPair[] toStudentPairArray(ArrayList<StudentPair> oldStudentPairs) {
        StudentPair[] studentPairs = new StudentPair[oldStudentPairs.size()];
        studentPairs = oldStudentPairs.toArray(studentPairs);
        return studentPairs;
    }
}
