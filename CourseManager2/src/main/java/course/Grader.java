package course;

import java.util.Arrays;
import java.util.HashMap;

import data.Student;
import util.Pair;

/** 
 * Grader Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
 */
public class Grader {
	private final static String[] LETTER_GRADES = { "A ", "A-", "B+", "B ", "B-", "C+",
													"C ", "C-", "D+", "D ", "D-", "F " };
	private final static int[] PERCENTAGE_GRADE_LBOUNDS = { 90, 85, 80, 75, 70, 65,
															60, 58, 55, 53, 50, 0 };
	private final static int[] PERCENTAGE_GRADE_UBOUNDS = { 100, 89, 84, 79, 74, 69,
															64, 59, 57, 54, 52, 49 };
	private final static HashMap<String, Pair<Integer, Integer>> GRADE_BOUNDS;
	
	static {
		GRADE_BOUNDS = new HashMap<String, Pair<Integer, Integer>>();
		for (int i = 0; i < LETTER_GRADES.length; i++) {
			GRADE_BOUNDS.put(LETTER_GRADES[i].toLowerCase(), Pair.with(PERCENTAGE_GRADE_LBOUNDS[i], PERCENTAGE_GRADE_UBOUNDS[i]));
		}
		GRADE_BOUNDS.put("a*", Pair.with(getLowerPercentageGrade("A-"), getUpperPercentageGrade("A ")));
		GRADE_BOUNDS.put("b*", Pair.with(getLowerPercentageGrade("B-"), getUpperPercentageGrade("B+")));
		GRADE_BOUNDS.put("c*", Pair.with(getLowerPercentageGrade("C-"), getUpperPercentageGrade("C+")));
		GRADE_BOUNDS.put("d*", Pair.with(getLowerPercentageGrade("D-"), getUpperPercentageGrade("D+")));
		GRADE_BOUNDS.put("f*", Pair.with(getLowerPercentageGrade("F "), getUpperPercentageGrade("F ")));
	}
	
	/**
	 * 
	 * @param letterGrade
	 * @return
	 */
	public static Integer getLowerPercentageGrade(String letterGrade) {
		return getPercentageGradeBounds(letterGrade).getValue0();
	}
	
	private static Pair<Integer, Integer> getPercentageGradeBounds(String letterGrade) {
		return GRADE_BOUNDS.get(preprocessLetterGrade(letterGrade));
	}
	
	private static String preprocessLetterGrade(String oldLetterGrade) {
		String letterGrade = oldLetterGrade;
		if (oldLetterGrade.length() == 1) {
			letterGrade = oldLetterGrade.concat(" ");
		}
		return letterGrade.toLowerCase();
	}
	
	/**
	 * 
	 * @param letterGrade
	 * @return
	 */
	public static Integer getUpperPercentageGrade(String letterGrade) {
		return getPercentageGradeBounds(letterGrade).getValue1();
	}
	
	/**
	 * 
	 * @param students
	 */
	public static void gradeStudents(Student[] students) {
		for (int i = 0; i < students.length; i++) {
			gradeStudent(students[i]);
		}
		System.out.println("grading completed");
	}
	
	public static void gradeStudent(Student student) {
		int percentage = student.getPercentageGrade();
		String letterGrade = LETTER_GRADES[findGradeIndex(percentage)];
		student.setLetterGrade(letterGrade);
	}
	
    private static int findGradeIndex(int percentageGrade) {
        for (int i = 0; i < PERCENTAGE_GRADE_LBOUNDS.length; i++) {
            if (percentageGrade >= PERCENTAGE_GRADE_LBOUNDS[i]) {
            	return i;
            }
        }
        return -1;
    }
	
	/**
	 * 
	 * @param students
	 */
	public static void statStudents(Student[] students) {
		int[] studentsWithEachGrade = new int[LETTER_GRADES.length];
		Arrays.fill(studentsWithEachGrade, 0);
		for (int i = 0; i < students.length; i++) {
			Student student = students[i];
            int index = findGradeIndex(student.getPercentageGrade());
            studentsWithEachGrade[index]++;
		}
        printGradeResults(studentsWithEachGrade);
	}
    
	private static void printGradeResults(int[] studentsWithEachGrade) {
//		System.out.println("grading completed:");
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
