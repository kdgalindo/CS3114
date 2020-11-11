package course;

import java.util.Arrays;
import java.util.Iterator;

import data.Identity;
import util.BST;

/**
 * StudentManager Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-17
 */
public class IdentityManager {
    private BST<Long, Identity> pidIdentityDB;
    
    public IdentityManager() {
    	pidIdentityDB = new BST<Long, Identity>();
    }
    
    public void insert(Identity identity) {
    	pidIdentityDB.insert(identity.getPersonalID(), identity);
    }
    
    public Identity findIdentity(long personalID) {
    	return pidIdentityDB.find(personalID);
    }
    
    public Identity[] getIdentities() {
    	Identity[] identities = new Identity[pidIdentityDB.size()];
		Arrays.fill(identities, null);
		Iterator<Identity> it = pidIdentityDB.iterator();
		int i = 0;
		while (it.hasNext()) {
			identities[i++] = it.next();
		}
		return identities;
    }
    
    public void clear() {
    	pidIdentityDB.clear();
    }
}
