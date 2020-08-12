package student;

/**
 * Name Class
 * Name Info
 * 
 * @author ati Angel Isiadinso
 * @author kyleg997 Kyle Galindo
 * @version 2019-10-20
 */
public class Name implements Comparable<Name> {
    private String first; // First name
    private String middle; // Middle name
    private String last; // Last name

    /**
     * Name first, last name constructor
     * 
     * @param f first name
     * @param l last name
     */
    public Name(String f, String l) {
        first = f;
        middle = null;
        last = l;
    }
    
    /**
     * Name first, middle, last name constructor
     * 
     * @param f first name
     * @param m middle name
     * @param l last name
     */
    public Name(String f, String m, String l) {
        first = f;
        middle = m;
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
     * Gets the middle name
     * 
     * @return middle name
     */
    public String getMiddle() {
        return middle;
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
