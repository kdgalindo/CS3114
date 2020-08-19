package interpreter;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * CmdParser Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class CmdParser {
    private CmdEvaluator cmdEvaluator;

    public CmdParser() {
    	cmdEvaluator = new CmdEvaluator();
    }

    public void parse(String fn) {
        try {
            Scanner sc = new Scanner(new File(fn));
            while (sc.hasNext()) {
                String cmd = sc.next();
                switch (cmd) {
                    case "loadstudentdata": {
                        	String studentDataFilename = sc.next();
                        	cmdEvaluator.loadStudentData(studentDataFilename);
                    	}
                        break;
                        
                    case "loadcoursedata": {
                        	String courseDataFilename = sc.next();
                        	cmdEvaluator.loadCourseData(courseDataFilename);
                    	}
                        break;

                    case "section": {
                        	int sectionNumber = sc.nextInt();
                        	cmdEvaluator.section(sectionNumber);
                    	}
                        break;

                    case "insert": {
                        	long personalID = sc.nextLong();
                        	String firstName = sc.next();
                        	String lastName = sc.next();
                        	cmdEvaluator.insert(personalID, firstName, lastName);
                    	}
                        break;

                    case "searchid": {
                    		long personalID = sc.nextLong();
                        	cmdEvaluator.searchID(personalID);
                    	}
                        break;

                    case "search": {
                    		StringTokenizer tokens = new StringTokenizer(sc.nextLine());
                        	if (tokens.countTokens() == 2) {
                        		String firstName = tokens.nextToken();
                        		String lastName = tokens.nextToken();
                        		cmdEvaluator.search(firstName, lastName);
                        	}
                        	else if (tokens.countTokens() == 1) {
                        		String name = tokens.nextToken();
                        		cmdEvaluator.search(name);
                        	}
                    	}
                        break;

                    case "score": {
                        	int percentage = sc.nextInt();
                        	cmdEvaluator.score(percentage);
                    	}
                        break;

                    case "remove": {
                    		StringTokenizer tokens = new StringTokenizer(sc.nextLine());
                        	if (tokens.countTokens() == 1) {
                        		long personalID = Long.parseLong(tokens.nextToken());
                        		cmdEvaluator.remove(personalID);
                        	}
                        	else if (tokens.countTokens() == 2) {
                        		String firstName = tokens.nextToken();
                        		String lastName = tokens.nextToken();
                        		cmdEvaluator.remove(firstName, lastName);
                        	}
                    	}
                        break;

                    case "clearsection":
                        cmdEvaluator.clearSection();
                        break;

                    case "dumpsection":
                        cmdEvaluator.dumpSection();
                        break;

                    case "grade":
                        cmdEvaluator.grade();
                        break;
                        
                    case "stat":
                        cmdEvaluator.stat();
                        break;
                        
                    case "list": {
                        	String letterGrade = sc.next();
                        	cmdEvaluator.list(letterGrade);
                    	}
                        break;

                    case "findpair": {
                        	int percentageDiff = 0;
                        	if (sc.hasNextInt()) {
                        		percentageDiff = sc.nextInt();
                        	}
                        	cmdEvaluator.findPair(percentageDiff);
                    	}
                        break;
                        
                    case "merge":
                        cmdEvaluator.merge();
                        break;
                        
                    case "savestudentdata": {
                        	String studentDataFilename = sc.next();
                        	cmdEvaluator.saveStudentData(studentDataFilename);
                    	}
                        break;
                        
                    case "savecoursedata": {
                        	String courseDataFilename = sc.next();
                        	cmdEvaluator.saveCourseData(courseDataFilename);
                    	}
                        break;
                        
                    case "clearcoursedata":
                        cmdEvaluator.clearCourseData();
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
}
