package interpreter;

import java.util.ArrayList;

import course.CourseManager;
import course.enrollment.CourseEnrollment;
import course.enrollment.file.EnrollmentFile;
import course.section.SectionEnrollment;
import data.*;
import grade.Grade;
import identity.*;
import identity.file.IdentityFile;

/**
 * CmdEvaluator Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class CmdEvaluator {
    private IdentityManager iManager;
    private CourseManager cManager;
    private boolean isStudentDataLoaded;

    public CmdEvaluator() {
        iManager = new IdentityManager();
        cManager = new CourseManager();
        isStudentDataLoaded = false;
    }

    public void loadStudentData(String filename) {
    	Identity[] studentIdentities = IdentityFile.readFrom(filename);
    	for (int i = 0; i < studentIdentities.length; i++) {
    		iManager.insert(studentIdentities[i]);
    	}
    	isStudentDataLoaded = true;
        cManager.clearStudentScorable();
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
        Identity studentIdentity = iManager.findIdentity(newStudent.getPersonalID());
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
        
        Student record = cManager.insert(newStudent, sectionNumber);
        if (record == null) { // Check if student enrolled
            System.out.println("Warning: Student " + newStudent.getFullName()
                + " is not loaded to section " + sectionNumber
                + " since he/she is already enrolled in section "
                + cManager.findEnrollment(newStudent.getPersonalID()));
        }
    }

    public boolean section(int sectionNumber) {
    	if (!cManager.isValidSectionNum(sectionNumber)) {
    		return false;
    	}
    	
        cManager.setCommandableSection(sectionNumber);
        System.out.println("switch to section "
            + cManager.getSectionNum(cManager.getCommandableSection()));
        return true;
    }
    
    public void insert(long personalID, String firstName, String lastName) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
        	System.out.println("Command insert is not valid for merged sections");
        	return;
        }
        
        Identity studentIdentity = iManager.findIdentity(personalID);
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
        
        Integer sectionNumber = cManager.findEnrollment(personalID);
        if (sectionNumber != null) { // Check if student enrolled
        	if (sectionNumber == cManager.getSectionNum(cManager.getCommandableSection())) {
                System.out.println(fullName
                		+ " is already in section "
                        + cManager.getSectionNum(cManager.getCommandableSection()));
        	}
        	else {
            	System.out.println(fullName
                        + " is already registered in a different section");
        	}
        	return;
        }
        
        Identity recordIdentity = new Identity(personalID, fullName);
        Student record = cManager.insert(new Student(recordIdentity, new Grade()));
        System.out.println(record.getFullName() + " inserted.");
    }
    
    public void searchID(long personalID) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
        	System.out.println("Command searchid is not valid for merged sections");
        	return;
        }
        
        Student student = cManager.find(personalID);
        if (student == null) {
            System.out.println("Search Failed. Couldn't find any student with ID "
                    + String.format("%09d", personalID));
            return;
        }
        
        System.out.println("Found " + student);
    }
    
    public void search(String firstName, String lastName) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        System.out.println("search results for " + fullName + ":");
        Student[] students = cManager.find(fullName);
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
        System.out.println(fullName
        		+ " was found in "
        		+ students.length
        		+ " records in section "
        		+ cManager.getSectionNum(cManager.getCommandableSection()));
    }
    
    public void search(String name) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        System.out.println("search results for " + name + ":");
        Student[] students = cManager.find(name);
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
        }
        System.out.println(name
        		+ " was found in "
        		+ students.length
        		+ " records in section "
        		+ cManager.getSectionNum(cManager.getCommandableSection()));
    }
    
    public void score(int percentageGrade) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
            System.out.println("Command score is not valid for merged sections");
            return;
        }
        
        if (!cManager.isStudentScorable()) {
            System.out.println("score command can only be called after an insert command"
                    + " or a successful search command with one exact output.");
            return;
        }
        
        if (!CourseManager.isValidPercentageGrade(percentageGrade)) {
            System.out.println("Scores have to be integers in range 0 to 100.");
            return;
        }
        
        Student student = cManager.scoreStudent(percentageGrade);
        System.out.println("Update "
        		+ student.getFullName()
        		+ " record, score = "
        		+ student.getPercentageGrade());
    }
    
    public void remove(long personalID) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        Identity studentIdentity = iManager.findIdentity(personalID);
        if (studentIdentity == null) {
        	System.out.println("Remove failed: couldn't find any student with id "
        			+ personalID);
        	return;
        }
        
        Student record = cManager.remove(personalID);
        if (record == null) {
            System.out.println("Remove failed: couldn't find any student with id "
            		+ personalID);
            return;
        }
        
        System.out.println("Student "
        		+ record.getFullName()
        		+ " get removed from section "
        		+ cManager.getSectionNum(cManager.getCommandableSection()));
    }
    
    public void remove(String firstName, String lastName) {
        if (!cManager.isModifiable(cManager.getCommandableSection())) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        Student student = cManager.remove(fullName);
        if (student == null) {
            System.out.println("Remove failed. Student "
            		+ fullName
                    + " doesn't exist in section "
            		+ cManager.getSectionNum(cManager.getCommandableSection()));
            return;
        }
        
        System.out.println("Student "
        		+ fullName
        		+ " get removed from section "
                + cManager.getSectionNum(cManager.getCommandableSection()));
    }

    public void clearSection() {
        cManager.clear();
        System.out.println("Section "
            + cManager.getSectionNum(cManager.getCommandableSection())
            + " cleared");
    }

    public void dumpSection() {
        System.out.println("section "
        		+ cManager.getSectionNum(cManager.getCommandableSection())
        		+ " dump:");
        System.out.println("BST by ID:");
        int size = cManager.dumpPIDs();
        System.out.println("BST by name:");
        cManager.dumpNames();
        System.out.println("BST by score:");
        cManager.dumpScores();
        System.out.println("Size = " + size);
    }
    
    public void grade() {
        cManager.gradeStudents();
    }
    
    public void stat() {
        System.out.println("Statistics of section "
        		+ cManager.getSectionNum(cManager.getCommandableSection())
        		+ ":");
        cManager.statStudents();
    }
    
    public void list(String letter) {
        System.out.println("Students with grade " + letter + " are:");
        Student[] students = cManager.listStudents(letter);
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
        ArrayList<String> strings = cManager.findpair(percentageDiff);
        for (int i = 0; i < strings.size(); i++) {
            System.out.println(strings.get(i));
        }
        System.out.println("found " + strings.size() + " pairs");
    }

    public void merge() {
        if (cManager.mergeSections()) {
            System.out.println("All sections merged at section "
            		+ cManager.getSectionNum(cManager.getCommandableSection()));
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section "
                    + cManager.getSectionNum(cManager.getCommandableSection())
                    + " is not empty.");
        }
    }

    public void saveStudentData(String filename) {
    	Identity[] identities = iManager.getIdentities();
    	IdentityFile.writeTo(identities, filename);
        System.out.println("Saved all Students data to " + filename);
        cManager.clearStudentScorable();
    }

    public void saveCourseData(String filename) {
        EnrollmentFile.writeTo(cManager.getEnrollment(), filename);
        System.out.println("Saved all course data to " + filename);
    }

    public void clearCourseData() {
        System.out.println("All course data cleared.");
        cManager.clearcoursedata();
    }
}
