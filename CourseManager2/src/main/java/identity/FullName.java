package identity;

/**
 * FullName Class
 * 
 * 
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-10
 */
public final class FullName implements Comparable<FullName> {
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
    
    /**
     * 
     * @param name Name
     * @return
     */
    public boolean containsName(String name) {
    	return firstName.equalsIgnoreCase(name) || lastName.equalsIgnoreCase(name);
    }

    /**
     * 
     * @param fullName Full name
     * @return
     */
    @Override
    public int compareTo(FullName fullName) {
        int result = getLCLastName().compareTo(fullName.getLCLastName());
        return (result != 0) ? result : getLCFirstName().compareTo(fullName.getLCFirstName());
    }
    
    private String getLCFirstName() {
    	return firstName.toLowerCase();
    }
    
    private String getLCLastName() {
    	return lastName.toLowerCase();
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s %s", firstName, lastName);
    }
}
