package student;

/**
 * FullName Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-12
 */
public class FullName implements Comparable<FullName> {
    private final String firstName;
    private final String lastName;

    public FullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public boolean equalsPartOfIgnoreCase(String name) {
    	return firstName.equalsIgnoreCase(name) || lastName.equalsIgnoreCase(name);
    }

    @Override
    public int compareTo(FullName name) {
        int result = lastName.toLowerCase().compareTo(name.getLastName().toLowerCase());
        if (result == 0) {
            return firstName.toLowerCase().compareTo(name.getFirstName().toLowerCase());
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
