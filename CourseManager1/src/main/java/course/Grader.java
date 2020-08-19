package course;

import java.util.Arrays;
import java.util.Iterator;

import student.Student;

/** 
 * Grader Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class Grader {
	private final static String[] LETTER_GRADES = { "A", "A-", "B+", "B", "B-", "C+",
													"C", "C-", "D+", "D", "D-", "F" };
	private final static int[] PERCENTAGE_GRADE_LBOUNDS = { 90, 85, 80, 75, 70, 65,
														  60, 58, 55, 53, 50 };
	
	public static void gradeStudents(Iterator<Student> it) {
		int[] studentsWithEachGrade = new int[LETTER_GRADES.length];
		Arrays.fill(studentsWithEachGrade, 0);
        while (it.hasNext()) {
            Student student = it.next();
            int index = findGradeIndex(student.getScorePercentage());
            studentsWithEachGrade[index]++;
        }        
        printGradeResults(studentsWithEachGrade);
	}
	
    private static int findGradeIndex(int scorePercent) {
        int index = PERCENTAGE_GRADE_LBOUNDS.length;
        for (int i = 0; i < PERCENTAGE_GRADE_LBOUNDS.length; i++) {
            if (scorePercent >= PERCENTAGE_GRADE_LBOUNDS[i]) {
            	index = i;
                break;
            }
        }
        return index;
    }
    
	private static void printGradeResults(int[] studentsWithEachGrade) {
		System.out.println("grading completed:");
        for (int i = 0; i < studentsWithEachGrade.length; i++) {
            int studentsWithGrade = studentsWithEachGrade[i];
            if (studentsWithGrade != 0) {
                System.out.println(studentsWithGrade
                	+ " students with grade "
                	+ LETTER_GRADES[i]);
            }
        }
	}
}
