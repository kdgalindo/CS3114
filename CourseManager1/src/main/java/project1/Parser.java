package project1;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Parser Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-29
 */
public class Parser {
    private Section[] sections; // Array of 3 Sections
    private Section section; // Current Section
    private Student record; // Current Record
    private boolean scoreCMDNext; // Can a score op be
                                  // performed next

    /**
     * Parser default constructor
     */
    public Parser() {
        sections = new Section[3];
        for (int i = 0; i < 3; i++) {
            sections[i] = new Section(i + 1);
        }
        section = sections[0];
        record = null;
        scoreCMDNext = false;
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
        try {
            Scanner sc = new Scanner(new File(fn));

            while (sc.hasNext()) {

                String cmd = sc.next();

                int number;
                String first;
                String last;
                String name;
                StringTokenizer tokens;
                // System.out.println(cmd);

                switch (cmd) {
                    case "section": // Found a section command
                        number = sc.nextInt();
                        sectionhelp(number);
                        break;

                    case "insert": // Found an insert command
                        first = sc.next();
                        last = sc.next();
                        inserthelp(first, last);
                        break;

                    case "search": // Found a search command
                        tokens = new StringTokenizer(sc.nextLine());
                        if (tokens.countTokens() == 2) {
                            first = tokens.nextToken();
                            last = tokens.nextToken();
                            simplesearchhelp(first, last);
                        }
                        else if (tokens.countTokens() == 1) {
                            name = tokens.nextToken();
                            fuzzysearchhelp(name);
                        }
                        break;

                    case "score": // Found a score command
                        number = sc.nextInt();
                        scorehelp(number);
                        break;

                    case "remove": // Found a remove command
                        first = sc.next();
                        last = sc.next();
                        removehelp(first, last);
                        break;

                    case "removesection": // Found a removesection command
                        if (sc.hasNextInt()) {
                            number = sc.nextInt();
                            removesectionhelp(number);
                        }
                        else {
                            section.removeSection();
                            System.out.println("Section " + section.getNumber()
                                + " removed");
                        }
                        scoreCMDNext = false;
                        break;

                    case "dumpsection": // Found a dumpsection command
                        dumpsectionhelp();
                        break;

                    case "grade": // Found a grade command
                        gradehelp();
                        break;

                    case "findpair": // Found a findpair command
                        number = 0;
                        if (sc.hasNextInt()) {
                            number = sc.nextInt();
                        }
                        findpairhelp(number);
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

    /**
     * Helps the section method of Section
     * 
     * @param n number
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean sectionhelp(int n) {
        boolean retVal = false;
        if ((n >= 1) && (n <= 3)) {
            section = sections[n - 1];
            System.out.println("switch to section " + section.getNumber());
            retVal = true;
        }
        scoreCMDNext = false;
        return retVal;
    }

    /**
     * Helps the insert method of Section
     * 
     * @param f firstname
     * @param l lastname
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean inserthelp(String f, String l) {
        boolean retVal = false;
        record = section.search(f, l);
        if (record == null) {
            record = section.insert(f, l);
            System.out.println(record.getFullName() + " inserted");
            retVal = true;
        }
        else { // Error
            System.out.println(f + " " + l + " is already in section " + section
                .getNumber());
            System.out.println(record);
        }
        scoreCMDNext = true;
        return retVal;
    }

    /**
     * Helps the search <firstname> <lastname>
     * method of Section
     * 
     * @param f firstname
     * @param l lastname
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean simplesearchhelp(String f, String l) {
        boolean retVal = false;
        record = section.search(f, l);
        if (record != null) {
            System.out.println("Found " + record);
            scoreCMDNext = true;
            retVal = true;
        }
        else { // Error
            System.out.println("Search failed. Student " + f + " " + l
                + " doesn't exist in section " + section.getNumber());
        }
        return retVal;
    }

    /**
     * Helps the score method of Section
     * 
     * @param n number
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean scorehelp(int n) {
        boolean retVal = false;
        if (scoreCMDNext) {
            if ((n >= 0) && (n <= 100)) {
                section.score(record, n);
                System.out.println("Update " + record.getFullName()
                    + " record, score = " + record.getScore());
                retVal = true;
            }
            else { // Error
                System.out.println("Scores have to be"
                    + " integers in range 0 to 100.");
            }
        }
        else { // Error
            System.out.println("score command can only be called after"
                + " an insert command or a successful search"
                + " command with one exact output.");
        }
        scoreCMDNext = false;
        return retVal;
    }

    /**
     * Helps the remove method of Section
     * 
     * @param f firstname
     * @param l lastname
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean removehelp(String f, String l) {
        boolean retVal = false;
        record = section.remove(f, l);
        if (record != null) {
            System.out.println("Student " + f + " " + l
                + " get removed from section " + section.getNumber());
            retVal = true;
        }
        else { // Error
            System.out.println("Remove failed. Student " + f + " " + l
                + " doesn't exist in section " + section.getNumber());
        }
        scoreCMDNext = false;
        return retVal;
    }

    /**
     * Helps the removesection method of
     * Section
     * 
     * @param n number
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean removesectionhelp(int n) {
        boolean retVal = false;
        if ((n >= 1) && (n <= 3)) {
            sections[n - 1].removeSection();
            System.out.println("Section " + n + " removed");
            retVal = true;
        }
        return retVal;
    }

    /**
     * Helps the search <name> method of Section
     * 
     * @param n name
     * @return value to determine whether command
     *         has succeeded
     */
    public boolean fuzzysearchhelp(String n) {
        boolean retVal = false;
        ArrayList<Student> list = new ArrayList<Student>();
        System.out.println("search results for " + n + ":");
        list = section.search(n);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
            retVal = true;
        }
        System.out.println(n + " was found in " + list.size()
            + " records in section " + section.getNumber());
        if (list.size() == 1) {
            record = list.get(0);
            scoreCMDNext = true;
        }
        else {
            scoreCMDNext = false;
        }
        return retVal;
    }

    /**
     * Helps the dumpsection method of
     * Section
     */
    public void dumpsectionhelp() {
        System.out.println("Section " + section.getNumber() + " dump:");
        System.out.println("Size = " + section.dumpSection());
        scoreCMDNext = false;
    }

    /**
     * Helps the grade method of Section
     */
    public void gradehelp() {
        section.grade();
        scoreCMDNext = false;
    }

    /**
     * Helps the findpair method of
     * Section
     * 
     * @param s score
     */
    public void findpairhelp(int s) {
        section.findPair(s);
        scoreCMDNext = false;
    }
}
