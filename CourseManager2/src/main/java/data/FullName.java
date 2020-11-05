package data;

/**
 * FullName Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-25
 */
public class FullName implements Comparable<FullName> {
    private final String firstName;
    private final String middleName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.middleName = "";
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
    
    public boolean equalsPartOfIgnoreCase(String name) {
    	return firstName.equalsIgnoreCase(name) || lastName.equalsIgnoreCase(name);
    }

    @Override
    public int compareTo(FullName fullName) {
        int result = lastName.toLowerCase().compareTo(fullName.getLastName().toLowerCase());
        if (result == 0) {
            return firstName.toLowerCase().compareTo(fullName.getFirstName().toLowerCase());
        }
        return result;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
