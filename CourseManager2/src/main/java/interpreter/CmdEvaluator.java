package interpreter;

import java.util.List;

import course.CourseManager;
import course.enrollment.CourseEnrollment;
import course.enrollment.file.EnrollmentFile;
import course.section.Section;
import course.section.SectionEnrollment;
import grade.*;
import identity.*;
import identity.file.IdentityFile;
import student.*;

/**
 * CmdEvaluator Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2021-01-05
 */
public class CmdEvaluator {
    private final IdentityManager iManager;
    private final CourseManager cManager;
    
    private Student gradableStudent;
    private boolean isStudentGradable;

    public CmdEvaluator() {
        iManager = new IdentityManager();
        cManager = new CourseManager();
        
        gradableStudent = null;
        isStudentGradable = false;
    }

    /**
     * 
     * @param filename
     */
    public void loadStudentData(String filename) {
    	makeStudentUngradable();
    	for (Identity studentIdentity : IdentityFile.readFrom(filename)) {
    		iManager.insert(studentIdentity);
    	}
        System.out.println(filename + " successfully loaded");
    }
    
    private void makeStudentUngradable() {
    	isStudentGradable = false;
    }
    
    /**
     * 
     * @param filename
     */
    public void loadCourseData(String filename) {
    	makeStudentUngradable();
    	if (iManager.isEmpty()) {
            System.out.println(
            	"Course Load Failed." +
            	" You have to load Student Information file first."
            );
            return;
    	}
    	
    	String file = filename.substring(0, filename.lastIndexOf('.'));
    	System.out.println(file + " Course has been successfully loaded.");
    	
    	Section current = cManager.getCmdSection(); // TODO
        loadCourseData(EnrollmentFile.readFrom(filename));
        cManager.setCmdSection(current.getNumber());
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
        
        Integer eSectionNumber = cManager.findEnrollment(student.getPersonalID());
        if ((eSectionNumber != null) && (eSectionNumber != sectionNum)) {
            System.out.println(
                	"Warning: Student " +
                	fullName +
                	" is not loaded to section " +
                	sectionNum +
                	" since he/she is already enrolled in section " +
                	eSectionNumber
            );
            return;
        }
        
        cManager.setCmdSection(sectionNum);
        if (eSectionNumber == null) {
        	cManager.insert(student);
        }
        else {
        	cManager.updateGrade(student.getPersonalID(), student.getGrade());
        }
    }
    
    /**
     * 
     * @param sectionNum
     */
    public void section(int sectionNum) {
    	makeStudentUngradable();
    	if (!cManager.isValidSectionNumber(sectionNum)) {
    		return;
    	}
    	
        cManager.setCmdSection(sectionNum);
        System.out.println(
        	"switch to section " +
            getNumber(cManager.getCmdSection())
        );
    }
    
    private int getNumber(Section section) {
    	return section.getNumber();
    }
    
    /**
     * 
     * @param personalID
     * @param firstName
     * @param lastName
     */
    public void insert(long personalID, String firstName, String lastName) {
    	makeStudentUngradable();
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
        	if (sNumber == getNumber(cManager.getCmdSection())) {
                System.out.println(
                	fullName +
                	" is already in section " +
                    getNumber(cManager.getCmdSection())
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
        
        // TODO maybe // Cleanup
        Identity identity = new Identity(personalID, fullName);
        Student student = new Student(identity, new Grade());
        cManager.insert(student);
        System.out.println(student.getFullName() + " inserted.");
        makeStudentGradable(student);
    }
    
    private void makeStudentGradable(Student student) {
    	gradableStudent = student;
    	isStudentGradable = true;
    }
    
    /**
     * 
     * @param personalID
     */
    public void searchID(long personalID) {
    	makeStudentUngradable();
        if (!isModifiable(cManager.getCmdSection())) {
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
        makeStudentGradable(student);
    }
    
    private boolean isModifiable(Section section) {
    	return section.isModifiable();
    }
    
    /**
     * 
     * @param firstName
     * @param lastName
     */
    public void search(String firstName, String lastName) {
    	makeStudentUngradable();
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
        	getNumber(cManager.getCmdSection())
        );
        if (students.size() == 1) {
        	makeStudentGradable(students.get(0));
        }
    }
    
    /**
     * 
     * @param name
     */
    public void search(String name) {
    	makeStudentUngradable();
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
        	getNumber(cManager.getCmdSection())
        );
        if (students.size() == 1) {
        	makeStudentGradable(students.get(0));
        }
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
        
        if (!isStudentGradable) {
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
        
        Student student = cManager.updatePercentageGrade(gradableStudent.getPersonalID(), percentageGrade);
        System.out.println(
        	"Update " +
        	student.getFullName() +
        	" record, score = " +
        	student.getPercentageGrade()
        );
        makeStudentUngradable();
    }
    
    /**
     * 
     * @param personalID
     */
    public void remove(long personalID) {
    	makeStudentUngradable();
        if (!cManager.isModifiable(cManager.getCmdSection())) {
        	System.out.println("Command remove is not valid for merged sections");
        	return;
        }
        
        // TODO // Condense if statements
        if (iManager.find(personalID) == null) {
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
        	getNumber(cManager.getCmdSection())
        );
    }
    
    /**
     * 
     * @param firstName
     * @param lastName
     */
    public void remove(String firstName, String lastName) {
    	makeStudentUngradable();
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
            	getNumber(cManager.getCmdSection())
            );
            return;
        }
        
        System.out.println(
        	"Student " +
        	fullName +
        	" get removed from section " +
            getNumber(cManager.getCmdSection())
        );
    }
    
    /**
     * 
     */
    public void clearSection() {
    	makeStudentUngradable();
        cManager.clear();
        
        System.out.println(
        	"Section " +
            getNumber(cManager.getCmdSection()) +
            " cleared"
        );
    }

    /**
     * 
     */
    public void dumpSection() {
    	makeStudentUngradable();
        System.out.println(
        	"section " +
        	getNumber(cManager.getCmdSection()) +
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
    	makeStudentUngradable();
        cManager.updateLetterGrades();
        System.out.println("grading completed");
    }
    
    /**
     * 
     */
    public void stat() {
    	makeStudentUngradable();
        System.out.println(
        	"Statistics of section " +
        	getNumber(cManager.getCmdSection()) +
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
    	makeStudentUngradable();
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
    	makeStudentUngradable();
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
    	makeStudentUngradable();
        if (cManager.mergeSections()) {
            System.out.println(
            	"All sections merged at section " +
            	getNumber(cManager.getCmdSection())
            );
        }
        else {
            System.out.println(
                "Sections could only be merged to an empty section. Section " +
                getNumber(cManager.getCmdSection()) +
                " is not empty."
            );
        }
    }

    public void saveStudentData(String filename) {
    	makeStudentUngradable();
    	IdentityFile.writeTo(iManager.list(), filename);
        System.out.println("Saved all Students data to " + filename);
    }

    /**
     * 
     * @param filename
     */
    public void saveCourseData(String filename) {
    	makeStudentUngradable();
        EnrollmentFile.writeTo(cManager.getEnrollment(), filename);
        System.out.println("Saved all course data to " + filename);
    }

    /**
     * 
     */
    public void clearCourseData() {
    	makeStudentUngradable();
    	cManager.clearSections();
        System.out.println("All course data cleared.");
    }
}
