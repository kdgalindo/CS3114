package project1;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * Section Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-29
 */
public class Section {
    private BST<FullName, Student> studentDB;
    private int number;
    private int order; // Order that Record was inserted

    public Section() {
    	studentDB = new BST<FullName, Student>();
        number = 1;
        order = 1;
    }

    public Section(int n) {
    	studentDB = new BST<FullName, Student>();
        number = n;
        order = 1;
    }

    public int getNumber() {
        return number;
    }

    public Student insert(String firstName, String lastName) {
        FullName fullName = new FullName(firstName, lastName);
        Student student = new Student(fullName, newID());
        studentDB.insert(fullName, student);
        order++;
        return studentDB.find(fullName);
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

    public Student search(String firstName, String lastName) {
        return studentDB.find(new FullName(firstName, lastName));
    }

    public void score(Student student, int scorePercent) {
        student.setScore(scorePercent);
    }

    public Student remove(String firstName, String lastName) {
        return studentDB.remove(new FullName(firstName, lastName));
    }

    public void removeSection() {
    	studentDB.clear();
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
    public ArrayList<Student> search(String name) {
        ArrayList<Student> students = new ArrayList<Student>();
        Iterator<Student> i = studentDB.iterator();
        if (studentDB.size() != 0) {
            while (i.hasNext()) {
                Student student = i.next();
                if (student.getFullName().getFirstName().equalsIgnoreCase(name)
                	|| student.getFullName().getLastName().equalsIgnoreCase(name)) {
                    students.add(student);
                }
            }
        }
        return students;
    }

    /**
     * Prints inorder traversal of Section
     * 
     * @return Size of the Section
     */
    public int dumpSection() {
    	studentDB.inorder();
        return studentDB.size();
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
        Iterator<Student> i = studentDB.iterator();
        if (studentDB.size() != 0) {
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
        Iterator<Student> i1 = studentDB.iterator();
        Iterator<Student> i2;
        Student r1;
        Student r2;
        int i = 0;
        int retVal = 0;
        System.out.println("Students with score difference less than or equal "
            + s + ":");
        if (studentDB.size() != 0) {
            while (i1.hasNext()) {
                r1 = i1.next();
                i2 = studentDB.iterator();
                for (int j = 0; j < i; j++) {
                    i2.next();
                }
                while (i2.hasNext()) {
                    r2 = i2.next();
                    int diff = Math.abs(r1.getScore() - r2.getScore());
                    if ((diff <= s) && (r1 != r2)) {
                        System.out.println(r1.getFullName() + ", " + r2.getFullName());
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
