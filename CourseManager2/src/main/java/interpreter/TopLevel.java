package interpreter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import bin.BinFileHelper;
import course.CourseManager;
import student.*;

/**
 * TopLevel Class
 * Handles Output
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-10-20
 */
public class TopLevel {
    private StudentManager smanager; // student manager
    private CourseManager cmanager; // course manager
    private boolean sdloaded; // student data loaded

    /**
     * TopLevel default constructor
     */
    TopLevel() {
        smanager = new StudentManager();
        cmanager = new CourseManager();
        sdloaded = false;
    }

    /**
     * loadstudentdata command
     * 
     * @param fn filename
     */
    public void loadstudentdata(String fn) {
        if (fn.endsWith(".csv")) {
            loadtxtstudentdata(fn);
        }
        else if (fn.endsWith(".data")) {
            loadbinstudentdata(fn);
        }
        cmanager.loadstudentdata();
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
                    Student s = new Student(p, new Name(f, m, l));
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
            Student s = new Student(p, new Name(f, m, l));
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
        smanager.insert(s.getPID(), s);
    }

    /**
     * loadcoursedata command
     * 
     * @param fn filename
     */
    public void loadcoursedata(String fn) {
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
                    Student ns = new Student(p, new Name(f, l));
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
            Student ns = new Student(p, new Name(f, l));
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
        Student s = smanager.search(p);
        if (s != null) { // Check if id exists
            Name n = nsr.getName();
            if (s.getName().compareTo(n) == 0) {
                StudentRecord sr = cmanager.loadcoursedata(sn, nsr);
                if (sr == null) { // Check if student enrolled
                    System.out.println("Warning: Student " + nsr.getName()
                        + " is not loaded to section " + sn
                        + " since he/she is already enrolled in section "
                        + cmanager.searchForSectionByPID(nsr.getPID()));
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

    /**
     * section command
     * 
     * @param n section number
     */
    public void section(int n) {
        cmanager.section(n);
        System.out.println("switch to section "
            + cmanager.currentSectionNumber());
    }

    /**
     * insert command
     * 
     * @param p pid
     * @param f firstname
     * @param l lastname
     */
    public void insert(long p, String f, String l) {
        if (cmanager.isCurrentSectionActive()) {
            Student s = smanager.search(p);
            if (s != null) { // Check if id exists
                Name n = new Name(f, l);
                if (s.getName().compareTo(n) == 0) { // Check if name matches
                    s = new Student(p, n);
                    StudentRecord sr = cmanager.insert(s);
                    if (sr != null) { // Check if student enrolled
                        System.out.println(n + " inserted.");
                    }
                    else {
                        if (cmanager.searchForSectionByPID(p) == cmanager
                            .currentSectionNumber()) {
                            System.out.println(n + " is already in section "
                                + cmanager.currentSectionNumber());
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
                System.out.println(f + " " + l
                    + " insertion failed. Wrong student information."
                    + " ID doesn't exist");
            }
        }
        else {
            System.out.println(
                "Command insert is not valid for merged sections");
        }
    }

    /**
     * searchid command
     * 
     * @param p pid
     */
    public void searchid(long p) {
        if (cmanager.isCurrentSectionActive()) {
            StudentRecord sr = cmanager.searchid(p);
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

    /**
     * search command
     * 
     * @param f firstname
     * @param l lastname
     */
    public void search(String f, String l) {
        if (cmanager.isCurrentSectionActive()) {
            Name n = new Name(f, l);
            System.out.println("search results for " + n + ":");
            ArrayList<StudentRecord> srl = cmanager.search(n);
            for (int i = 0; i < srl.size(); i++) {
                System.out.println(srl.get(i));
            }
            System.out.println(n + " was found in " + srl.size()
                + " records in section " + cmanager.currentSectionNumber());
        }
        else {
            System.out.println(
                "Command search is not valid for merged sections");
        }
    }

    /**
     * Search command
     * 
     * @param n name
     */
    public void search(String n) {
        if (cmanager.isCurrentSectionActive()) {
            System.out.println("search results for " + n + ":");
            ArrayList<StudentRecord> srl = cmanager.search(n);
            for (int i = 0; i < srl.size(); i++) {
                System.out.println(srl.get(i));
            }
            System.out.println(n + " was found in " + srl.size()
                + " records in section " + cmanager.currentSectionNumber());
        }
        else {
            System.out.println(
                "Command search is not valid for merged sections");
        }
    }

    /**
     * score command
     * 
     * @param n number
     */
    public void score(int n) {
        if (cmanager.isCurrentSectionActive()) {
            if (cmanager.isAStudentGradable()) {
                if ((n >= 0) && (n <= 100)) {
                    StudentRecord sr = cmanager.score(n);
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
        if (cmanager.isCurrentSectionActive()) {
            Student s = smanager.search(p);
            if (s != null) {
                StudentRecord sr = cmanager.remove(p);
                if (sr != null) {
                    System.out.println("Student " + sr.getName()
                        + " get removed from section " + cmanager
                            .currentSectionNumber());
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

    /**
     * remove command
     * 
     * @param f firstname
     * @param l lastname
     */
    public void remove(String f, String l) {
        if (cmanager.isCurrentSectionActive()) {
            Name n = new Name(f, l);
            StudentRecord sr = cmanager.remove(n);
            if (sr != null) {
                System.out.println("Student " + n + " get removed from section "
                    + cmanager.currentSectionNumber());
            }
            else {
                System.out.println("Remove failed. Student " + n
                    + " doesn't exist in section " + cmanager
                        .currentSectionNumber());
            }
        }
        else {
            System.out.println(
                "Command remove is not valid for merged sections");
        }
    }

    /**
     * clearsection command
     */
    public void clearsection() {
        cmanager.clearsection();
        System.out.println("Section "
            + cmanager.currentSectionNumber()
            + " cleared");
    }

    /**
     * dumpsection command
     */
    public void dumpsection() {
        System.out.println("section " + cmanager.currentSectionNumber()
            + " dump:");
        System.out.println("BST by ID:");
        int i = cmanager.dumpPIDs();
        System.out.println("BST by name:");
        cmanager.dumpNames();
        System.out.println("BST by score:");
        cmanager.dumpScores();
        System.out.println("Size = " + i);
    }

    /**
     * grade command
     */
    public void grade() {
        cmanager.grade();
        System.out.println("grading completed");
    }

    /**
     * stat command
     */
    public void stat() {
        String[] letters = { "A ", "A-", "B+", "B ", "B-", "C+", "C ", "C-",
            "D+", "D ", "D-", "F " };
        System.out.println("Statistics of section " + cmanager
            .currentSectionNumber() + ":");
        int[] numbers = cmanager.stat();
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
        ArrayList<StudentRecord> srl = cmanager.list(g);
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
    public void findpair(int s) {
        System.out.println("Students with score difference less than or equal "
            + s + ":");
        ArrayList<String> sl = cmanager.findpair(s);
        for (int i = 0; i < sl.size(); i++) {
            System.out.println(sl.get(i));
        }
        System.out.println("found " + sl.size() + " pairs");
    }

    /**
     * merge command
     */
    public void merge() {
        if (cmanager.merge()) {
            System.out.println("All sections merged at section " + cmanager
                .currentSectionNumber());
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section "
                    + cmanager.currentSectionNumber() + " is not empty.");
        }
    }

    /**
     * savestudentdata command
     * 
     * @param bfn binary filename
     */
    public void savestudentdata(String bfn) {
        System.out.println("Saved all Students data to " + bfn);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BinFileHelper bfh = new BinFileHelper();
            baos.write("VTSTUDENTS".getBytes());
            baos.write(bfh.intToByteArray(smanager.size()));
            Iterator<Student> itr = smanager.iterator();
            if (!smanager.isEmpty()) {
                while (itr.hasNext()) {
                    Student s = itr.next();
                    baos.write(bfh.longToByteArray(s.getPID()));
                    baos.write(s.getFirst().getBytes());
                    baos.write("$".getBytes());
                    String m = s.getMiddle();
                    if (m != null) {
                        baos.write(m.getBytes());
                    }
                    baos.write("$".getBytes());
                    baos.write(s.getLast().getBytes());
                    baos.write("$".getBytes());
                    baos.write("GOHOKIES".getBytes());
                }
            }
            bfh.byteArrayToBinFile(baos.toByteArray(), bfn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        cmanager.loadstudentdata();
    }

    /**
     * savecoursedata command
     * 
     * @param bfn binaryfilename
     */
    public void savecoursedata(String bfn) {
        System.out.println("Saved all course data to " + bfn);
        BinFileHelper bfh = new BinFileHelper();
        byte[] ba = cmanager.savecoursedata();
        bfh.byteArrayToBinFile(ba, bfn);
    }

    /**
     * clearcoursedata command
     */
    public void clearcoursedata() {
        System.out.println("All course data cleared.");
        cmanager.clearcoursedata();
    }
}
