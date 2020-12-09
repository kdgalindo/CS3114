package grade;

/**
 * Grade Class
 * 
 * Grades have a percentage grade and a letter grade. Grades can only be
 * modified by the grader.
 * 
 * TODO Create default percentage and letter constants for the default 
 * constructor.
 * TODO Only allow grades to be instantiated by the grader.
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-08
 */
public class Grade {
	private int percentage;
	private String letter;
	
	public Grade() {
		percentage = 0;
		letter = "F ";
	}
	
	public Grade(int percentage, String letter) {
		this.percentage = percentage;
		this.letter = letter;
	}
	
	public int getPercentage() {
		return percentage;
	}
	
	void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public String getLetter() {
		return letter;
	}
	
	void setLetter(String letter) {
		this.letter = letter;
	}
}
