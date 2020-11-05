package course;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import bin.BinFileHelper;
import bst.BST;
import data.FullName;
import data.Student;

/**
 * CourseManager Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class CourseManager {
	private final int MAX_SECTION_NUMBER = 21;
    private Section[] sections;
    private Section currentSection;
    private ArrayList<Student> students;
    private BST<Long, Integer> pidSectionDB;
    private Integer currentStudentIndex; // index of gradable student record

    public CourseManager() {
        sections = new Section[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new Section(sectionNumber);
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

    /**
     * Grade command cannot come after
     * load student data command
     */
    public void loadstudentdata() {
    	clearStudentScorable();
    }
    
    private void clearStudentScorable() {
    	currentStudentIndex = null;
    }
    
    public Student loadCourseData(int sectionNumber, Student newStudent) {
    	clearStudentScorable();
        Student student = null;
        long personalID = newStudent.getPersonalID(); // student pid
        Integer cSectionNumber = findStudentSection(personalID); // current section number
        if (cSectionNumber == null) { // Check if in section
        	student = newStudent;
        	addToActiveSection(student, students.size(), sectionNumber);
            students.add(student); // Add to student record list
        }
        else if (cSectionNumber == sectionNumber) { // Check if in current section
        	student = newStudent;
            int index = sections[sectionNumber - 1].findIndex(student.getPersonalID());
            Student oldStudent = students.get(index);
            sections[sectionNumber - 1].updateStudentScore(oldStudent.getPercentageGrade(),
            		student.getPercentageGrade(), index); // Update score
            oldStudent.setPercentageGrade(student.getPercentageGrade());
            oldStudent.setLetterGrade(student.getLetterGrade()); // Update grade
        }
        return student;
    }
    
    public Integer findStudentSection(long personalID) {
        return pidSectionDB.find(personalID);
    }
    
    private void addToActiveSection(Student student, int index, int sectionNumber) {
    	addToSection(student, index, sectionNumber);
    	pidSectionDB.insert(student.getPersonalID(), sectionNumber);
    }
    
    private void addToSection(Student student, int index, int sectionNumber) {
    	sections[sectionNumber - 1].insert(student.getPersonalID(), index);
    	sections[sectionNumber - 1].insert(student.getFullName(), index);
    	sections[sectionNumber - 1].insert(student.getPercentageGrade(), index);
    }

    /**
     * 
     * @param sectionNumber
     */
    public void setSection(int sectionNumber) {
    	clearStudentScorable();
        if (isValidSection(sectionNumber)) {
        	currentSection = sections[sectionNumber - 1];
        }
    }
    
    public boolean isValidSection(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    private void setStudentScorable(int index) {
    	currentStudentIndex = index;
    }
    
    /**
     * 
     * @param student
     * @return
     */
    public Student insert(Student student) {
    	clearStudentScorable();
        long personalID = student.getPersonalID();
        if (findStudentSection(personalID) == null) { // Check if in section
        	addToActiveSection(student, students.size());
            students.add(student); // Add to student record list
            setStudentScorable(students.size() - 1);
            return student;
        }
        return null;
    }
    
    public void addToActiveSection(Student student, int index) {
    	addToSection(student, index);
    	pidSectionDB.insert(student.getPersonalID(), currentSection.getNumber());
    }
    
    private void addToSection(Student student, int index) {
    	currentSection.insert(student.getPersonalID(), index);
    	currentSection.insert(student.getFullName(), index);
    	currentSection.insert(student.getPercentageGrade(), index);
    }
    
    /**
     * 
     * @param personalID
     * @return
     */
    public Student findStudent(long personalID) {
    	clearStudentScorable();
        Student student = null;
        Integer index = currentSection.findIndex(personalID);
        if (index != null) { // Check if in section
        	student = students.get(index);
            setStudentScorable(index);
        }
        return student;
    }
    
    public Student[] findStudents(FullName fullName) {
    	clearStudentScorable();
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        int[] indices = currentSection.findIndices(fullName);
        for (int i = 0; i < indices.length; i++) {
        	studentsWithName.add(students.get(indices[i]));
        }
        if (indices.length == 1) {
            setStudentScorable(indices[0]);
        }
        return toStudentArray(studentsWithName);
    }
    
    private Student[] toStudentArray(ArrayList<Student> oldStudents) {
        Student[] students = new Student[oldStudents.size()];
        students = oldStudents.toArray(students);
        return students;
    }
    
    public Student[] findStudents(String name) {
    	clearStudentScorable();
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        Iterator<Integer> it = currentSection.studentFNIndexIterator();
        while (it.hasNext()) {
            int index = it.next();
            Student student = students.get(index);
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	studentsWithName.add(student);
                currentStudentIndex = index;
            }
        }
        if (studentsWithName.size() != 1) {
        	clearStudentScorable();
        }
        return toStudentArray(studentsWithName);
    }
    
    public Student scoreStudent(int percentage) {
        Student student = null;
        if (currentStudentIndex != null) {
        	student = students.get(currentStudentIndex);
            currentSection.updateStudentScore(student.getPercentageGrade(), percentage, currentStudentIndex);
            student.setPercentageGrade(percentage);
            clearStudentScorable();
        }
        return student;
    }
    
    public static boolean isValidPercentageGrade(int scorePercentage) {
    	return ((scorePercentage > -1) && (scorePercentage < 101));
    }
    
    public Student removeStudent(long personalID) {
    	clearStudentScorable();
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
    	clearStudentScorable();
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
     * 
     */
    public void clearsection() {
    	clearStudentScorable();
        currentSection.clear();
    }
    
    /**
     * 
     * @return
     */
    public int dumpPIDs() {
    	clearStudentScorable();
    	dump(currentSection.studentPIDIndexIterator());
        return currentSection.size();
    }
    
    public int dumpNames() {
    	clearStudentScorable();
    	dump(currentSection.studentFNIndexIterator());
        return currentSection.size();
    }
    
    public int dumpScores() {
    	clearStudentScorable();
    	dump(currentSection.studentPGIndexIterator());
        return currentSection.size();
    }
    
    private void dump(Iterator<Integer> it) {
    	while (it.hasNext()) {
    		System.out.println(students.get(it.next()));
    
    	}
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
                            addToSection(student, index);
                        }
                    }
                }
            }
            result = true;
        }
        return result;
    }
    
    public byte[] saveCourseData() {
        currentStudentIndex = null;
        byte[] bytes = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BinFileHelper bfh = new BinFileHelper();
            baos.write("CS3114atVT".getBytes());
            int sl = savecoursedatahelper();
            baos.write(bfh.intToByteArray(sl));
            for (int i = 0; i < sl; i++) {
                Iterator<Integer> it = sections[i].studentPIDIndexIterator();
                if (!sections[i].isEmpty() && sections[i].isActive()) {
                    baos.write(bfh.intToByteArray(sections[i].size()));
                    while (it.hasNext()) {
                        int index = it.next();
                        Student student = students.get(index);
                        baos.write(bfh.longToByteArray(student.getPersonalID()));
                        baos.write(student.getFirstName().getBytes());
                        baos.write("$".getBytes());
                        baos.write(student.getLastName().getBytes());
                        baos.write("$".getBytes());
                        baos.write(bfh.intToByteArray(student.getPercentageGrade()));
                        if (student.getLetterGrade().getBytes().length > 1) {
                            baos.write(student.getLetterGrade().getBytes());
                        }
                        else {
                            String g = new String(student.getGrade() + " ");
                            baos.write(g.getBytes());
                        }
                    }
                    baos.write("GOHOKIES".getBytes());
                }
            }
            bytes = baos.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;
    }
    
    /**
     * Helps save all of the active student
     * records in a byte array structured
     * according to the project description
     * by determining the number of the last
     * used section
     * 
     * @return last used section number
     */
    public int savecoursedatahelper() {
        int lsn = 1;
        for (int i = 0; i < sections.length; i++) {
            if (!sections[i].isEmpty() && sections[i].isActive()) {
                if (sections[i].getNumber() > lsn) {
                    lsn = sections[i].getNumber();
                }
            }
        }
        return lsn;
    }

    /**
     * Clears all student records
     * from CourseManager
     */
    public void clearcoursedata() {
        currentStudentIndex = null;
        for (int i = 0; i < 21; i++) {
            sections[i].clear();
        }
        students.clear();
        pidSectionDB.clear();
    }
}
