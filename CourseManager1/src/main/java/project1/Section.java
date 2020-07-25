package project1;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * Section Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-25
 */
public class Section {
    private BST<Name, Student> bst;
    private int number; // Section number
    private int order; // Order that Record was inserted

    /**
     * Section default constructor
     */
    public Section() {
        bst = new BST<Name, Student>();
        number = 1;
        order = 1;
    }

    /**
     * Section number constructor
     * 
     * @param n section number
     */
    public Section(int n) {
        bst = new BST<Name, Student>();
        number = n;
        order = 1;
    }

    /**
     * Returns the section number
     * 
     * @return section number
     */
    public int getNumber() {
        return number;
    }


    /**
     * 
     * @param f first name
     * @param l last name
     * @return record found 
     */
    public Student insert(String f, String l) {
        Name n = new Name(f, l);
        bst.insert(n, new Student(n, newID()));
        order++;
        return bst.find(n);
    }

    /**
     * Returns a new ID for a Record
     * 
     * @return id
     */
    private String newID() {
        int id = (number * 10000) + order;
        return String.format("%06d", id);
    }

    /**
     * Finds the Record value associated with the
     * Name key and returns the Record value
     * 
     * @param f first name
     * @param l last name
     * @return Record value associated with Name key
     */
    public Student search(String f, String l) {
        return bst.find(new Name(f, l));
    }

    /**
     * Sets the score of a Record
     * 
     * @param n score
     * @param r Record
     */
    public void score(int n, Student r) {
        r.setScore(n);
    }

    /**
     * Deletes the Record value associated with the
     * Name key and returns the Record value
     * 
     * @param f first name
     * @param l last name
     * @return Record value associated with Name key
     */
    public Student remove(String f, String l) {
        return bst.remove(new Name(f, l));
    }

    /**
     * Clears all of the Record values in the 
     * Section and sets the order of the Section
     * back to 1
     */
    public void removeSection() {
        bst.clear();
        order = 1;
    }

    /**
     * Returns all of the Records in the Section
     * where n matches the first or last name of
     * the Record
     * 
     * @param n name
     * @return ArrayList of Records where n matches
     *         the first or last name of the Record
     */
    public ArrayList<Student> search(String n) {
        ArrayList<Student> l = new ArrayList<Student>();
        Iterator<Student> i = bst.iterator();
        if (bst.size() != 0) {
            while (i.hasNext()) {
                Student r = i.next();
                if (r.getName().getFirstName().equalsIgnoreCase(n) || r.getName()
                    .getLastName().equalsIgnoreCase(n)) {
                    l.add(r);
                }
            }
        }
        return l;
    }

    /**
     * Prints inorder traversal of Section
     * 
     * @return Size of the Section
     */
    public int dumpSection() {
        bst.inorder();
        return bst.size();
    }

    /**
     * Prints the number of Records within each
     * letter grade range; if there are Records
     * within the letter grade range, print, else,
     * do not print
     */
    public void grade() {
        int[] numbers = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        String[] letters = { "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+",
            "D", "D-", "F" };
        Iterator<Student> i = bst.iterator();
        if (bst.size() != 0) {
            while (i.hasNext()) {
                Student r = i.next();
                int j = gradehelper(r.getScore());
                numbers[j]++;
            }
        }
        System.out.println("grading completed:");
        for (int k = 0; k < numbers.length; k++) {
            int n = numbers[k];
            if (n != 0) {
                System.out.println(n + " students with grade " + letters[k]);
            }
        }
    }

    /**
     * Returns index to determine the number of
     * Records within each grade range
     * 
     * @param s score
     * @return Index to determine the number of
     *         Records within each grade range
     */
    private int gradehelper(int s) {
        int retVal = 11;
        int[] ranges = { 90, 85, 80, 75, 70, 65, 60, 58, 55, 53, 50 };
        for (int i = 0; i < 11; i++) {
            if (s >= ranges[i]) {
                retVal = i;
                break;
            }
        }
        return retVal;
    }

    /**
     * Prints the pairs of Records with score
     * differences less than or equal to s
     * 
     * @param s score
     * @return Number of Record pairs
     */
    public int findPair(int s) {
        Iterator<Student> i1 = bst.iterator();
        Iterator<Student> i2;
        Student r1;
        Student r2;
        int i = 0;
        int retVal = 0;
        System.out.println("Students with score difference less than or equal "
            + s + ":");
        if (bst.size() != 0) {
            while (i1.hasNext()) {
                r1 = i1.next();
                i2 = bst.iterator();
                for (int j = 0; j < i; j++) {
                    i2.next();
                }
                while (i2.hasNext()) {
                    r2 = i2.next();
                    int diff = Math.abs(r1.getScore() - r2.getScore());
                    if ((diff <= s) && (r1 != r2)) {
                        System.out.println(r1.getName() + ", " + r2.getName());
                        retVal++;
                    }
                }
                i++;
            }
        }
        System.out.println("found " + retVal + " pairs");
        return retVal;
    }
}
