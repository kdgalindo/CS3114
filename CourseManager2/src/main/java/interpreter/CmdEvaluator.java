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
 * TopLevel Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class CmdEvaluator {
    private StudentManager2 studentManager;
    private CourseManager2 courseManager;
    private boolean sdloaded; // student data loaded

    /**
     * TopLevel default constructor
     */
    public CmdEvaluator() {
        studentManager = new StudentManager2();
        courseManager = new CourseManager2();
        sdloaded = false;
    }

    /**
     * loadstudentdata command
     * 
     * @param fn filename
     */
    public void loadStudentData(String fn) {
        if (fn.endsWith(".csv")) {
            loadtxtstudentdata(fn);
        }
        else if (fn.endsWith(".data")) {
            loadbinstudentdata(fn);
        }
        courseManager.loadstudentdata();
    }

    /**
     * loadstudentdata command for
     * .csv files
     * 
     * @param tfn text filename
     */
    private void loadtxtstudentdata(String tfn) {
        System.out.println(tfn + " successfully loaded");
        try {
            Scanner sc1 = new Scanner(new File(tfn));
            while (sc1.hasNextLine()) {
                Scanner sc2 = new Scanner(sc1.nextLine());
                sc2.useDelimiter("\\s*,\\s*");
                while (sc2.hasNext()) {
                    long p = sc2.nextLong(); // student id
                    String f = sc2.next(); // first name
                    String m = sc2.next(); // middle name
                    String l = sc2.next(); // last name
                    Student s = new Student(p, new FullName(f, m, l));
                    loadstudentdatahelper(s);
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

    /**
     * loadstudentdata command for
     * .data files
     * 
     * @param bfn binary filename
     */
    private void loadbinstudentdata(String bfn) {
        System.out.println(bfn + " successfully loaded");

        BinFileHelper bfh = new BinFileHelper();
        byte[] bs = bfh.binFileToByteArray(bfn);

        for (int i = 14; i < bs.length; i++) {
            long p = bfh.byteArrayToLong(bs, i);
            i += 8; // student id
            String f = bfh.byteArrayToName(bs, i);
            i += f.length() + 1; // first name
            String m = bfh.byteArrayToName(bs, i);
            i += m.length() + 1; // middle name
            String l = bfh.byteArrayToName(bs, i);
            i += l.length() + 1; // last name
            i += 7;
            Student s = new Student(p, new FullName(f, m, l));
            loadstudentdatahelper(s);
        }
        sdloaded = true;
    }
    
    /**
     * loadstudentdata command helper
     * 
     * @param s student
     */
    public void loadstudentdatahelper(Student s) {
        studentManager.insert(s.getPersonalID(), s);
    }

    /**
     * loadcoursedata command
     * 
     * @param fn filename
     */
    public void loadCourseData(String fn) {
        if (sdloaded) {
            if (fn.endsWith(".csv")) {
                System.out.println(fn.substring(0, fn.length() - 4)
                    + " Course has been successfully loaded.");
                loadtxtcoursedata(fn);
            }
            else if (fn.endsWith(".data")) {
                System.out.println(fn.substring(0, fn.length() - 5)
                    + " Course has been successfully loaded.");
                loadbincoursedata(fn);
            }
        }
        else {
            System.out.println(
                "Course Load Failed. You have to load Student Information"
                + " file first.");
        }
    }

    /**
     * loadcoursedata command for
     * .csv files
     * 
     * @param tfn text filename
     */
    public void loadtxtcoursedata(String tfn) {
        try {
            Scanner sc1 = new Scanner(new File(tfn));
            while (sc1.hasNextLine()) {
                Scanner sc2 = new Scanner(sc1.nextLine());
                sc2.useDelimiter("\\s*,\\s*");
                while (sc2.hasNext()) {
                    int i = sc2.nextInt(); // section id
                    long p = sc2.nextLong(); // student id
                    String f = sc2.next(); // first name
                    String l = sc2.next(); // last name
                    int s = 0;
                    String g = "F ";
                    if (sc2.hasNextInt()) {
                        s = sc2.nextInt(); // score
                        g = sc2.next(); // grade
                    }
                    else {
                        sc2.nextLine();
                    }
                    Student ns = new Student(p, new FullName(f, l));
                    StudentRecord nsr = new StudentRecord(ns, s, g);
                    loadcoursedatahelper(i, nsr);
                }
                sc2.close();
            }
            sc1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * loadcoursedata command for
     * .data files
     * 
     * @param bfn binary filename
     */
    public void loadbincoursedata(String bfn) {
        BinFileHelper bfh = new BinFileHelper();
        byte[] ba = bfh.binFileToByteArray(bfn);
        int csn = 1; // current section number
        int lsn = bfh.byteArrayToInt(ba, 10);
        int csrn = 1; // current student record number
        int lsrn = bfh.byteArrayToInt(ba, 14);
        for (int i = 18; i < ba.length; i++) {
            long p = bfh.byteArrayToLong(ba, i);
            i += 8; // student id
            String f = bfh.byteArrayToName(ba, i);
            i += f.length() + 1; // first name + $
            String l = bfh.byteArrayToName(ba, i);
            i += l.length() + 1; // last name + $
            int s = 0;
            String g = "F ";
            if (bfh.byteArrayHasScoreGrade(ba, i + 4)) {
                s = bfh.byteArrayToInt(ba, i);
                g = bfh.byteArrayToGrade(ba, i + 4);
                i += 5; // score + grade
            }
            Student ns = new Student(p, new FullName(f, l));
            StudentRecord nsr = new StudentRecord(ns, s, g);
            loadcoursedatahelper(csn, nsr);
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

    /**
     * loadcoursedata command helper
     * 
     * @param sn section number
     * @param nsr new student record
     */
    public void loadcoursedatahelper(int sn, StudentRecord nsr) {
        long p = nsr.getPID();
        Student s = studentManager.search(p);
        if (s != null) { // Check if id exists
            FullName n = nsr.getName();
            if (s.getFullName().compareTo(n) == 0) {
                StudentRecord sr = courseManager.loadcoursedata(sn, nsr);
                if (sr == null) { // Check if student enrolled
                    System.out.println("Warning: Student " + nsr.getName()
                        + " is not loaded to section " + sn
                        + " since he/she is already enrolled in section "
                        + courseManager.searchForSectionByPID(nsr.getPID()));
                }
            }
            else {
                System.out.println("Warning: Student " + nsr.getName()
                    + " is not loaded to section " + sn
                    + " since the corresponding pid belongs to another"
                    + " student.");
            }
        }
        else {
            System.out.println("Warning: Student " + nsr.getName()
                + " is not loaded to section " + sn
                + " since he/she doesn't exist in the loaded student records.");
        }
    }

    public void section(int sectionNumber) {
        courseManager.setSection(sectionNumber);
        System.out.println("switch to section "
            + courseManager.getSection());
    }

    public void insert(long personalID, String firstName, String lastName) {
        if (courseManager.isSectionActive()) {
            Student student = studentManager.findStudent(personalID);
            if (student != null) { // Check if id exists
                FullName n = new FullName(firstName, lastName);
                if (student.getFullName().compareTo(n) == 0) { // Check if name matches
                	student = new Student(personalID, n);
                    StudentRecord sr = courseManager.insert(student);
                    if (sr != null) { // Check if student enrolled
                        System.out.println(n + " inserted.");
                    }
                    else {
                        if (courseManager.searchForSectionByPID(personalID) == courseManager
                            .getSection()) {
                            System.out.println(n + " is already in section "
                                + courseManager.getSection());
                        }
                        else {
                            System.out.println(n
                                + " is already registered in a different"
                                + " section");
                        }
                    }
                }
                else {
                    System.out.println(n
                        + " insertion failed. Wrong student information."
                        + " ID belongs to another student");
                }
            }
            else {
                System.out.println(firstName + " " + lastName
                    + " insertion failed. Wrong student information."
                    + " ID doesn't exist");
            }
        }
        else {
            System.out.println(
                "Command insert is not valid for merged sections");
        }
    }
    
    public void insert2(long personalID, String firstName, String lastName) {
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
        
        StudentRecord studentRecord = courseManager.insert(new Student(personalID, fullName));
        System.out.println(studentRecord.getName() + " inserted.");
    }

    /**
     * searchid command
     * 
     * @param p pid
     */
    public void searchID(long p) {
        if (courseManager.isCurrentSectionActive()) {
            StudentRecord sr = courseManager.searchid(p);
            if (sr != null) {
                System.out.println("Found " + sr);
            }
            else {
                System.out.println(
                    "Search Failed. Couldn't find any student with ID " + String
                        .format("%09d", p));
            }
        }
        else {
            System.out.println(
                "Command searchid is not valid for merged sections");
        }
    }
    
    public void searchID2(long personalID) {
        if (!courseManager.isCurrentSectionActive()) {
        	System.out.println("Command searchid is not valid for merged sections");
        	return;
        }
        
        StudentRecord studentRecord = courseManager.searchid(personalID);
        if (studentRecord == null) {
            System.out.println("Search Failed. Couldn't find any student with ID "
                    + String.format("%09d", personalID));
            return;
        }
        
        System.out.println("Found " + studentRecord);
    }

    /**
     * search command
     * 
     * @param f firstname
     * @param l lastname
     */
    public void search(String f, String l) {
        if (courseManager.isCurrentSectionActive()) {
            FullName n = new FullName(f, l);
            System.out.println("search results for " + n + ":");
            ArrayList<StudentRecord> srl = courseManager.search(n);
            for (int i = 0; i < srl.size(); i++) {
                System.out.println(srl.get(i));
            }
            System.out.println(n + " was found in " + srl.size()
                + " records in section " + courseManager.getSection());
        }
        else {
            System.out.println(
                "Command search is not valid for merged sections");
        }
    }
    
    public void search2(String firstName, String lastName) {
        if (!courseManager.isCurrentSectionActive()) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        System.out.println("search results for " + fullName + ":");
        ArrayList<StudentRecord> studentRecords = courseManager.search(fullName);
        for (int i = 0; i < studentRecords.size(); i++) {
            System.out.println(studentRecords.get(i));
        }
        System.out.println(fullName
        		+ " was found in "
        		+ studentRecords.size()
        		+ " records in section "
        		+ courseManager.getSection());
    }

    /**
     * Search command
     * 
     * @param n name
     */
    public void search(String n) {
        if (courseManager.isCurrentSectionActive()) {
            System.out.println("search results for " + n + ":");
            ArrayList<StudentRecord> srl = courseManager.search(n);
            for (int i = 0; i < srl.size(); i++) {
                System.out.println(srl.get(i));
            }
            System.out.println(n + " was found in " + srl.size()
                + " records in section " + courseManager.getSection());
        }
        else {
            System.out.println(
                "Command search is not valid for merged sections");
        }
    }
    
    public void search2(String name) {
        if (!courseManager.isCurrentSectionActive()) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        System.out.println("search results for " + name + ":");
        ArrayList<StudentRecord> studentRecords = courseManager.search(name);
        for (int i = 0; i < studentRecords.size(); i++) {
            System.out.println(studentRecords.get(i));
        }
        System.out.println(name
        		+ " was found in "
        		+ studentRecords.size()
        		+ " records in section "
        		+ courseManager.getSection());
    }

    /**
     * score command
     * 
     * @param n number
     */
    public void score(int n) {
        if (courseManager.isCurrentSectionActive()) {
            if (courseManager.isAStudentGradable()) {
                if ((n >= 0) && (n <= 100)) {
                    StudentRecord sr = courseManager.score(n);
                    System.out.println("Update " + sr.getName()
                        + " record, score = " + sr.getScore());
                }
                else {
                    System.out.println(
                        "Scores have to be integers in range 0 to 100.");
                }
            }
            else {
                System.out.println(
                    "score command can only be called after an insert command"
                    + " or a successful search command with one exact output.");
            }
        }
        else {
            System.out.println(
                "Command score is not valid for merged sections");
        }
    }

    /**
     * remove command
     * 
     * @param p pid
     */
    public void remove(long p) {
        if (courseManager.isCurrentSectionActive()) {
            Student s = studentManager.search(p);
            if (s != null) {
                StudentRecord sr = courseManager.remove(p);
                if (sr != null) {
                    System.out.println("Student " + sr.getName()
                        + " get removed from section " + courseManager
                            .getSection());
                }
                else {
                    System.out.println(
                        "Remove failed: couldn't find any student with id "
                            + p);
                }
            }
            else {
                System.out.println(
                    "Remove failed: couldn't find any student with id " + p);
            }
        }
        else {
            System.out.println(
                "Command remove is not valid for merged sections");
        }
    }
    
    public void remove2(long personalID) {
        if (!courseManager.isCurrentSectionActive()) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        Student student = studentManager.search(personalID);
        if (student == null) {
        	System.out.println("Remove failed: couldn't find any student with id "
        			+ personalID);
        	return;
        }
        
        StudentRecord studentRecord = courseManager.remove(personalID);
        if (studentRecord == null) {
            System.out.println("Remove failed: couldn't find any student with id "
            		+ personalID);
            return;
        }
        
        System.out.println("Student "
        		+ studentRecord.getName()
        		+ " get removed from section "
        		+ courseManager.getSection());
    }

    /**
     * remove command
     * 
     * @param f firstname
     * @param l lastname
     */
    public void remove(String f, String l) {
        if (courseManager.isCurrentSectionActive()) {
            FullName n = new FullName(f, l);
            StudentRecord sr = courseManager.remove(n);
            if (sr != null) {
                System.out.println("Student " + n + " get removed from section "
                    + courseManager.getSection());
            }
            else {
                System.out.println("Remove failed. Student " + n
                    + " doesn't exist in section " + courseManager
                        .getSection());
            }
        }
        else {
            System.out.println(
                "Command remove is not valid for merged sections");
        }
    }
    
    public void remove2(String firstName, String lastName) {
        if (!courseManager.isCurrentSectionActive()) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        StudentRecord studentRecord = courseManager.remove(fullName);
        if (studentRecord == null) {
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

    /**
     * clearsection command
     */
    public void clearSection() {
        courseManager.clearsection();
        System.out.println("Section "
            + courseManager.getSection()
            + " cleared");
    }

    /**
     * dumpsection command
     */
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

    /**
     * grade command
     */
    public void grade() {
        courseManager.grade();
        System.out.println("grading completed");
    }

    /**
     * stat command
     */
    public void stat() {
        String[] letters = { "A ", "A-", "B+", "B ", "B-", "C+", "C ", "C-",
            "D+", "D ", "D-", "F " };
        System.out.println("Statistics of section " + courseManager
            .getSection() + ":");
        int[] numbers = courseManager.stat();
        for (int i = 0; i < numbers.length; i++) {
            int number = numbers[i];
            if (number != 0) {
                System.out.println(number + " students with grade "
                    + letters[i]);
            }
        }
    }

    /**
     * list command
     * 
     * @param g grade
     */
    public void list(String g) {
        System.out.println("Students with grade " + g + " are:");
        ArrayList<StudentRecord> srl = courseManager.list(g);
        for (int i = 0; i < srl.size(); i++) {
            StudentRecord sr = srl.get(i);
            System.out.println(sr + ", grade = " + sr.getGrade());
        }
        System.out.println("Found " + srl.size() + " students");
    }

    /**
     * findpair command
     * 
     * @param s score
     */
    public void findPair(int s) {
        System.out.println("Students with score difference less than or equal "
            + s + ":");
        ArrayList<String> sl = courseManager.findpair(s);
        for (int i = 0; i < sl.size(); i++) {
            System.out.println(sl.get(i));
        }
        System.out.println("found " + sl.size() + " pairs");
    }

    /**
     * merge command
     */
    public void merge() {
        if (courseManager.merge()) {
            System.out.println("All sections merged at section " + courseManager
                .getSection());
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section "
                    + courseManager.getSection() + " is not empty.");
        }
    }

    /**
     * savestudentdata command
     * 
     * @param bfn binary filename
     */
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

    /**
     * savecoursedata command
     * 
     * @param bfn binaryfilename
     */
    public void saveCourseData(String bfn) {
        System.out.println("Saved all course data to " + bfn);
        BinFileHelper bfh = new BinFileHelper();
        byte[] ba = courseManager.savecoursedata();
        bfh.byteArrayToBinFile(ba, bfn);
    }

    /**
     * clearcoursedata command
     */
    public void clearCourseData() {
        System.out.println("All course data cleared.");
        courseManager.clearcoursedata();
    }
}
