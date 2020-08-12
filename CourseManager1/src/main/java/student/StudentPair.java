package student;

/** 
 * StudentPair Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-12
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
	
	public int scorePercentageDiff() {
		return Math.abs(first.getScorePercentage() - second.getScorePercentage());
	}
	
    @Override
    public String toString() {
        return first.getFullName() + ", " + second.getFullName();
    }
}
