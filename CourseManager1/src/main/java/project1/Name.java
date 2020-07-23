package project1;

/**
 * Name Class
 * Student name information
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-09-19
 */
public class Name implements Comparable<Name> {
    private String first; // First name
    private String last; // Last name


    /**
     * Name default constructor
     */
    public Name() {
        first = null;
        last = null;
    }


    /**
     * Name first name, last name constructor
     * 
     * @param f first name
     * @param l last name
     */
    public Name(String f, String l) {
        first = f;
        last = l;
    }


    /**
     * Gets the first name
     * 
     * @return first name
     */
    public String getFirst() {
        return first;
    }


    /**
     * Gets the last name
     * 
     * @return last name
     */
    public String getLast() {
        return last;
    }


    /**
     * Sets the first name
     * 
     * @param f
     *            first name
     * @return first name
     */
    public String setFirst(String f) {
        first = f;
        return first;
    }


    /**
     * Sets the last name
     * 
     * @param l last name
     * @return last name
     */
    public String setLast(String l) {
        last = l;
        return last;
    }


    /**
     * compareTo override for Name Object
     * 
     * @param n Student Name
     * @return 0 if equal, less than 0 if Name is
     *         lexicographically less than n, greater
     *         than 0 if Name is lexicographically
     *         greater than n
     */
    @Override
    public int compareTo(Name n) {
        int l = last.toLowerCase().compareTo(n.last.toLowerCase());
        if (l == 0) {
            return first.toLowerCase().compareTo(n.first.toLowerCase());
        }
        else {
            return l;
        }
    }


    /**
     * toString override for Name Object
     * 
     * @return Name string
     */
    @Override
    public String toString() {
        return String.format(first + " " + last);
    }
}
