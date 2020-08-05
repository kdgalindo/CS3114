package project1;

/** 
 * Pair Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-04
 */
public class Pair<A, B> {
	private final A value0;
	private final B value1;
	
	public static <A, B> Pair<A, B> with(A value0, B value1) {
		return new Pair<A, B>(value0, value1);
	}
	
	private Pair(A value0, B value1) {
		this.value0 = value0;
		this.value1 = value1;
	}
	
	public A getValue0() {
		return value0;
	}
	
	public B getValue1() {
		return value1;
	}
	
    @Override
    public String toString() {
        return String.format(value0 + ", " + value1);
    }
}
