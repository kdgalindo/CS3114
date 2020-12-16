package interpreter;

import java.util.List;

import course.CourseManager;
import course.enrollment.CourseEnrollment;
import course.enrollment.file.EnrollmentFile;
import course.section.SectionEnrollment;
import grade.*;
import identity.*;
import identity.file.IdentityFile;
import student.*;

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

    /**
     * 
     * @param filename
     */
    public void loadStudentData(String filename) {
    	for (Identity studentIdentity : IdentityFile.readFrom(filename)) {
    		iManager.insert(studentIdentity);
    	}
    	isStudentDataLoaded = true;
        cManager.makeStudentUngradable();
        System.out.println(filename + " successfully loaded");
    }
    
    /**
     * 
     * @param filename
     */
    public void loadCourseData(String filename) {
    	if (!isStudentDataLoaded) { // TODO iManager.isEmpty
            System.out.println(
            	"Course Load Failed." +
            	" You have to load Student Information file first."
            );
            return;
    	}
    	
    	String file = filename.substring(0, filename.lastIndexOf('.'));
    	System.out.println(file + " Course has been successfully loaded.");
    	
        loadCourseData(EnrollmentFile.readFrom(filename));
    }
    
    private void loadCourseData(CourseEnrollment cEnrollment) {
    	for (SectionEnrollment sEnrollment : cEnrollment.getSectionEnrollments()) {
    		for (Student sStudent : sEnrollment.getStudents()) {
    			loadStudentData(sStudent, sEnrollment.getSectionNumber());
    		}
    	}
    }
    
    private void loadStudentData(Student student, int sectionNum) {
    	FullName fullName = student.getFullName();
        Identity sIdentity = iManager.find(student.getPersonalID());
        if (sIdentity == null) {
            System.out.println("Warning: Student " +
            	fullName +
            	" is not loaded to section " +
            	sectionNum +
            	" since he/she doesn't exist in the loaded student records."
            );
            return;
        }
        
        FullName sFullName = sIdentity.getFullName();
        if (sFullName.compareTo(fullName) != 0) { // TODO
            System.out.println(
            	"Warning: Student " +
            	fullName +
            	" is not loaded to section " +
            	sectionNum +
            	" since the corresponding pid belongs to another student."
            );
            return;
        }
        
        Student sStudent = cManager.insert(student, sectionNum);
        if (sStudent == null) {
            System.out.println(
            	"Warning: Student " +
            	fullName +
            	" is not loaded to section " +
            	sectionNum +
            	" since he/she is already enrolled in section " +
            	cManager.findEnrollment(student.getPersonalID())
            );
            return;
        }
    }
    
    /**
     * 
     * @param sectionNum
     */
    public void section(int sectionNum) {
    	if (!cManager.isValidSectionNum(sectionNum)) {
    		return;
    	}
    	
        cManager.setCmdSection(sectionNum);
        System.out.println(
        	"switch to section " +
            cManager.getSectionNum(cManager.getCmdSection()) // TODO
        );
    }
    
    /**
     * 
     * @param personalID
     * @param firstName
     * @param lastName
     */
    public void insert(long personalID, String firstName, String lastName) {
        if (!cManager.isModifiable(cManager.getCmdSection())) {
        	System.out.println("Command insert is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        Identity sIdentity = iManager.find(personalID);
        if (sIdentity == null) {
            System.out.println(
            	fullName +
                " insertion failed. Wrong student information." +
            	" ID doesn't exist"
            );
            return;
        }
        
        FullName sFullName = sIdentity.getFullName();
        if (sFullName.compareTo(fullName) != 0) {
        	System.out.println(
        		fullName +
                " insertion failed. Wrong student information." +
                " ID belongs to another student"
            );
        	return;
        }
        
        Integer sNumber = cManager.findEnrollment(personalID);
        if (sNumber != null) {
        	if (sNumber == cManager.getSectionNum(cManager.getCmdSection())) { // TODO
                System.out.println(
                	fullName +
                	" is already in section " +
                    cManager.getSectionNum(cManager.getCmdSection()) // TODO
                );
        	}
        	else {
            	System.out.println(
            		fullName +
                    " is already registered in a different section"
            	);
        	}
        	return;
        }
        
        Identity identity = new Identity(personalID, fullName);
        Student student = cManager.insert(new Student(identity, new Grade()));
        System.out.println(student.getFullName() + " inserted.");
    }
    
    /**
     * 
     * @param personalID
     */
    public void searchID(long personalID) {
        if (!cManager.isModifiable(cManager.getCmdSection())) { // TODO
        	System.out.println("Command searchid is not valid for merged sections");
        	return;
        }
        
        Student student = cManager.find(personalID);
        if (student == null) {
            System.out.println(
            	"Search Failed. Couldn't find any student with ID " +
                String.format("%09d", personalID)
            );
            return;
        }
        
        System.out.println("Found " + student);
    }
    
    /**
     * 
     * @param firstName
     * @param lastName
     */
    public void search(String firstName, String lastName) {
        if (!cManager.isModifiable(cManager.getCmdSection())) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        System.out.println("search results for " + fullName + ":");
        List<Student> students = cManager.find(fullName);
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println(
        	fullName +
        	" was found in " +
        	students.size() +
        	" records in section " +
        	cManager.getSectionNum(cManager.getCmdSection())
        );
    }
    
    /**
     * 
     * @param name
     */
    public void search(String name) {
        if (!cManager.isModifiable(cManager.getCmdSection())) {
        	System.out.println("Command search is not valid for merged sections");
        	return;
        }
        
        System.out.println("search results for " + name + ":");
        List<Student> students = cManager.find(name);
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println(
        	name +
        	" was found in " +
        	students.size() +
        	" records in section " +
        	cManager.getSectionNum(cManager.getCmdSection())
        );
    }
    
    /**
     * 
     * @param percentageGrade
     */
    public void score(int percentageGrade) {
        if (!cManager.isModifiable(cManager.getCmdSection())) {
            System.out.println("Command score is not valid for merged sections");
            return;
        }
        
        if (!cManager.isStudentGradable()) {
            System.out.println(
            	"score command can only be called after an insert command" +
                " or a successful search command with one exact output."
            );
            return;
        }
        
        if (!Grader.isValidPercentageGrade(percentageGrade)) {
            System.out.println("Scores have to be integers in range 0 to 100.");
            return;
        }
        
        Student student = cManager.updatePercentageGrade(percentageGrade);
        System.out.println(
        	"Update " +
        	student.getFullName() +
        	" record, score = " +
        	student.getPercentageGrade()
        );
    }
    
    /**
     * 
     * @param personalID
     */
    public void remove(long personalID) {
        if (!cManager.isModifiable(cManager.getCmdSection())) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        Identity sIdentity = iManager.find(personalID);
        if (sIdentity == null) {
        	System.out.println(
        		"Remove failed: couldn't find any student with id " +
        		personalID
        	);
        	return;
        }
        
        Student student = cManager.remove(personalID);
        if (student == null) {
            System.out.println(
            	"Remove failed: couldn't find any student with id " +
            	personalID
            );
            return;
        }
        
        System.out.println(
        	"Student " +
        	student.getFullName() +
        	" get removed from section " +
        	cManager.getSectionNum(cManager.getCmdSection())
        );
    }
    
    /**
     * 
     * @param firstName
     * @param lastName
     */
    public void remove(String firstName, String lastName) {
        if (!cManager.isModifiable(cManager.getCmdSection())) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        FullName fullName = new FullName(firstName, lastName);
        Student student = cManager.remove(fullName);
        if (student == null) {
            System.out.println(
            	"Remove failed. Student " +
            	fullName +
                " doesn't exist in section " +
            	cManager.getSectionNum(cManager.getCmdSection())
            );
            return;
        }
        
        System.out.println(
        	"Student " +
        	fullName +
        	" get removed from section " +
            cManager.getSectionNum(cManager.getCmdSection())
        );
    }
    
    /**
     * 
     */
    public void clearSection() {
        cManager.clear();
        
        System.out.println(
        	"Section " +
            cManager.getSectionNum(cManager.getCmdSection()) +
            " cleared"
        );
    }

    /**
     * 
     */
    public void dumpSection() {
        System.out.println(
        	"section " +
        	cManager.getSectionNum(cManager.getCmdSection()) +
        	" dump:"
        );
        
        System.out.println("BST by ID:");
        List<Student> students = cManager.listInPersonalIDOrder();
        dump(students);
        System.out.println("BST by name:");
        dump(cManager.listInFullNameOrder());
        System.out.println("BST by score:");
        dump(cManager.listInPercentageGradeOrder());
        System.out.println("Size = " + students.size());
    }
    
    private void dump(List<Student> students) {
    	for (Student student : students) {
    		System.out.println(student);
    	}
    }
    
    /**
     * 
     */
    public void grade() {
        cManager.updateGrades();
        System.out.println("grading completed");
    }
    
    /**
     * 
     */
    public void stat() {
        System.out.println(
        	"Statistics of section " +
        	cManager.getSectionNum(cManager.getCmdSection()) +
        	":"
        );
        
        for (String s : cManager.listGradeLevelStats()) { // TODO
        	System.out.println(s);
        }
    }
    
    /**
     * 
     * @param letter
     */
    public void list(String letter) {
        System.out.println("Students with grade " + letter + " are:");
        List<Student> students = cManager.listInGradeLevel(letter);
        for (Student student : students) {
            System.out.println(
            	student +
            	", grade = " +
            	student.getLetterGrade()
            );
        }
        
        System.out.println("Found " + students.size() + " students");
    }

    /**
     * 
     * @param percentageDiff
     */
    public void findPair(int percentageDiff) {
        System.out.println(
        	"Students with score difference less than or equal " +
        	percentageDiff +
        	":"
        );
        
        List<String> strings = cManager.listStudentPairsWithin(percentageDiff);
        for (String s : strings) { // TODO
            System.out.println(s);
        }
        
        System.out.println("found " + strings.size() + " pairs");
    }

    /**
     * 
     */
    public void merge() {
        if (cManager.mergeSections()) {
            System.out.println(
            	"All sections merged at section " +
            	cManager.getSectionNum(cManager.getCmdSection())
            );
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section " +
                cManager.getSectionNum(cManager.getCmdSection()) +
                " is not empty."
            );
        }
    }

    public void saveStudentData(String filename) {
    	IdentityFile.writeTo(iManager.list(), filename);
        System.out.println("Saved all Students data to " + filename);
        cManager.makeStudentUngradable();
    }

    /**
     * 
     * @param filename
     */
    public void saveCourseData(String filename) {
        EnrollmentFile.writeTo(cManager.getEnrollment(), filename);
        System.out.println("Saved all course data to " + filename);
    }

    /**
     * 
     */
    public void clearCourseData() {
    	cManager.clearSections();
        System.out.println("All course data cleared.");
    }
}
