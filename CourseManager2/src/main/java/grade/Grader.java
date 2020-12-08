package grade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import student.Student;

/** 
 * Grader Class
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-08
 */
public final class Grader {
	private final static String[] LETTERS = { "A ", "A-", "B+", "B ", "B-", "C+",
											  "C ", "C-", "D+", "D ", "D-", "F " };
	private final static int[] PERCENTAGE_LBOUNDS = { 90, 85, 80, 75, 70, 65,
												  	  60, 58, 55, 53, 50, 0 };
	private final static int[] PERCENTAGE_UBOUNDS = { 100, 89, 84, 79, 74, 69,
												  	  64, 59, 57, 54, 52, 49 };
	private final static HashMap<String, Range> GRADE_RANGES;
	
	private Grader() {
		
	}
	
	private static class Range {
		private final int lBound;
		private final int uBound;
		
		private Range(int lBound, int uBound) {
			this.lBound = lBound;
			this.uBound = uBound;
		}
	}
	
	static {
		GRADE_RANGES = new HashMap<String, Range>();
		for (int i = 0; i < LETTERS.length; ++i) {
			GRADE_RANGES.put(LETTERS[i].toLowerCase(), new Range(PERCENTAGE_LBOUNDS[i], PERCENTAGE_UBOUNDS[i]));
		}
		GRADE_RANGES.put("a*", new Range(getPercentageGradeLB("A-"), getPercentageGradeUB("A ")));
		GRADE_RANGES.put("b*", new Range(getPercentageGradeLB("B-"), getPercentageGradeUB("B+")));
		GRADE_RANGES.put("c*", new Range(getPercentageGradeLB("C-"), getPercentageGradeUB("C+")));
		GRADE_RANGES.put("d*", new Range(getPercentageGradeLB("D-"), getPercentageGradeUB("D+")));
		GRADE_RANGES.put("f*", new Range(getPercentageGradeLB("F "), getPercentageGradeUB("F ")));
	}
	
	/**
	 * 
	 * @param lGrade Letter grade
	 * @return
	 */
	public static Integer getPercentageGradeLB(String lGrade) {
		Range pGradeRange = GRADE_RANGES.get(toLetterGradeKey(lGrade));
		if (pGradeRange == null) {
			return null;
		}
		return pGradeRange.lBound;
	}
	
	private static String toLetterGradeKey(String lGrade) {
		String key = (lGrade.length() == 1) ? lGrade.concat(" ") : lGrade;
		return key.toLowerCase();
	}
	
	/**
	 * 
	 * @param lGrade Letter grade
	 * @return
	 */
	public static Integer getPercentageGradeUB(String lGrade) {
		Range pGradeRange = GRADE_RANGES.get(toLetterGradeKey(lGrade));
		if (pGradeRange == null) {
			return null;
		}
		return pGradeRange.uBound;
	}
	
	
	
	/**
	 * 
	 * @param student Student
	 * @param pGrade Percentage grade
	 */
	public static void setPercentageGrade(Student student, int pGrade) {
		if (isValidPercentageGrade(pGrade)) {
			setPercentage(student.getGrade(), pGrade);
		}
	}
	
    /**
     * 
     * @param pGrade Percentage grade
     * @return
     */
    public static boolean isValidPercentageGrade(int pGrade) {
    	return (pGrade > -1) && (pGrade < 101);
    }
	
	private static void setPercentage(Grade grade, int pGrade) {
		grade.setPercentage(pGrade);
	}
    
    
	
	/**
	 * 
	 * @param student Student
	 */
	public static void setLetterGrade(Student student) {
		int pGrade = student.getPercentageGrade();
		if (isValidPercentageGrade(pGrade)) {
			String lGrade = getLetterGrade(pGrade);
			setLetter(student.getGrade(), lGrade);
		}
	}
    
	private static String getLetterGrade(int pGrade) {
		int index = getLetterIndex(pGrade);
		if (index == LETTERS.length) {
			return null;
		}
		return LETTERS[index];
	}
	
    private static int getLetterIndex(int pGrade) {
    	int index = 0;
        for (int lBound : PERCENTAGE_LBOUNDS) {
        	if (pGrade >= lBound) {
        		break;
        	}
        	++index;
        }
        return index;
    }
    
	private static void setLetter(Grade grade, String lGrade) {
		grade.setLetter(lGrade);
	}
	
    
	
	/**
	 * 
	 * @param student Student
	 * @param grade Grade
	 */
	public static void setGrade(Student student, Grade grade) {
		if (isValidGrade(grade)) {
			setGrade(student.getGrade(), grade);
		}
	}
	
	private static boolean isValidGrade(Grade grade) {
		return isValidPercentageGrade(grade.getPercentage()) && isValidLetterGrade(grade);
	}
	
    private static boolean isValidLetterGrade(Grade grade) {
    	return letterGradesEqual(grade.getLetter(), getLetterGrade(grade.getPercentage()));
    }
    
    private static boolean letterGradesEqual(String lGrade1, String lGrade2) {
    	return toLetterGradeKey(lGrade1).equals(toLetterGradeKey(lGrade2));
    }
    
    private static void setGrade(Grade oGrade, Grade nGrade) {
    	oGrade.setPercentage(nGrade.getPercentage());
    	oGrade.setLetter(nGrade.getLetter());
    }
    
    
	
	/**
	 * 
	 * @param students List of students
	 */
	public static List<String> listGradeLevelStats(List<Student> students) {
		int[] studentsPerGrade = new int[LETTERS.length];
		Arrays.fill(studentsPerGrade, 0);
		for (Student student : students) {
            int index = getLetterIndex(student.getPercentageGrade());
            ++studentsPerGrade[index];
		}
		return toGradeLevelStatsList(studentsPerGrade);
	}
    
	private static List<String> toGradeLevelStatsList(int[] studentsPerGrade) {
		List<String> gLevelStats = new ArrayList<String>();
		for (int i = 0; i < studentsPerGrade.length; ++i) {
			if (studentsPerGrade[i] != 0) {
				gLevelStats.add(studentsPerGrade[i] +
						" students with grade " +
	                	LETTERS[i]);
			}
		}
		return gLevelStats;
	}
}
