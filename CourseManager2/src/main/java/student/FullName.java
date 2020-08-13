package student;

/**
 * FullName Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-13
 */
public class FullName implements Comparable<FullName> {
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.middleName = null;
        this.lastName = lastName;
    }
    
    public FullName(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }
    
    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int compareTo(FullName fullName) {
        int result = lastName.toLowerCase().compareTo(fullName.getLastName().toLowerCase());
        if (result == 0) {
            return firstName.toLowerCase().compareTo(fullName.getFirstName().toLowerCase());
        }
        else {
            return result;
        }
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}