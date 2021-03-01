package identity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import util.BST;

/**
 * IdentityManager Class
 * 
 * 
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2021-01-05
 */
public class IdentityManager {
    private BST<Long, Identity> idIdentityDb;
    
    /**
     * 
     */
    public IdentityManager() {
    	idIdentityDb = new BST<Long, Identity>();
    }
    
    /**
     * 
     * @return
     */
    public boolean isEmpty() {
    	return idIdentityDb.isEmpty();
    }
    
    /**
     * 
     * @param personalID
     * @return
     */
    public Identity find(long personalID) {
    	return idIdentityDb.find(personalID);
    }
    
    /**
     * 
     * @return
     */
    public List<Identity> list() {
    	List<Identity> identities = new ArrayList<Identity>(idIdentityDb.size());
    	for (Iterator<Identity> i = idIdentityDb.iterator(); i.hasNext();) {
    		identities.add(i.next());
    	}
    	return identities;
    }
    
    /**
     * 
     * @param identity
     */
    public void insert(Identity identity) {
    	idIdentityDb.insert(identity.getPersonalID(), identity);
    }
    
    /**
     * 
     */
    public void clear() {
    	idIdentityDb.clear();
    }
}
