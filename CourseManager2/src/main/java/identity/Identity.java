package identity;

/**
 * Identity Class
 * 
 * 
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-10
 */
public final class Identity implements Comparable<Identity> {
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
    
    @Override
    public int compareTo(Identity identity) {
        return Long.compare(personalID, identity.getPersonalID());
    }
    
    @Override
    public String toString() {
    	return String.format("%09d, %s", personalID, fullName);
    }
}
