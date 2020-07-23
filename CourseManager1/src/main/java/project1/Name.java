package project1;

/**
 * Name Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-07-23
 */
public class Name implements Comparable<Name> {
    private final String firstName;
    private final String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public int compareTo(Name name) {
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
        return String.format(firstName + " " + lastName);
    }
}
