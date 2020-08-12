package interpreter;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Parser Class
 * Parser for Coursemanager2
 *
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-10-20
 */
public class Parser {
    private TopLevel toplevel;

    /**
     * Parser default constructor
     */
    public Parser() {
        toplevel = new TopLevel();
    }

    /**
     * Parses .txt file for commands to
     * perform operations on Sections,
     * prints output of commands to
     * standard output
     * 
     * @param fn filename
     */
    public void parse(String fn) {
        String fn2;
        int number;
        long pid;
        String first;
        String last;
        String name;
        String grade;
        StringTokenizer tokens;
        try {
            Scanner sc = new Scanner(new File(fn));

            while (sc.hasNext()) {

                String cmd = sc.next();
                // System.out.println(cmd);

                switch (cmd) {
                    case "loadstudentdata": // Found a loadstudentdata command
                        fn2 = sc.next();
                        toplevel.loadstudentdata(fn2);
                        break;
                        
                    case "loadcoursedata": // Found a loadcoursedata command
                        fn2 = sc.next();
                        toplevel.loadcoursedata(fn2);
                        break;

                    case "section": // Found a section command
                        number = sc.nextInt();
                        toplevel.section(number);
                        break;

                    case "insert": // Found an insert command
                        pid = sc.nextLong();
                        first = sc.next();
                        last = sc.next();
                        toplevel.insert(pid, first, last);
                        break;

                    case "searchid": // Found a searchid command
                        pid = sc.nextLong();
                        toplevel.searchid(pid);
                        break;

                    case "search": // Found a search command
                        tokens = new StringTokenizer(sc.nextLine());
                        if (tokens.countTokens() == 2) {
                            first = tokens.nextToken();
                            last = tokens.nextToken();
                            toplevel.search(first, last);
                        }
                        else if (tokens.countTokens() == 1) {
                            name = tokens.nextToken();
                            toplevel.search(name);
                        }
                        break;

                    case "score": // Found a score command
                        number = sc.nextInt();
                        toplevel.score(number);
                        break;

                    case "remove": // Found a remove command
                        tokens = new StringTokenizer(sc.nextLine());
                        if (tokens.countTokens() == 1) {
                            pid = Long.parseLong(tokens.nextToken());
                            toplevel.remove(pid);
                        }
                        else if (tokens.countTokens() == 2) {
                            first = tokens.nextToken();
                            last = tokens.nextToken();
                            toplevel.remove(first, last);
                        }
                        break;

                    case "clearsection": // Found a clearsection command
                        toplevel.clearsection();
                        break;

                    case "dumpsection": // Found a dumpsection command
                        toplevel.dumpsection();
                        break;

                    case "grade": // Found a grade command
                        toplevel.grade();
                        break;
                        
                    case "stat": // Found a stat command
                        toplevel.stat();
                        break;
                        
                    case "list": // Found a list command
                        grade = sc.next();
                        toplevel.list(grade);
                        break;

                    case "findpair": // Found a findpair command
                        number = 0;
                        if (sc.hasNextInt()) {
                            number = sc.nextInt();
                        }
                        toplevel.findpair(number);
                        break;
                        
                    case "merge": // Found a merge command
                        toplevel.merge();
                        break;
                        
                    case "savestudentdata": // Found a savestudentdata command
                        fn2 = sc.next();
                        toplevel.savestudentdata(fn2);
                        break;
                        
                    case "savecoursedata": // Found a savecoursedata command
                        fn2 = sc.next();
                        toplevel.savecoursedata(fn2);
                        break;
                        
                    case "clearcoursedata": // Found a clearcoursedata command
                        toplevel.clearcoursedata();
                        break;

                    default: // Found an unrecognized command
//                        System.out.println("---- " + cmd + " ----");
//                        sc.nextLine();
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
