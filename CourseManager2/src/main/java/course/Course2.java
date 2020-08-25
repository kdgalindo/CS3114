package course;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import bst.BST;
import student.FullName;
import student.Student;

/**
 * Course Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class Course2 {
	private final int MAX_SECTION_NUMBER = 21;
    private Section2[] sections;
    private Section2 currentSection;
    private ArrayList<Student> students;
    private BST<Long, Integer> pidSectionDB;
    private Integer currentStudentIndex; // index of gradable student record

    /**
     * CourseManager default constructor
     */
    public Course2() {
        sections = new Section2[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new Section2(sectionNumber);
        }
        currentSection = sections[0];
        pidSectionDB = new BST<Long, Integer>();
        students = new ArrayList<Student>();
        currentStudentIndex = null;
    }

    public int getSection() {
        return currentSection.getNumber();
    }
    
    public boolean isSectionActive() {
        return currentSection.isActive();
    }

    public boolean isStudentGradable() {
        return (currentStudentIndex != null);
    }
    
    private void clrStudentGradable() {
    	currentStudentIndex = null;
    }

    public void setSection(int sectionNumber) {
    	clrStudentGradable();
        if (isValidSection(sectionNumber)) {
        	currentSection = sections[sectionNumber - 1];
        }
    }
    
    public boolean isValidSection(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    private void setStudentGradable(int index) {
    	currentStudentIndex = index;
    }
    
    public Student insert(Student student) {
    	clrStudentGradable();
        long personalID = student.getPersonalID();
        if (findStudentSection(personalID) == null) { // Check if in section
            insertHelper(student, students.size()); // Add to section
            pidSectionDB.insert(personalID, currentSection.getNumber()); // Associate with section
            students.add(student); // Add to student record list
            setStudentGradable(students.size() - 1);
            return student;
        }
        return null;
    }
    
    public Integer findStudentSection(long personalID) {
        return pidSectionDB.find(personalID);
    }
    
    private void insertHelper(Student student, int index) {
    	currentSection.insert(student.getPersonalID(), index);
    	currentSection.insert(student.getFullName(), index);
    	currentSection.insert(student.getPercentageGrade(), index);
    }
    
    public Student findStudent(long personalID) {
    	clrStudentGradable();
        Student student = null;
        Integer index = currentSection.findIndex(personalID);
        if (index != null) { // Check if in section
        	student = students.get(index);
        	setStudentGradable(index);
        }
        return student;
    }
    
    public Student[] findStudents(FullName fullName) {
    	clrStudentGradable();
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        int[] indices = currentSection.findIndices(fullName);
        for (int i = 0; i < indices.length; i++) {
        	studentsWithName.add(students.get(indices[i]));
        }
        if (indices.length == 1) {
        	setStudentGradable(indices[0]);
        }
        return toStudentArray(studentsWithName);
    }
    
    private Student[] toStudentArray(ArrayList<Student> oldStudents) {
        Student[] students = new Student[oldStudents.size()];
        students = oldStudents.toArray(students);
        return students;
    }
    
    public Student[] findStudents(String name) {
    	clrStudentGradable();
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        Iterator<Integer> it = currentSection.studentFNIndexIterator();
        while (it.hasNext()) {
            int index = it.next();
            Student student = students.get(index);
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	studentsWithName.add(student);
                if (studentsWithName.size() == 1) {
                    currentStudentIndex = index;
                }
            }
        }
        if (studentsWithName.size() != 1) {
        	clrStudentGradable();
        }
        return toStudentArray(studentsWithName);
    }
    
    public Student scoreStudent(int percentage) {
        Student student = null;
        if (currentStudentIndex != null) {
        	student = students.get(currentStudentIndex);
            currentSection.updateStudentScore(student.getPercentageGrade(), percentage, currentStudentIndex);
            student.setPercentageGrade(percentage);
            clrStudentGradable();
        }
        return student;
    }
    
    public static boolean isValidPercentageGrade(int scorePercentage) {
    	return ((scorePercentage > -1) && (scorePercentage < 101));
    }
    
    public Student removeStudent(long personalID) {
    	clrStudentGradable();
        Student student = null;
        Integer sectionNumber = pidSectionDB.find(personalID);
        if ((sectionNumber != null) && (sectionNumber == currentSection.getNumber())) {
            int index = currentSection.remove(personalID);
            student = students.get(index);
            currentSection.remove(student.getFullName(), index);
            currentSection.remove(student.getPercentageGrade(), index);
            pidSectionDB.remove(personalID); // Disassociate with section
            student.clrActive(); // Kill the student record
        }
        return student;
    }
    
    public Student removeStudent(FullName fullName) {
    	clrStudentGradable();
        Student student = null;
        int[] indices = currentSection.findIndices(fullName);
        if (indices.length == 1) { // Check if name unique
            int index = currentSection.remove(fullName);
            student = students.get(index);
            currentSection.remove(student.getPersonalID());
            currentSection.remove(student.getPercentageGrade(), index);
            pidSectionDB.remove(student.getPersonalID()); // Disassociate with section
            student.clrActive(); // Kill the student record
        }
        return student;
    }

    /**
     * Clears the current section
     * from CourseManager
     */
    public void clearsection() {
    	clrStudentGradable();
        currentSection.clear();
    }
    
    public int dumpPIDs() {
    	clrStudentGradable();
        Iterator<Integer> itr = currentSection.studentPIDIndexIterator();
        while (itr.hasNext()) {
        	Student sr = students.get(itr.next());
            System.out.println(sr);
        }
        return currentSection.size();
    }
    
    public int dumpNames() {
        currentStudentIndex = null;
        Iterator<Integer> itr = currentSection.studentFNIndexIterator();
        while (itr.hasNext()) {
        	Student sr = students.get(itr.next());
            System.out.println(sr);
        }
        return currentSection.size();
    }
    
    public int dumpScores() {
        currentStudentIndex = null;
        Iterator<Integer> itr = currentSection.studentPGIndexIterator();
        while (itr.hasNext()) {
        	Student sr = students.get(itr.next());
            System.out.println(sr);
        }
        return currentSection.size();
    }
    
    public void gradeStudents() {
        currentStudentIndex = null;
        Grader.gradeStudents(toStudentArray());
    }
    
    private Student[] toStudentArray() {
		Student[] newStudents = new Student[currentSection.size()];
		Arrays.fill(newStudents, null);
		Iterator<Integer> it = currentSection.studentPGIndexIterator();
		int i = 0;
		while (it.hasNext()) {
			newStudents[i++] = students.get(it.next());
		}
		return newStudents;
    }
    
    public void statStudents() {
        currentStudentIndex = null;
        Grader.statStudents(toStudentArray());
    }
    
    public Student[] listStudents(String letter) {
        currentStudentIndex = null;
        ArrayList<Student> records = new ArrayList<Student>();
        int lower = Grader.getLowerPercentageGrade(letter);
        int upper = Grader.getUpperPercentageGrade(letter);
        ArrayList<Integer> indices = currentSection.searchForScoresInRange(lower, upper);
        for (int i = 0; i < indices.size(); i++) {
        	records.add(students.get(indices.get(i)));
        }
        return toStudentArray(records);
    }
    
    public ArrayList<String> findpair(int scorePercentageDiff) {
        ArrayList<String> studentPairsWithinDiff = new ArrayList<String>();
        int studentsToSkip = 0;
        Iterator<Integer> itOuter = currentSection.studentPGIndexIterator();
        while (itOuter.hasNext()) {
        	Student first = students.get(itOuter.next());
        	Iterator<Integer> itInner = currentSection.studentPGIndexIterator();
            skipStudents(itInner, ++studentsToSkip);
            while (itInner.hasNext()) {
            	Student second = students.get(itInner.next());
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
    
    public boolean mergeSections() {
        currentStudentIndex = null;
        boolean result = false;
        if (currentSection.isEmpty()) {
            currentSection.setActive(false);
            for (int i = 0; i < sections.length; i++) {
                if (sections[i].isActive()) {
                    Iterator<Integer> it = sections[i].studentPIDIndexIterator();
                    if (!sections[i].isEmpty()) {
                        while (it.hasNext()) {
                            int index = it.next();
                            Student student = students.get(index);
                            insertHelper(student, index);
                        }
                    }
                }
            }
            result = true;
        }
        return result;
    }
}
