package interpreter;

import java.util.ArrayList;

import course.CourseManager;
import course.EnrollmentFile;
import course.IdentityFile;
import course.IdentityManager;
import data.*;

/**
 * CmdEvaluator Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class CmdEvaluator {
    private IdentityManager studentManager;
    private CourseManager courseManager;
    private boolean isStudentDataLoaded;

    public CmdEvaluator() {
        studentManager = new IdentityManager();
        courseManager = new CourseManager();
        isStudentDataLoaded = false;
    }

    public void loadStudentData(String filename) {
    	Identity[] studentIdentities = IdentityFile.readFrom(filename);
    	for (int i = 0; i < studentIdentities.length; i++) {
    		studentManager.insert(studentIdentities[i]);
    	}
    	isStudentDataLoaded = true;
        courseManager.loadstudentdata();
        System.out.println(filename + " successfully loaded");
    }
    
    public void loadCourseData(String filename) {
    	if (!isStudentDataLoaded) {
            System.out.println("Course Load Failed."
            		+ " You have to load Student Information file first.");
            return;
    	}
    	
    	String file = filename.substring(0, filename.lastIndexOf('.'));
    	System.out.println(file
    			+ " Course has been successfully loaded.");
    	
        CourseEnrollment cEnrollment = EnrollmentFile.readFrom(filename);
        loadSectionsData(cEnrollment.getSectionEnrollments());
    }
    
    private void loadSectionsData(SectionEnrollment[] sEnrollments) {
    	for (int i = 0; i < sEnrollments.length; ++i) {
    		int sNumber = sEnrollments[i].getSectionNumber();
    		Student[] sStudents = sEnrollments[i].getStudents();
    		for (int j = 0; j < sStudents.length; ++j) {
    			loadCourseDataHelper(sNumber, sStudents[j]);
    		}
    	}
    }
    
    public void loadCourseDataHelper(int sectionNumber, Student newStudent) {
        Identity studentIdentity = studentManager.findIdentity(newStudent.getPersonalID());
        if (studentIdentity == null) { // Check if id exists
            System.out.println("Warning: Student "
            		+ newStudent.getFullName()
            		+ " is not loaded to section "
            		+ sectionNumber
            		+ " since he/she doesn't exist in the loaded student records.");
            return;
        }
        
        FullName fullName = newStudent.getFullName();
        if (studentIdentity.getFullName().compareTo(fullName) != 0) {
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

    public boolean section(int sectionNumber) {
    	if (!courseManager.isValidSection(sectionNumber)) {
    		return false;
    	}
    	
        courseManager.setSection(sectionNumber);
        System.out.println("switch to section "
            + courseManager.getSectionNumber());
        return true;
    }
    
    public void insert(long personalID, String firstName, String lastName) {
        if (!courseManager.isSectionActive()) {
        	System.out.println("Command insert is not valid for merged sections");
        	return;
        }
        
        Identity studentIdentity = studentManager.findIdentity(personalID);
        if (studentIdentity == null) { // Check if id exists
            System.out.println(firstName + " " + lastName
                    + " insertion failed. Wrong student information."
                    + " ID doesn't exist");
            return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        if (studentIdentity.getFullName().compareTo(fullName) != 0) { // Check if name matches
        	System.out.println(fullName
                    + " insertion failed. Wrong student information."
                    + " ID belongs to another student");
        	return;
        }
        
        Integer sectionNumber = courseManager.findStudentSection(personalID);
        if (sectionNumber != null) { // Check if student enrolled
        	if (sectionNumber == courseManager.getSectionNumber()) {
                System.out.println(fullName
                		+ " is already in section "
                        + courseManager.getSectionNumber());
        	}
        	else {
            	System.out.println(fullName
                        + " is already registered in a different section");
        	}
        	return;
        }
        
        Identity recordIdentity = new Identity(personalID, fullName);
        Student record = courseManager.insert(new Student(recordIdentity, new Grade()));
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
        		+ courseManager.getSectionNumber());
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
        		+ courseManager.getSectionNumber());
    }
    
    public void score(int percentageGrade) {
        if (!courseManager.isSectionActive()) {
            System.out.println("Command score is not valid for merged sections");
            return;
        }
        
        if (!courseManager.isStudentScorable()) {
            System.out.println("score command can only be called after an insert command"
                    + " or a successful search command with one exact output.");
            return;
        }
        
        if (!CourseManager.isValidPercentageGrade(percentageGrade)) {
            System.out.println("Scores have to be integers in range 0 to 100.");
            return;
        }
        
        Student student = courseManager.scoreStudent(percentageGrade);
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
        
        Identity studentIdentity = studentManager.findIdentity(personalID);
        if (studentIdentity == null) {
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
        		+ courseManager.getSectionNumber());
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
            		+ courseManager.getSectionNumber());
            return;
        }
        
        System.out.println("Student "
        		+ fullName
        		+ " get removed from section "
                + courseManager.getSectionNumber());
    }

    public void clearSection() {
        courseManager.clearSection();
        System.out.println("Section "
            + courseManager.getSectionNumber()
            + " cleared");
    }

    public void dumpSection() {
        System.out.println("section "
        		+ courseManager.getSectionNumber()
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
        		+ courseManager.getSectionNumber() + ":");
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
                .getSectionNumber());
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section "
                    + courseManager.getSectionNumber() + " is not empty.");
        }
    }

    public void saveStudentData(String filename) {
    	Identity[] identities = studentManager.getIdentities();
    	IdentityFile.writeTo(identities, filename);
        System.out.println("Saved all Students data to " + filename);
        courseManager.loadstudentdata();
    }

    public void saveCourseData(String filename) {
        EnrollmentFile.writeTo(courseManager.getEnrollment(), filename);
        System.out.println("Saved all course data to " + filename);
//        BinFileHelper bfh = new BinFileHelper();
//        byte[] ba = courseManager.saveCourseData();
//        bfh.byteArrayToBinFile(ba, bfn);
    }

    public void clearCourseData() {
        System.out.println("All course data cleared.");
        courseManager.clearcoursedata();
    }
}
