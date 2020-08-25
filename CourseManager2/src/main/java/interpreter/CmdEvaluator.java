package interpreter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import bin.BinFileHelper;
import course.CourseManager2;
import course.StudentManager2;
import student.*;

/**
 * CmdEvaluator Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class CmdEvaluator {
    private StudentManager2 studentManager;
    private CourseManager2 courseManager;
    private boolean sdloaded; // student data loaded

    public CmdEvaluator() {
        studentManager = new StudentManager2();
        courseManager = new CourseManager2();
        sdloaded = false;
    }

    public void loadStudentData(String fn) {
        if (fn.endsWith(".csv")) {
        	txtLoadStudentData(fn);
        }
        else if (fn.endsWith(".data")) {
        	binLoadStudentData(fn);
        }
        courseManager.loadstudentdata();
    }

    private void txtLoadStudentData(String tfn) {
        System.out.println(tfn + " successfully loaded");
        try {
            Scanner sc1 = new Scanner(new File(tfn));
            while (sc1.hasNextLine()) {
                Scanner sc2 = new Scanner(sc1.nextLine());
                sc2.useDelimiter("\\s*,\\s*");
                while (sc2.hasNext()) {
                    long personalID = sc2.nextLong(); // student id
                    String firstName = sc2.next(); // first name
                    String middleName = sc2.next(); // middle name
                    String lastName = sc2.next(); // last name
                    FullName fullName = new FullName(firstName, middleName, lastName);
                    Student student = new Student(personalID, fullName);
                    loadStudentDataHelper(student);
                }
                sc2.close();
            }
            sc1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        sdloaded = true;
    }

    private void binLoadStudentData(String bfn) {
        System.out.println(bfn + " successfully loaded");

        BinFileHelper bfh = new BinFileHelper();
        byte[] bs = bfh.binFileToByteArray(bfn);

        for (int i = 14; i < bs.length; i++) {
            long personalID = bfh.byteArrayToLong(bs, i);
            i += 8; // student id
            String firstName = bfh.byteArrayToName(bs, i);
            i += firstName.length() + 1; // first name
            String middleName = bfh.byteArrayToName(bs, i);
            i += middleName.length() + 1; // middle name
            String lastName = bfh.byteArrayToName(bs, i);
            i += lastName.length() + 1; // last name
            i += 7;
            FullName fullName = new FullName(firstName, middleName, lastName);
            Student student = new Student(personalID, fullName);
            loadStudentDataHelper(student);
        }
        sdloaded = true;
    }
    
    public void loadStudentDataHelper(Student s) {
        studentManager.insert(s.getPersonalID(), s);
    }

    public void loadCourseData(String fn) {
        if (sdloaded) {
            if (fn.endsWith(".csv")) {
                System.out.println(fn.substring(0, fn.length() - 4)
                    + " Course has been successfully loaded.");
                txtLoadCourseData(fn);
            }
            else if (fn.endsWith(".data")) {
                System.out.println(fn.substring(0, fn.length() - 5)
                    + " Course has been successfully loaded.");
                binLoadCourseData(fn);
            }
        }
        else {
            System.out.println(
                "Course Load Failed. You have to load Student Information"
                + " file first.");
        }
    }
    
    public void txtLoadCourseData(String tfn) {
        try {
            Scanner sc1 = new Scanner(new File(tfn));
            while (sc1.hasNextLine()) {
                Scanner sc2 = new Scanner(sc1.nextLine());
                sc2.useDelimiter("\\s*,\\s*");
                while (sc2.hasNext()) {
                    int sectionNumber = sc2.nextInt(); // section id
                    long personalID = sc2.nextLong(); // student id
                    String firstName = sc2.next(); // first name
                    String lastName = sc2.next(); // last name
                    int percentageGrade = 0;
                    String letterGrade = "F ";
                    if (sc2.hasNextInt()) {
                        percentageGrade = sc2.nextInt(); // score
                        letterGrade = sc2.next(); // grade
                    }
                    else {
                        sc2.nextLine();
                    }
                    FullName fullName = new FullName(firstName, lastName);
                    Grade grade = new Grade(percentageGrade, letterGrade);
                    Student student = new Student(personalID, fullName, grade);
                    loadCourseDataHelper(sectionNumber, student);
                }
                sc2.close();
            }
            sc1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void binLoadCourseData(String bfn) {
        BinFileHelper bfh = new BinFileHelper();
        byte[] ba = bfh.binFileToByteArray(bfn);
        int csn = 1; // current section number
        int lsn = bfh.byteArrayToInt(ba, 10);
        int csrn = 1; // current student record number
        int lsrn = bfh.byteArrayToInt(ba, 14);
        for (int i = 18; i < ba.length; i++) {
            long personalID = bfh.byteArrayToLong(ba, i);
            i += 8; // student id
            String firstName = bfh.byteArrayToName(ba, i);
            i += firstName.length() + 1; // first name + $
            String lastName = bfh.byteArrayToName(ba, i);
            i += lastName.length() + 1; // last name + $
            int percentageGrade = 0;
            String letterGrade = "F ";
            if (bfh.byteArrayHasScoreGrade(ba, i + 4)) {
            	percentageGrade = bfh.byteArrayToInt(ba, i);
            	letterGrade = bfh.byteArrayToGrade(ba, i + 4);
                i += 5; // score + grade
            }
            FullName fullName = new FullName(firstName, lastName);
            Grade grade = new Grade(percentageGrade, letterGrade);
            Student student = new Student(personalID, fullName, grade);
            loadCourseDataHelper(csn, student);
            if (csrn == lsrn) {
                i += 9; // GOHOKIES
                if (csn != lsn) {
                    lsrn = bfh.byteArrayToInt(ba, i);
                    i += 3; // record #
                    csn++;
                }
                csrn = 1;
            }
            else {
                csrn++;
            }
        }
    }
    
    public void loadCourseDataHelper(int sectionNumber, Student newStudent) {
        Student student = studentManager.search(newStudent.getPersonalID());
        if (student == null) { // Check if id exists
            System.out.println("Warning: Student "
            		+ newStudent.getFullName()
            		+ " is not loaded to section "
            		+ sectionNumber
            		+ " since he/she doesn't exist in the loaded student records.");
            return;
        }
        
        FullName fullName = newStudent.getFullName();
        if (student.getFullName().compareTo(fullName) != 0) {
            System.out.println("Warning: Student "
            		+ newStudent.getFullName()
            		+ " is not loaded to section "
            		+ sectionNumber
            		+ " since the corresponding pid belongs to another student.");
            return;
        }
        
        Student record = courseManager.loadCourseData(sectionNumber, newStudent);
        if (record == null) { // Check if student enrolled
            System.out.println("Warning: Student " + newStudent.getFullName()
                + " is not loaded to section " + sectionNumber
                + " since he/she is already enrolled in section "
                + courseManager.findStudentSection(newStudent.getPersonalID()));
        }
    }

    public void section(int sectionNumber) {
        courseManager.setSection(sectionNumber);
        System.out.println("switch to section "
            + courseManager.getSection());
    }
    
    public void insert(long personalID, String firstName, String lastName) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command insert is not valid for merged sections");
        	return;
        }
        
        Student student = studentManager.findStudent(personalID);
        if (student == null) { // Check if id exists
            System.out.println(firstName + " " + lastName
                    + " insertion failed. Wrong student information."
                    + " ID doesn't exist");
            return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        if (student.getFullName().compareTo(fullName) != 0) { // Check if name matches
        	System.out.println(fullName
                    + " insertion failed. Wrong student information."
                    + " ID belongs to another student");
        	return;
        }
        
        Integer sectionNumber = courseManager.findStudentSection(personalID);
        if (sectionNumber != null) { // Check if student enrolled
        	if (sectionNumber == courseManager.getSection()) {
                System.out.println(fullName
                		+ " is already in section "
                        + courseManager.getSection());
        	}
        	else {
            	System.out.println(fullName
                        + " is already registered in a different section");
        	}
        	return;
        }
        
        Student record = courseManager.insert(new Student(personalID, fullName, new Grade()));
        System.out.println(record.getFullName() + " inserted.");
    }
    
    public void searchID(long personalID) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command searchid is not valid for merged sections");
        	return;
        }
        
        Student student = courseManager.findStudent(personalID);
        if (student == null) {
            System.out.println("Search Failed. Couldn't find any student with ID "
                    + String.format("%09d", personalID));
            return;
        }
        
        System.out.println("Found " + student);
    }
    
    public void search(String firstName, String lastName) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        System.out.println("search results for " + fullName + ":");
        Student[] students = courseManager.findStudents(fullName);
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
        System.out.println(fullName
        		+ " was found in "
        		+ students.length
        		+ " records in section "
        		+ courseManager.getSection());
    }
    
    public void search(String name) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        System.out.println("search results for " + name + ":");
        Student[] students = courseManager.findStudents(name);
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
        System.out.println(name
        		+ " was found in "
        		+ students.length
        		+ " records in section "
        		+ courseManager.getSection());
    }
    
    public void score(int percentage) {
        if (!courseManager.isSectionActive()) {
            System.out.println("Command score is not valid for merged sections");
            return;
        }
        
        if (!courseManager.isStudentGradable()) {
            System.out.println("score command can only be called after an insert command"
                    + " or a successful search command with one exact output.");
            return;
        }
        
        if (!CourseManager2.isValidPercentageGrade(percentage)) {
            System.out.println("Scores have to be integers in range 0 to 100.");
            return;
        }
        
        Student student = courseManager.scoreStudent(percentage);
        System.out.println("Update "
        		+ student.getFullName()
        		+ " record, score = "
        		+ student.getPercentageGrade());
    }
    
    public void remove(long personalID) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        Student student = studentManager.search(personalID);
        if (student == null) {
        	System.out.println("Remove failed: couldn't find any student with id "
        			+ personalID);
        	return;
        }
        
        Student record = courseManager.removeStudent(personalID);
        if (record == null) {
            System.out.println("Remove failed: couldn't find any student with id "
            		+ personalID);
            return;
        }
        
        System.out.println("Student "
        		+ record.getFullName()
        		+ " get removed from section "
        		+ courseManager.getSection());
    }
    
    public void remove(String firstName, String lastName) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        Student student = courseManager.removeStudent(fullName);
        if (student == null) {
            System.out.println("Remove failed. Student "
            		+ fullName
                    + " doesn't exist in section "
            		+ courseManager.getSection());
            return;
        }
        
        System.out.println("Student "
        		+ fullName
        		+ " get removed from section "
                + courseManager.getSection());
    }

    public void clearSection() {
        courseManager.clearsection();
        System.out.println("Section "
            + courseManager.getSection()
            + " cleared");
    }

    public void dumpSection() {
        System.out.println("section "
        		+ courseManager.getSection()
        		+ " dump:");
        System.out.println("BST by ID:");
        int size = courseManager.dumpPIDs();
        System.out.println("BST by name:");
        courseManager.dumpNames();
        System.out.println("BST by score:");
        courseManager.dumpScores();
        System.out.println("Size = " + size);
    }
    
    public void grade() {
        courseManager.gradeStudents();
    }
    
    public void stat() {
        System.out.println("Statistics of section "
        		+ courseManager.getSection() + ":");
        courseManager.statStudents();
    }
    
    public void list(String letter) {
        System.out.println("Students with grade " + letter + " are:");
        Student[] students = courseManager.listStudents(letter);
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            System.out.println(student
            		+ ", grade = "
            		+ student.getLetterGrade());
        }
        System.out.println("Found "
        		+ students.length
        		+ " students");
    }

    public void findPair(int percentageDiff) {
        System.out.println("Students with score difference less than or equal "
        		+ percentageDiff + ":");
        ArrayList<String> strings = courseManager.findpair(percentageDiff);
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }
        System.out.println("found " + strings.size() + " pairs");
    }

    public void merge() {
        if (courseManager.mergeSections()) {
            System.out.println("All sections merged at section " + courseManager
                .getSection());
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section "
                    + courseManager.getSection() + " is not empty.");
        }
    }

    public void saveStudentData(String bfn) {
        System.out.println("Saved all Students data to " + bfn);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BinFileHelper bfh = new BinFileHelper();
            baos.write("VTSTUDENTS".getBytes());
            baos.write(bfh.intToByteArray(studentManager.size()));
            Iterator<Student> itr = studentManager.iterator();
            if (!studentManager.isEmpty()) {
                while (itr.hasNext()) {
                    Student s = itr.next();
                    baos.write(bfh.longToByteArray(s.getPersonalID()));
                    baos.write(s.getFirstName().getBytes());
                    baos.write("$".getBytes());
                    String m = s.getMiddleName();
                    if (m != null) {
                        baos.write(m.getBytes());
                    }
                    baos.write("$".getBytes());
                    baos.write(s.getLastName().getBytes());
                    baos.write("$".getBytes());
                    baos.write("GOHOKIES".getBytes());
                }
            }
            bfh.byteArrayToBinFile(baos.toByteArray(), bfn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        courseManager.loadstudentdata();
    }

    public void saveCourseData(String bfn) {
        System.out.println("Saved all course data to " + bfn);
        BinFileHelper bfh = new BinFileHelper();
        byte[] ba = courseManager.saveCourseData();
        bfh.byteArrayToBinFile(ba, bfn);
    }

    public void clearCourseData() {
        System.out.println("All course data cleared.");
        courseManager.clearcoursedata();
    }
}
