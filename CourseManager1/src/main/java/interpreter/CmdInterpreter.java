package interpreter;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

import course.SectionManager;
import student.Student;
import student.StudentPair;

/**
 * CmdInterpreter Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-10
 */
public class CmdInterpreter {
	private SectionManager sectionManager;
	
	public CmdInterpreter() {
		sectionManager = new SectionManager();
	}
	
    public void eval(String fn) {
        try {
            Scanner sc = new Scanner(new File(fn));
            while (sc.hasNext()) {
                String cmd = sc.next();
                switch (cmd) {
                    case "section": {
                    		int sectionNumber = sc.nextInt();
                    		evalSection(sectionNumber);
                    	}
                        break;
                        
                    case "insert": {
                        	String firstName = sc.next();
                        	String lastName = sc.next();
                        	evalInsert(firstName, lastName);
                    	}
                        break;

                    case "search": {
                    		StringTokenizer tokens = new StringTokenizer(sc.nextLine());
                        	if (tokens.countTokens() == 2) {
                            	String firstName = tokens.nextToken();
                            	String lastName = tokens.nextToken();
                            	evalSearch(firstName, lastName);
                        	}
                        	else if (tokens.countTokens() == 1) {
                            	String name = tokens.nextToken();
                            	evalSearch(name);
                        	}
                    	}
                        break;

                    case "score": {
                        	int scorePercent = sc.nextInt();
                        	evalScore(scorePercent);
                    	}
                        break;

                    case "remove": {
                        	String firstName = sc.next();
                        	String lastName = sc.next();
                        	evalRemove(firstName, lastName);
                    	}
                        break;

                    case "removesection": {
                        	if (sc.hasNextInt()) {
                            	int sectionNumber = sc.nextInt();
                            	evalRemoveSection(sectionNumber);
                        	}
                        	else {
                        		evalRemoveSection();
                        	}
                    	}
                        break;

                    case "dumpsection":
                        evalDumpSection();
                        break;

                    case "grade":
                        evalGrade();
                        break;

                    case "findpair": {
                        	int scorePercentDiff = 0;
                        	if (sc.hasNextInt()) {
                        		scorePercentDiff = sc.nextInt();
                        	}
                        	evalFindPair(scorePercentDiff);
                    	}
                        break;

                    default: // Found an unrecognized command
                        // sc.nextLine();
                        break;
                }
            }
            sc.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean evalSection(int sectionNumber) {
        boolean result = false;
        if (sectionManager.isValidSection(sectionNumber)) {
        	sectionManager.setSection(sectionNumber);
        	int currentSectionNumber = sectionManager.getSection();
        	System.out.println("switch to section " + currentSectionNumber);
        	result = true;
        }
        return result;
    }
    
    public boolean evalInsert(String firstName, String lastName) {
        boolean result = false;
        Student student = sectionManager.findStudent(firstName, lastName);
        if (student == null) {
            sectionManager.insertStudent(firstName, lastName);
            System.out.println(firstName + " " + lastName
            	+ " inserted");
            result = true;
        }
        else {
            System.out.println(firstName + " " + lastName
            	+ " is already in section "
            	+ sectionManager.getSection());
            System.out.println(student);
        }
        return result;
    }

    public boolean evalSearch(String firstName, String lastName) {
        boolean result = false;
        Student student = sectionManager.findStudent(firstName, lastName);
        if (student != null) {
            System.out.println("Found " + student);
            result = true;
        }
        else {
            System.out.println("Search failed. Student "
            	+ firstName + " " + lastName
                + " doesn't exist in section "
            	+ sectionManager.getSection());
        }
        return result;
    }
    
    public boolean evalSearch(String name) {
        boolean result = false;
        System.out.println("search results for " + name + ":");
        Student[] students = sectionManager.findStudents(name);
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i]);
            result = true;
        }
        System.out.println(name + " was found in "
        	+ students.length + " records in section "
        	+ sectionManager.getSection());
        return result;
    }

    public boolean evalScore(int scorePercent) {
        boolean result = false;
        if (sectionManager.isStudentScorable()) {
            if (SectionManager.isValidScorePercent(scorePercent)) {
                Student student = sectionManager.scoreStudent(scorePercent);
                System.out.println("Update "
                	+ student.getFullName()
                    + " record, score = "
                	+ student.getScorePercentage());
                result = true;
            }
            else {
                System.out.println("Scores have to be"
                    + " integers in range 0 to 100.");
            }
        }
        else {
            System.out.println("score command can only be called after"
                + " an insert command or a successful search"
                + " command with one exact output.");
        }
        return result;
    }

    public boolean evalRemove(String firstName, String lastName) {
        boolean result = false;
        Student student = sectionManager.removeStudent(firstName, lastName);
        if (student != null) {
            System.out.println("Student "
            	+ firstName + " " + lastName
                + " get removed from section "
            	+ sectionManager.getSection());
            result = true;
        }
        else {
            System.out.println("Remove failed. Student "
            	+ firstName + " " + lastName
                + " doesn't exist in section "
                + sectionManager.getSection());
        }
        return result;
    }
    
    public boolean evalRemoveSection() {
    	int currentSectionNumber = sectionManager.getSection();
    	return evalRemoveSectionHandler(currentSectionNumber);
    }

    public boolean evalRemoveSection(int sectionNumber) {
        return evalRemoveSectionHandler(sectionNumber);
    }
    
    private boolean evalRemoveSectionHandler(int sectionNumber) {
    	boolean result = false;
    	if (sectionManager.isValidSection(sectionNumber)) {
    		sectionManager.clearSection(sectionNumber);
    		System.out.println("Section " + sectionNumber + " removed");
    		result = true;
    	}
    	return result;
    }

    public void evalDumpSection() {
        System.out.println("Section " + sectionManager.getSection() + " dump:");
        System.out.println("Size = " + sectionManager.dumpSection());
    }

    public void evalGrade() {
        sectionManager.gradeSection();
    }

    public void evalFindPair(int scorePercentDiff) {
        System.out.println("Students with score difference less than or equal "
        	+ scorePercentDiff + ":");
        StudentPair[] studentPairs = sectionManager.findStudentPairs(scorePercentDiff);
        for (int i = 0; i < studentPairs.length; i++) {
        	System.out.println(studentPairs[i]);
        }
        System.out.println("found " + studentPairs.length + " pairs");
    }
}
