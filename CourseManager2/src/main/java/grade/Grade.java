package grade;

/**
 * Grade Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-07
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
