package project1;

/**
 * Record Class
 * Student record information
 * 
 * @author ati Angel Isiadinso
 * @author kylegg7 Kyle Galindo
 * @version 2019-09-18
 */
public class Record {
    private Name name; // Student Name
    private String id; // Student ID
    private int score; // Student score


    /**
     * Record default constructor
     */
    public Record() {
        name = null;
        id = "010001";
        score = 0;
    }


    /**
     * Record Student Name, ID constructor
     * 
     * @param n Student name
     * @param i Student ID
     */
    public Record(Name n, String i) {
        name = n;
        id = i;
        score = 0;
    }


    /**
     * Record Student Name, ID, score constructor
     * 
     * @param n Student Name
     * @param i Student ID
     * @param s Student score
     */
    public Record(Name n, String i, int s) {
        name = n;
        id = i;
        score = s;
    }


    /**
     * Gets the student's name
     * 
     * @return Student Name
     */
    public Name getName() {
        return name;
    }


    /**
     * Gets the student's ID
     * 
     * @return Student ID
     */
    public String getID() {
        return id;
    }


    /**
     * Gets the student's score
     * 
     * @return Student score
     */
    public int getScore() {
        return score;
    }


    /**
     * Sets the student's name
     * 
     * @param n Student Name
     * @return name
     */
    public Name setName(Name n) {
        name = n;
        return name;
    }


    /**
     * Sets the student's ID
     * 
     * @param i Student ID
     * @return id
     */
    public String setID(String i) {
        id = i;
        return id;
    }


    /**
     * Set the student's score
     * 
     * @param s Student score
     * @return score
     */
    public int setScore(int s) {
        score = s;
        return score;
    }


    /**
     * toString override for Record Object
     * 
     * @return Record string
     */
    @Override
    public String toString() {
        return String.format(id + ", " + name + ", score = " + score);
    }
}
