package project1;

import java.util.Iterator;

/** 
 * Grader Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-04
 */
public class Grader {
	public final static String[] GRADE_LETTERS = { "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D", "D-", "F" };
	private final static int[] GRADE_LOWER_BOUNDS = { 90, 85, 80, 75, 70, 65, 60, 58, 55, 53, 50 };
	
	public static int[] grade(Iterator<Student> it) {
		int[] numbers = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        while (it.hasNext()) {
            Student student = it.next();
            int j = gradehelper(student.getScore());
            numbers[j]++;
        }        
        return numbers;
	}
	
    private static int gradehelper(int scorePercent) {
        int result = 11;
        for (int i = 0; i < 11; i++) {
            if (scorePercent >= GRADE_LOWER_BOUNDS[i]) {
                result = i;
                break;
            }
        }
        return result;
    }
}
