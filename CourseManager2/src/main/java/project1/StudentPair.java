package project1;

/** 
 * StudentPair Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-05
 */
public class StudentPair {
	private final Student first;
	private final Student second;
	
	public StudentPair(Student first, Student second) {
		this.first = first;
		this.second = second;
	}
	
	public Student getFirst() {
		return first;
	}
	
	public Student getSecond() {
		return second;
	}
	
    @Override
    public String toString() {
        return first.getFullName() + ", " + second.getFullName();
    }
}
