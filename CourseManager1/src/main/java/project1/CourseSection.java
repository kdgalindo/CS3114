package project1;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * CourseSection Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-05
 */
public class CourseSection {
    private BST<FullName, Student> studentDB;
    private int studentInsertOrder;
    private int number;

    public CourseSection(int sectionNumber) {
    	studentDB = new BST<FullName, Student>();
    	studentInsertOrder = 0;
        number = sectionNumber;
    }

    public int getNumber() {
        return number;
    }

    public Student insertStudent(String firstName, String lastName) {
        FullName name = new FullName(firstName, lastName);
        Student student = new Student(name, newStudentIDNumber());
        studentDB.insert(name, student);
        return studentDB.find(name);
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
            if (isNameInFullName(name, student.getFullName())) {
            	studentsWithName.add(student);
            }
        }
        Student[] students = new Student[studentsWithName.size()];
        students = studentsWithName.toArray(students);
        return students;
    }
    
    private boolean isNameInFullName(String name, FullName fullName) {
    	String firstName = fullName.getFirstName();
    	String lastName = fullName.getLastName();
    	return name.equalsIgnoreCase(firstName) || name.equalsIgnoreCase(lastName);
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
    
    public int[] gradeAllStudents() {
    	Iterator<Student> it = studentDB.iterator();
    	return Grader.grade(it);
    }
    
    public StudentPair[] findStudentPairs(int scorePercentDiff) {
        ArrayList<StudentPair> studentsWithDiff = new ArrayList<StudentPair>();
        int studentsToSkip = 1;
        Iterator<Student> itOuter = studentDB.iterator();
        while (itOuter.hasNext()) {
        	Student first = itOuter.next();
        	Iterator<Student> itInner = studentDB.iterator();
            skipStudents(itInner, studentsToSkip++);
            while (itInner.hasNext()) {
            	StudentPair studentPair = new StudentPair(first, itInner.next());
                if (isWithinDifference(studentPair, scorePercentDiff)) {
                    studentsWithDiff.add(studentPair);
                }
            }
        }
        StudentPair[] studentPairs = new StudentPair[studentsWithDiff.size()];
        studentPairs = studentsWithDiff.toArray(studentPairs);
        return studentPairs;
    }
    
    private void skipStudents(Iterator<Student> it, int studentsToSkip) {
    	for (int i = 0; i < studentsToSkip; i++) {
    		if (it.hasNext()) {
    			it.next();
    		}
    	}
    }
    
    private boolean isWithinDifference(StudentPair studentPair, int scorePercentDiff) {
    	Student first = studentPair.getFirst();
    	Student second = studentPair.getSecond();
    	int diff = Math.abs(first.getScore() - second.getScore());
    	return diff <= scorePercentDiff;
    }
}
