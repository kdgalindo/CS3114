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
 * @version 2020-08-13
 */
public class CourseManager {
	private final int MAX_SECTION_NUMBER = 21;
    private Section[] sections;
    private Section currentSection;
    private ArrayList<StudentRecord> srecords; // list of student records
    private BST<Long, Integer> pbst; // BST w/ id key & section value
    private BST<String, Integer> gbst; // BST w/ grade key & index value
    private Integer gindex; // index of gradable student record

    /**
     * CourseManager default constructor
     */
    public CourseManager() {
        sections = new Section[MAX_SECTION_NUMBER];
        for (int i = 0; i < MAX_SECTION_NUMBER; i++) {
        	int sectionNumber = i + 1;
            sections[i] = new Section(sectionNumber);
        }
        currentSection = sections[0];
        pbst = new BST<Long, Integer>();
        gBST();
        srecords = new ArrayList<StudentRecord>();
        gindex = null;
    }

    /**
     * Initializes the gbst of grade keys
     * and index values
     */
    private void gBST() {
        gbst = new BST<String, Integer>();
        gbst.insert("a*", 0);
        gbst.insert("a", 0);
        gbst.insert("a-", 2);
        gbst.insert("b*", 4);
        gbst.insert("b+", 4);
        gbst.insert("b", 6);
        gbst.insert("b-", 8);
        gbst.insert("c*", 10);
        gbst.insert("c+", 10);
        gbst.insert("c", 12);
        gbst.insert("c-", 14);
        gbst.insert("d*", 16);
        gbst.insert("d+", 16);
        gbst.insert("d", 18);
        gbst.insert("d-", 20);
        gbst.insert("f*", 22);
        gbst.insert("f", 22);
    }

    public int getSection() {
        return currentSection.getNumber();
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
        return pbst.find(p);
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
        Integer csn = pbst.find(p); // current section number
        if (csn == null) { // Check if in section
            sr = nsr;
            sections[sn - 1].insertStudent(sr.getStudent(), sr.getScore(),
                srecords.size()); // Add to section
            pbst.insert(p, sn); // Associate with section
            srecords.add(sr); // Add to student record list
        }
        else if (csn == sn) { // Check if in current section
            sr = nsr;
            int srli = sections[sn - 1].findStudent(sr.getPID());
            StudentRecord osr = srecords.get(srli);
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

    /**
     * Insert a student into the
     * current section
     * 
     * @param s student
     * @return student record if not
     * already in section, null otherwise
     */
    public StudentRecord insert(Student s) {
        gindex = null;
        StudentRecord nsr = null;
        long p = s.getPersonalID(); // student pid
        Integer sn = pbst.find(p); // section number
        if (sn == null) { // Check if in section
            nsr = new StudentRecord(s);
            currentSection.insertStudent(nsr.getStudent(), nsr.getScore(), srecords
                .size()); // Add to section
            pbst.insert(p, currentSection.getNumber()); // Associate with section
            srecords.add(nsr); // Add to student record list
            gindex = srecords.size() - 1;
        }
        return nsr;
    }

    /**
     * Search for a student record by
     * pid
     * 
     * @param p student pid
     * @return student record if found,
     * null otherwise
     */
    public StudentRecord searchid(long p) {
        gindex = null;
        StudentRecord sr = null;
        Integer srli = currentSection.findStudent(p); // srecord index
        if (srli != null) { // Check if in section
            sr = srecords.get(srli);
            gindex = srli;
        }
        return sr;
    }

    /**
     * Search for student records by
     * name
     * 
     * @param n name
     * @return student record list
     */
    public ArrayList<StudentRecord> search(FullName n) {
        gindex = null;
        ArrayList<StudentRecord> nsrl = new ArrayList<StudentRecord>();
        ArrayList<Integer> il = currentSection.searchByName(n); // srecord indices
        for (int i = 0; i < il.size(); i++) {
            nsrl.add(srecords.get(il.get(i)));
        }
        if (il.size() == 1) {
            gindex = il.get(0);
        }
        return nsrl;
    }

    /**
     * Search for student records by
     * string
     * 
     * @param s string
     * @return student record list
     */
    public ArrayList<StudentRecord> search(String s) {
        gindex = null;
        ArrayList<StudentRecord> nsrl = new ArrayList<StudentRecord>();
        Iterator<Integer> itr = currentSection.iterateByName();
        if (!currentSection.isEmpty()) {
            while (itr.hasNext()) {
                int i = itr.next();
                StudentRecord sr = srecords.get(i);
                String f = sr.getFirst();
                String l = sr.getLast();
                if (f.equalsIgnoreCase(s) || l.equalsIgnoreCase(s)) {
                    nsrl.add(sr);
                    if (nsrl.size() == 1) {
                        gindex = i;
                    }
                }
            }
        }
        if (nsrl.size() != 1) {
            gindex = null;
        }
        return nsrl;
    }

    /**
     * Update the score of a
     * student record
     * 
     * @param n number
     * @return student record if found,
     * null otherwise
     */
    public StudentRecord score(int n) {
        StudentRecord sr = null;
        if (gindex != null) {
            sr = srecords.get(gindex);
            currentSection.updateStudentScore(sr.getScore(), n, gindex);
            sr.setScore(n);
            gindex = null;
        }
        return sr;
    }

    /**
     * Remove a student record by
     * pid
     * 
     * @param p student pid
     * @return student record if found,
     * null otherwise
     */
    public StudentRecord remove(long p) {
        gindex = null;
        StudentRecord sr = null;
        Integer sn = pbst.find(p); // section number
        if ((sn != null) && (sn == currentSection.getNumber())) {
            int i = currentSection.removeStudentPID(p);
            sr = srecords.get(i);
            currentSection.removeStudentName(sr.getName(), i);
            currentSection.removeStudentScore(sr.getScore(), i);
            pbst.remove(p); // Disassociate with section
            sr.setActive(false); // Kill the student record
        }
        return sr;
    }

    /**
     * Remove a student record by
     * name
     * 
     * @param n name
     * @return student record if found,
     * null otherwise
     */
    public StudentRecord remove(FullName n) {
        gindex = null;
        StudentRecord sr = null;
        ArrayList<Integer> il = currentSection.searchByName(n);
        if (il.size() == 1) { // Check if name unique
            int i = currentSection.removeStudentName(n);
            sr = srecords.get(i);
            currentSection.removeStudentPID(sr.getPID());
            currentSection.removeStudentScore(sr.getScore(), i);
            pbst.remove(sr.getPID()); // Disassociate with section
            sr.setActive(false); // Kill the student record
        }
        return sr;
    }

    /**
     * Clears the current section
     * from CourseManager
     */
    public void clearsection() {
        gindex = null;
        currentSection.clear();
    }

    /**
     * Dumps an inorder traversal of
     * all the student records in the
     * current section by pid
     * 
     * @return number of records
     */
    public int dumpPIDs() {
        gindex = null;
        Iterator<Integer> itr = currentSection.iterateByPID();
        if (!currentSection.isEmpty()) {
            while (itr.hasNext()) {
                StudentRecord sr = srecords.get(itr.next());
                System.out.println(sr);
            }
        }
        return currentSection.size();
    }

    /**
     * Dumps an inorder traversal of
     * all the student records in the
     * current section by name
     * 
     * @return number of records
     */
    public int dumpNames() {
        gindex = null;
        Iterator<Integer> itr = currentSection.iterateByName();
        if (!currentSection.isEmpty()) {
            while (itr.hasNext()) {
                StudentRecord sr = srecords.get(itr.next());
                System.out.println(sr);
            }
        }
        return currentSection.size();
    }

    /**
     * Dumps an inorder traversal of
     * all the student records in the
     * current section by score
     * 
     * @return number of records
     */
    public int dumpScores() {
        gindex = null;
        Iterator<Integer> itr = currentSection.iterateByScore();
        if (!currentSection.isEmpty()) {
            while (itr.hasNext()) {
                StudentRecord sr = srecords.get(itr.next());
                System.out.println(sr);
            }
        }
        return currentSection.size();
    }

    /**
     * Updates the letter grades
     * of all student records in
     * the current section
     */
    public void grade() {
        gindex = null;
        String[] letters = { "A ", "A-", "B+", "B ", "B-", "C+", "C ", "C-",
            "D+", "D ", "D-", "F " };
        Iterator<Integer> itr = currentSection.iterateByScore();
        if (!currentSection.isEmpty()) {
            while (itr.hasNext()) {
                int i = itr.next();
                StudentRecord sr = srecords.get(i);
                int s = sr.getScore();
                sr.setGrade(letters[gradehelper(s)]);
            }
        }
    }

    /**
     * Helps update the grades of all
     * student records in the current
     * section by obtaining the index
     * of the letter grade
     * 
     * @param s score
     * @return letter grade index
     */
    private int gradehelper(int s) {
        gindex = null;
        int li = 11;
        int[] ranges = { 90, 85, 80, 75, 70, 65, 60, 58, 55, 53, 50 };
        for (int i = 0; i < 11; i++) {
            if (s >= ranges[i]) {
                li = i;
                break;
            }
        }
        return li;
    }

    /**
     * Lists the number of students
     * with each letter grade in the
     * current section
     * 
     * @return array of numbers
     */
    public int[] stat() {
        gindex = null;
        int[] numbers = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        Iterator<Integer> itr = currentSection.iterateByScore();
        if (!currentSection.isEmpty()) {
            while (itr.hasNext()) {
                int i = itr.next();
                StudentRecord sr = srecords.get(i);
                int s = sr.getScore();
                numbers[gradehelper(s)]++;
            }
        }
        return numbers;
    }

    /**
     * Lists the student records with
     * letter grade g in the current
     * section
     * 
     * @param g grade
     * @return list of student records
     */
    public ArrayList<StudentRecord> list(String g) {
        gindex = null;
        int[] granges = { 100, 90, 89, 85, 84, 80, 79, 75, 74, 70, 69, 65, 64,
            60, 59, 58, 57, 55, 54, 53, 52, 50, 49, 0 };
        ArrayList<StudentRecord> nsrl = new ArrayList<StudentRecord>();
        String ng = g.toLowerCase();
        ArrayList<Integer> il;
        int i1 = gbst.find(ng);
        int i2 = listhelper(ng, i1);
        il = currentSection.searchForScoresInRange(granges[i2], granges[i1]);
        for (int i = 0; i < il.size(); i++) {
            nsrl.add(srecords.get(il.get(i)));
        }
        return nsrl;
    }

    /**
     * Helps list the student records
     * with letter grade g in the current
     * section by obtaining the index of
     * the lower bound
     * 
     * @param g grade
     * @param i1 index of higher
     * @return index of lower
     */
    private int listhelper(String g, int i1) {
        int i2 = i1;
        if (g.contains("*")) {
            if (g.contains("a")) {
                i2 = i1 + 3;
            }
            else if (!g.contains("f")) {
                i2 = i1 + 5;
            }
        }
        else {
            i2 = i1 + 1;
        }
        return i2;
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
        Iterator<Integer> itr1 = currentSection.iterateByScore();
        int i = 0;
        if (!currentSection.isEmpty()) {
            while (itr1.hasNext()) {
                StudentRecord sr1 = srecords.get(itr1.next());
                Iterator<Integer> itr2 = currentSection.iterateByScore();
                for (int j = 0; j < i; j++) {
                    itr2.next();
                }
                while (itr2.hasNext()) {
                    StudentRecord sr2 = srecords.get(itr2.next());
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
                    Iterator<Integer> itr = sections[i].iterateByPID();
                    if (!sections[i].isEmpty()) {
                        while (itr.hasNext()) {
                            int j = itr.next();
                            StudentRecord sr = srecords.get(j);
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
                Iterator<Integer> itr = sections[i].iterateByPID();
                if (!sections[i].isEmpty() && sections[i].isActive()) {
                    baos.write(bfh.intToByteArray(sections[i].size()));
                    while (itr.hasNext()) {
                        int j = itr.next();
                        StudentRecord sr = srecords.get(j);
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
        srecords.clear();
        pbst.clear();
    }
}
