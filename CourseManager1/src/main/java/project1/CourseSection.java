package project1;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * CourseSection Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-10
 */
public class CourseSection {
    private BST<FullName, Student> studentDB;
    private int studentInsertOrder;
    private int sectionNumber;

    public CourseSection(int sectionNumber) {
    	studentDB = new BST<FullName, Student>();
    	studentInsertOrder = 0;
        this.sectionNumber = sectionNumber;
    }

    public int getNumber() {
        return sectionNumber;
    }

    public Student insertStudent(String firstName, String lastName) {
        FullName name = new FullName(firstName, lastName);
        Student student = new Student(name, newStudentIDsectionNumber());
        studentDB.insert(name, student);
        return studentDB.find(name);
    }
    
    /**
     * Generates a unique six-digit student ID where
     * the first two digits indicate the section number
     * and the last four digits represent the order
     * that a student was inserted starting from 0001
     * 
     * @return unique six-digit student ID string
     */
    private String newStudentIDsectionNumber() {
        return String.format("%02d%04d", sectionNumber, ++studentInsertOrder);
    }

    public Student findStudent(String firstName, String lastName) {
        return studentDB.find(new FullName(firstName, lastName));
    }
    
    public Student[] findStudents(String name) {
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        Iterator<Student> it = studentDB.iterator();
        while (it.hasNext()) {
            Student student = it.next();
            if (containsName(student.getFullName(), name)) {
            	studentsWithName.add(student);
            }
        }
        return toStudentArray(studentsWithName);
    }
    
    /**
     * Checks whether or not someone's first name
     * and/or last name matches a name disregarding
     * letter case
     * 
     * @param fullName someone's first and last name
     * @param name name string being compared to someone's
     * full name
     * @return true if someone's first name and/or
     * last name equals a name disregarding letter case
     */
    private boolean containsName(FullName fullName, String name) {
    	String firstName = fullName.getFirstName();
    	String lastName = fullName.getLastName();
    	return name.equalsIgnoreCase(firstName) || name.equalsIgnoreCase(lastName);
    }
    
    /**
     * Converts an ArrayList of Students to an array
     * of Students
     * 
     * @param oldStudents ArrayList of Students being converted
     * @return array of Students
     */
    private Student[] toStudentArray(ArrayList<Student> oldStudents) {
        Student[] students = new Student[oldStudents.size()];
        students = oldStudents.toArray(students);
        return students;
    }

    public void score(Student student, int scorePercent) {
        student.setScore(scorePercent);
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
    	CourseGrader.gradeStudents(it);
    }
    
    public StudentPair[] findStudentPairs(int scorePercentDiff) {
        ArrayList<StudentPair> studentPairsWithinDiff = new ArrayList<StudentPair>();
        int studentsToSkip = 0;
        Iterator<Student> itOuter = studentDB.iterator();
        while (itOuter.hasNext()) {
        	Student first = itOuter.next();
        	Iterator<Student> itInner = studentDB.iterator();
            skipStudents(itInner, ++studentsToSkip);
            while (itInner.hasNext()) {
            	StudentPair studentPair = new StudentPair(first, itInner.next());
                if (isWithinDifference(studentPair, scorePercentDiff)) {
                	studentPairsWithinDiff.add(studentPair);
                }
            }
        }
        return toStudentPairArray(studentPairsWithinDiff);
    }
    
    /**
     * Advances a Student Iterator by a specified number
     * of Students
     * 
     * @param it iterator to be advanced
     * @param studentsToSkip number of students to advance iterator
     */
    private void skipStudents(Iterator<Student> it, int studentsToSkip) {
    	for (int i = 0; i < studentsToSkip; i++) {
    		if (it.hasNext()) {
    			it.next();
    		}
    	}
    }
    
    /**
     * Checks whether or not a pair of Students'
     * scores are within a specified percent difference
     * 
     * @param studentPair pair of students
     * @param scorePercentDiff score difference to compare against the
     * score difference of the student pair
     * @return true if the student score difference is
     * within the specified score difference
     */
    private boolean isWithinDifference(StudentPair studentPair, int scorePercentDiff) {
    	Student first = studentPair.getFirst();
    	Student second = studentPair.getSecond();
    	int diff = Math.abs(first.getScore() - second.getScore());
    	return diff <= scorePercentDiff;
    }
    
    /**
     * Converts an ArrayList of StudentPairs to an array
     * of StudentPairs
     * 
     * @param oldStudentPairs ArrayList of StudentPairs being converted
     * @return array of StudentPairs
     */
    private StudentPair[] toStudentPairArray(ArrayList<StudentPair> oldStudentPairs) {
        StudentPair[] studentPairs = new StudentPair[oldStudentPairs.size()];
        studentPairs = oldStudentPairs.toArray(studentPairs);
        return studentPairs;
    }
}
