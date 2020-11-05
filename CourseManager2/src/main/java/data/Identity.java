package data;

public class Identity implements Comparable<Identity> {
	private final long personalID;
	private final FullName fullName;
	
	public Identity(long personalID, FullName fullName) {
		this.personalID = personalID;
		this.fullName = fullName;
	}
	
	public long getPersonalID() {
		return personalID;
	}
	
    public FullName getFullName() {
        return fullName;
    }
    
    public String getFirstName() {
        return fullName.getFirstName();
    }
    
    public String getMiddleName() {
        return fullName.getMiddleName();
    }
    
    public String getLastName() {
        return fullName.getLastName();
    }
    
    @Override
    public int compareTo(Identity identity) {
        return Long.compare(personalID, identity.getPersonalID());
    }
    
    @Override
    public String toString() {
    	return String.format("%09d, %s", personalID, fullName);
    }
}
