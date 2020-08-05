package project1;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * CourseSection Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-04
 */
public class CourseSection {
    private BST<FullName, Student> studentDB;
    private int studentInsertOrder;
    private int number;

    public CourseSection(int sectionNumber) {
    	studentDB = new BST<FullName, Student>();
    	studentInsertOrder = 1;
        number = sectionNumber;
    }

    public int getNumber() {
        return number;
    }

    public Student insertStudent(String firstName, String lastName) {
        FullName name = new FullName(firstName, lastName);
        Student student = new Student(name, newID());
        studentDB.insert(name, student);
        studentInsertOrder++;
        return studentDB.find(name);
    }

    /**
     * Returns a new ID for a Record
     * 
     * @return id
     */
    private String newID() {
        int id = (number * 10000) + studentInsertOrder;
        return String.format("%06d", id);
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
        studentInsertOrder = 1;
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
        int studentsToSkip = 1;
        
        ArrayList<StudentPair> studentsWithDiff = new ArrayList<StudentPair>();
        
        Iterator<Student> itOuter = studentDB.iterator();
        while (itOuter.hasNext()) {
        	Student first = itOuter.next();
        	
        	Iterator<Student> itInner = studentDB.iterator();
        	
            skipStudents(itInner, studentsToSkip);
            
            while (itInner.hasNext()) {
            	Student second = itInner.next();
                int diff = Math.abs(first.getScore() - second.getScore());
                if ((diff <= scorePercentDiff)) {
                    studentsWithDiff.add(new StudentPair(first, second));
                }
            }
            studentsToSkip++;
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
}
