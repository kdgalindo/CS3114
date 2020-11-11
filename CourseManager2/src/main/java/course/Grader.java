package course;

import java.util.Arrays;
import java.util.HashMap;

import data.Student;

/** 
 * Grader Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-11-11
 */
public class Grader {
	private final static String[] LGRADES = { "A ", "A-", "B+", "B ", "B-", "C+",
											  "C ", "C-", "D+", "D ", "D-", "F " };
	private final static int[] PGRADE_LBOUNDS = { 90, 85, 80, 75, 70, 65,
												  60, 58, 55, 53, 50, 0 };
	private final static int[] PGRADE_UBOUNDS = { 100, 89, 84, 79, 74, 69,
												  64, 59, 57, 54, 52, 49 };
	private final static HashMap<String, PGradeRange> GRADE_RANGES;
	
	private static class PGradeRange {
		private final int lBound;
		private final int uBound;
		
		private PGradeRange(int lBound, int uBound) {
			this.lBound = lBound;
			this.uBound = uBound;
		}
	}
	
	static {
		GRADE_RANGES = new HashMap<String, PGradeRange>();
		for (int i = 0; i < LGRADES.length; ++i) {
			GRADE_RANGES.put(LGRADES[i].toLowerCase(), new PGradeRange(PGRADE_LBOUNDS[i], PGRADE_UBOUNDS[i]));
		}
		GRADE_RANGES.put("a*", new PGradeRange(getPercentageGradeLowerBound("A-"), getPercentageGradeUpperBound("A ")));
		GRADE_RANGES.put("b*", new PGradeRange(getPercentageGradeLowerBound("B-"), getPercentageGradeUpperBound("B+")));
		GRADE_RANGES.put("c*", new PGradeRange(getPercentageGradeLowerBound("C-"), getPercentageGradeUpperBound("C+")));
		GRADE_RANGES.put("d*", new PGradeRange(getPercentageGradeLowerBound("D-"), getPercentageGradeUpperBound("D+")));
		GRADE_RANGES.put("f*", new PGradeRange(getPercentageGradeLowerBound("F "), getPercentageGradeUpperBound("F ")));
	}
	
	/**
	 * 
	 * @param letterGrade
	 * @return
	 */
	public static Integer getPercentageGradeLowerBound(String letterGrade) {
		PGradeRange range = getPGradeRange(letterGrade);
		if (range == null) {
			return null;
		}
		
		return range.lBound;
	}
	
	private static PGradeRange getPGradeRange(String letterGrade) {
		return GRADE_RANGES.get(preprocessLetterGrade(letterGrade));
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
	public static Integer getPercentageGradeUpperBound(String letterGrade) {
		PGradeRange range = getPGradeRange(letterGrade);
		if (range == null) {
			return null;
		}
		
		return range.uBound;
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
		String letterGrade = LGRADES[findGradeIndex(percentage)];
		student.setLetterGrade(letterGrade);
	}
	
    private static int findGradeIndex(int percentageGrade) {
        for (int i = 0; i < PGRADE_LBOUNDS.length; i++) {
            if (percentageGrade >= PGRADE_LBOUNDS[i]) {
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
		int[] studentsWithEachGrade = new int[LGRADES.length];
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
                	+ LGRADES[i]);
            }
        }
	}
}
