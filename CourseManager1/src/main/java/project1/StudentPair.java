package project1;

/** 
 * StudentPair Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-05
 */
public class StudentPair {
	private Student first;
	private Student second;
	
	public StudentPair(Student first, Student second) {
		this.first = first;
		this.second = second;
	}
	
    @Override
    public String toString() {
        return first.getFullName() + ", " + second.getFullName();
    }
}
