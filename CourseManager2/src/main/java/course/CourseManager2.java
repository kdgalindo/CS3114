package course;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import bin.BinFileHelper;
import bst.BST;
import student.FullName;
import student.Student;
import student.StudentRecord;

/**
 * CourseManager Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class CourseManager2 {
	private final int MAX_SECTION_NUMBER = 21;
    private Section2[] sections;
    private Section2 currentSection;
    private ArrayList<StudentRecord> studentRecords;
    private ArrayList<Student> students;
    private BST<Long, Integer> pidSectionDB;
    private Integer gindex; // index of gradable student record

    /**
     * CourseManager default constructor
     */
    public CourseManager2() {
        sections = new Section2[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new Section2(sectionNumber);
        }
        currentSection = sections[0];
        pidSectionDB = new BST<Long, Integer>();
        studentRecords = new ArrayList<StudentRecord>();
        students = new ArrayList<Student>();
        gindex = null;
    }

    public int getSection() {
        return currentSection.getNumber();
    }
    
    public boolean isSectionActive() {
        return currentSection.isActive();
    }

    /**
     * Checks if a student is currently
     * gradable
     * 
     * @return TRUE if a student is gradable,
     * FALSE otherwise
     */
    public boolean isAStudentGradable() {
        return (gindex != null);
    }

    /**
     * Searches for a section number
     * given a student pid
     * 
     * @param p student pid
     * @return section number
     */
    public int searchForSectionByPID(long p) {
        return pidSectionDB.find(p);
    }

    /**
     * Checks if the current section
     * is active; section is active
     * if it is not a merged section
     * 
     * @return TRUE if active, FALSE
     * otherwise
     */
    public boolean isCurrentSectionActive() {
        return currentSection.isActive();
    }

    /**
     * Grade command cannot come after
     * load student data command
     */
    public void loadstudentdata() {
        gindex = null;
    }

    /**
     * Loads student into section sn
     * 
     * @param sn section number
     * @param nsr new student record
     * @return student record if not
     * already in section or in sn,
     * null otherwise
     */
    public StudentRecord loadcoursedata(int sn, StudentRecord nsr) {
        gindex = null;
        StudentRecord sr = null;
        long p = nsr.getPID(); // student pid
        Integer csn = pidSectionDB.find(p); // current section number
        if (csn == null) { // Check if in section
            sr = nsr;
            sections[sn - 1].insertStudent(sr.getStudent(), sr.getScore(),
                studentRecords.size()); // Add to section
            pidSectionDB.insert(p, sn); // Associate with section
            studentRecords.add(sr); // Add to student record list
        }
        else if (csn == sn) { // Check if in current section
            sr = nsr;
            int srli = sections[sn - 1].findStudent(sr.getPID());
            StudentRecord osr = studentRecords.get(srli);
            sections[sn - 1].updateStudentScore(osr.getScore(), osr.setScore(sr
                .getScore()), srli); // Update score
            osr.setGrade(sr.getGrade()); // Update grade
        }
        return sr;
    }

    public void setSection(int sectionNumber) {
        gindex = null;
        if (isValidSection(sectionNumber)) {
        	currentSection = sections[sectionNumber - 1];
        }
    }
    
    public boolean isValidSection(int sectionNumber) {
    	return ((sectionNumber > 0) && (sectionNumber < MAX_SECTION_NUMBER + 1));
    }
    
    public StudentRecord insert(Student student) {
        gindex = null;
        StudentRecord studentRecord = null;
        long personalID = student.getPersonalID();
        if (findStudentSection(personalID) == null) { // Check if in section
        	studentRecord = new StudentRecord(student);
        	currentSection.insertStudent(studentRecord.getStudent(), studentRecord.getScore(),
        			studentRecords.size()); // Add to section
            pidSectionDB.insert(personalID, currentSection.getNumber()); // Associate with section
            studentRecords.add(studentRecord); // Add to student record list
            gindex = studentRecords.size() - 1;
        }
        return studentRecord;
    }
    
    public Integer findStudentSection(long personalID) {
        return pidSectionDB.find(personalID);
    }
    
    public Student insert3(long personalID, String firstName, String lastName) {
        gindex = null;
        Student student = null;
        Integer sectionNumber = pidSectionDB.find(personalID);
        if (sectionNumber == null) { // Check if in section
        	student = new Student(personalID, new FullName(firstName, lastName));
            currentSection.insert(student, studentRecords.size()); // Add to section
            pidSectionDB.insert(personalID, currentSection.getNumber()); // Associate with section
            students.add(student); // Add to student record list
            gindex = studentRecords.size() - 1;
        }
        return student;
    }
    
    public Student insert3(Student student) {
        gindex = null;
        long personalID = student.getPersonalID();
        if (findStudentSection(personalID) == null) { // Check if in section
            currentSection.insert(student, studentRecords.size()); // Add to section
            pidSectionDB.insert(personalID, currentSection.getNumber()); // Associate with section
            students.add(student); // Add to student record list
            gindex = studentRecords.size() - 1;
            return student;
        }
        return null;
    }
    
    public StudentRecord searchid(long personalID) {
        gindex = null;
        StudentRecord studentRecord = null;
        Integer studentRecordIndex = currentSection.findStudent(personalID);
        if (studentRecordIndex != null) { // Check if in section
        	studentRecord = studentRecords.get(studentRecordIndex);
            gindex = studentRecordIndex;
        }
        return studentRecord;
    }
    
    public Student searchid3(long personalID) {
        gindex = null;
        Student student = null;
        Integer studentIndex = currentSection.findStudent(personalID);
        if (studentIndex != null) { // Check if in section
        	student = students.get(studentIndex);
            gindex = studentIndex;
        }
        return student;
    }
    
    public ArrayList<StudentRecord> search(FullName fullName) {
        gindex = null;
        ArrayList<StudentRecord> records = new ArrayList<StudentRecord>();
        int[] studentRecordIndices = currentSection.findStudents(fullName);
        for (int i = 0; i < studentRecordIndices.length; i++) {
        	records.add(studentRecords.get(studentRecordIndices[i]));
        }
        if (studentRecordIndices.length == 1) {
            gindex = studentRecordIndices[0];
        }
        return records;
    }
    
    public Student[] search3(String firstName, String lastName) {
        gindex = null;
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        FullName fullName = new FullName(firstName, lastName);
        int[] studentIndices = currentSection.findStudents(fullName);
        for (int i = 0; i < studentIndices.length; i++) {
            studentsWithName.add(students.get(studentIndices[i]));
        }
        if (studentIndices.length == 1) {
            gindex = studentIndices[0];
        }
        return toStudentArray(studentsWithName);
    }
    
    private Student[] toStudentArray(ArrayList<Student> oldStudents) {
        Student[] students = new Student[oldStudents.size()];
        students = oldStudents.toArray(students);
        return students;
    }
    
    public ArrayList<StudentRecord> search(String name) {
        gindex = null;
        ArrayList<StudentRecord> records = new ArrayList<StudentRecord>();
        Iterator<Integer> it = currentSection.studentFNIndexIterator();
        while (it.hasNext()) {
            int index = it.next();
            StudentRecord studentRecord = studentRecords.get(index);
            FullName fullName = studentRecord.getName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	records.add(studentRecord);
                if (records.size() == 1) {
                    gindex = index;
                }
            }
        }
        if (records.size() != 1) {
            gindex = null;
        }
        return records;
    }
    
    public Student[] search3(String name) {
        gindex = null;
        ArrayList<Student> studentsWithName = new ArrayList<Student>();
        Iterator<Integer> it = currentSection.studentFNIndexIterator();
        while (it.hasNext()) {
            int index = it.next();
            Student student = students.get(index);
            FullName fullName = student.getFullName();
            if (fullName.equalsPartOfIgnoreCase(name)) {
            	students.add(student);
                if (studentsWithName.size() == 1) {
                    gindex = index;
                }
            }
        }
        if (studentsWithName.size() != 1) {
            gindex = null;
        }
        return toStudentArray(studentsWithName);
    }
    
    public StudentRecord score(int percentage) {
        StudentRecord studentRecord = null;
        if (gindex != null) {
        	studentRecord = studentRecords.get(gindex);
            currentSection.updateStudentScore(studentRecord.getScore(), percentage, gindex);
            studentRecord.setScore(percentage);
            gindex = null;
        }
        return studentRecord;
    }
    
    public Student score3(int percentage) {
        Student student = null;
        if (gindex != null) {
        	student = students.get(gindex);
            currentSection.updateStudentScore(student.getPercentageGrade(), percentage, gindex);
            student.setPercentageGrade(percentage);
            gindex = null;
        }
        return student;
    }
    
    public static boolean isValidPercentageGrade(int scorePercentage) {
    	return ((scorePercentage > -1) && (scorePercentage < 101));
    }
    
    public StudentRecord remove(long personalID) {
        gindex = null;
        StudentRecord studentRecord = null;
        Integer sectionNumber = pidSectionDB.find(personalID);
        if ((sectionNumber != null) && (sectionNumber == currentSection.getNumber())) {
            int index = currentSection.removeStudentPID(personalID);
            studentRecord = studentRecords.get(index);
            currentSection.removeStudentName(studentRecord.getName(), index);
            currentSection.removeStudentScore(studentRecord.getScore(), index);
            pidSectionDB.remove(personalID); // Disassociate with section
            studentRecord.setActive(false); // Kill the student record
        }
        return studentRecord;
    }
    
    public Student remove3(long personalID) {
        gindex = null;
        Student student = null;
        Integer sectionNumber = pidSectionDB.find(personalID);
        if ((sectionNumber != null) && (sectionNumber == currentSection.getNumber())) {
            int index = currentSection.removeStudentPID(personalID);
            student = students.get(index);
            currentSection.removeStudentName(student.getFullName(), index);
            currentSection.removeStudentScore(student.getPercentageGrade(), index);
            pidSectionDB.remove(personalID); // Disassociate with section
            student.clrActive(); // Kill the student record
        }
        return student;
    }
    
    public StudentRecord remove(FullName fullName) {
        gindex = null;
        StudentRecord studentRecord = null;
        int[] indices = currentSection.findStudents(fullName);
        if (indices.length == 1) { // Check if name unique
            int index = currentSection.removeStudentName(fullName);
            studentRecord = studentRecords.get(index);
            currentSection.removeStudentPID(studentRecord.getPID());
            currentSection.removeStudentScore(studentRecord.getScore(), index);
            pidSectionDB.remove(studentRecord.getPID()); // Disassociate with section
            studentRecord.setActive(false); // Kill the student record
        }
        return studentRecord;
    }
    
    public Student remove3(String firstName, String lastName) {
        gindex = null;
        Student student = null;
        FullName fullName = new FullName(firstName, lastName);
        int[] indices = currentSection.findStudents(fullName);
        if (indices.length == 1) { // Check if name unique
            int index = currentSection.removeStudentName(fullName);
            student = students.get(index);
            currentSection.removeStudentPID(student.getPersonalID());
            currentSection.removeStudentScore(student.getPercentageGrade(), index);
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
        gindex = null;
        currentSection.clear();
    }
    
    public int dumpPIDs() {
        gindex = null;
        Iterator<Integer> itr = currentSection.studentPIDIndexIterator();
        while (itr.hasNext()) {
            StudentRecord sr = studentRecords.get(itr.next());
            System.out.println(sr);
        }
        return currentSection.size();
    }
    
    public int dumpNames() {
        gindex = null;
        Iterator<Integer> itr = currentSection.studentFNIndexIterator();
        while (itr.hasNext()) {
            StudentRecord sr = studentRecords.get(itr.next());
            System.out.println(sr);
        }
        return currentSection.size();
    }
    
    public int dumpScores() {
        gindex = null;
        Iterator<Integer> itr = currentSection.studentPGIndexIterator();
        while (itr.hasNext()) {
            StudentRecord sr = studentRecords.get(itr.next());
            System.out.println(sr);
        }
        return currentSection.size();
    }
    
    public void grade() {
        gindex = null;
        Iterator<Integer> it = currentSection.studentPGIndexIterator();
        Grader.gradeStudents(toStudentRecordArrayList(it));
    }
    
    private ArrayList<StudentRecord> toStudentRecordArrayList(Iterator<Integer> it) {
        ArrayList<StudentRecord> subStudentRecords = new ArrayList<StudentRecord>();
    	while (it.hasNext()) {
            int index = it.next();
            subStudentRecords.add(studentRecords.get(index));
        }
    	return subStudentRecords;
    }
    
    public void stat() {
        gindex = null;
        Iterator<Integer> it = currentSection.studentPGIndexIterator();
        Grader.statStudents(toStudentRecordArrayList(it));
    }
    
    public ArrayList<StudentRecord> list(String letter) {
        gindex = null;
        ArrayList<StudentRecord> records = new ArrayList<StudentRecord>();
        int lower = Grader.getLowerPercentageGrade(letter);
        int upper = Grader.getUpperPercentageGrade(letter);
        ArrayList<Integer> indices = currentSection.searchForScoresInRange(lower, upper);
        for (int i = 0; i < indices.size(); i++) {
        	records.add(studentRecords.get(indices.get(i)));
        }
        return records;
    }

    /**
     * Finds pairs of Student Records
     * with score differences less than
     * or equal to a score
     * 
     * @param s score
     * @return string list of pairs of
     * names
     */
    public ArrayList<String> findpair(int s) {
        gindex = null;
        ArrayList<String> nsl = new ArrayList<String>();
        Iterator<Integer> itr1 = currentSection.studentPGIndexIterator();
        int i = 0;
        if (!currentSection.isEmpty()) {
            while (itr1.hasNext()) {
                StudentRecord sr1 = studentRecords.get(itr1.next());
                Iterator<Integer> itr2 = currentSection.studentPGIndexIterator();
                for (int j = 0; j < i; j++) {
                    itr2.next();
                }
                while (itr2.hasNext()) {
                    StudentRecord sr2 = studentRecords.get(itr2.next());
                    int diff = Math.abs(sr1.getScore() - sr2.getScore());
                    if ((diff <= s) && (sr1 != sr2)) {
                        nsl.add(sr1.getName() + ", " + sr2.getName());
                    }
                }
                i++;
            }
        }
        return nsl;
    }

    /**
     * Merges all student records
     * from all sections into the current
     * section
     * 
     * @return TRUE if merge is
     * successful, FALSE otherwise
     */
    public boolean merge() {
        gindex = null;
        boolean b = false;
        if (currentSection.isEmpty()) {
            currentSection.setActive(false);
            for (int i = 0; i < sections.length; i++) {
                if (sections[i].isActive()) {
                    Iterator<Integer> itr = sections[i].studentPIDIndexIterator();
                    if (!sections[i].isEmpty()) {
                        while (itr.hasNext()) {
                            int j = itr.next();
                            StudentRecord sr = studentRecords.get(j);
                            currentSection.insertStudent(sr.getStudent(), sr
                                .getScore(), j);
                        }
                    }
                }
            }
            b = true;
        }
        return b;
    }

    /**
     * Saves all of the active student
     * records in a byte array structured
     * according to the project description
     * 
     * @return byte array
     */
    public byte[] savecoursedata() {
        gindex = null;
        byte[] ba = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BinFileHelper bfh = new BinFileHelper();
            baos.write("CS3114atVT".getBytes());
            int sl = savecoursedatahelper();
            baos.write(bfh.intToByteArray(sl));
            for (int i = 0; i < sl; i++) {
                Iterator<Integer> itr = sections[i].studentPIDIndexIterator();
                if (!sections[i].isEmpty() && sections[i].isActive()) {
                    baos.write(bfh.intToByteArray(sections[i].size()));
                    while (itr.hasNext()) {
                        int j = itr.next();
                        StudentRecord sr = studentRecords.get(j);
                        baos.write(bfh.longToByteArray(sr.getPID()));
                        baos.write(sr.getFirst().getBytes());
                        baos.write("$".getBytes());
                        baos.write(sr.getLast().getBytes());
                        baos.write("$".getBytes());
                        baos.write(bfh.intToByteArray(sr.getScore()));
                        if (sr.getGrade().getBytes().length > 1) {
                            baos.write(sr.getGrade().getBytes());
                        }
                        else {
                            String g = new String(sr.getGrade() + " ");
                            baos.write(g.getBytes());
                        }
                    }
                    baos.write("GOHOKIES".getBytes());
                }
            }
            ba = baos.toByteArray();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ba;
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
    int savecoursedatahelper() {
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
        gindex = null;
        for (int i = 0; i < 21; i++) {
            sections[i].clear();
        }
        studentRecords.clear();
        pidSectionDB.clear();
    }
}
