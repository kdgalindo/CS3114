package data;

/**
 * Student Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-08-19
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
	
	public void setPercentage(int percentage) {
		this.percentage = percentage;
	}
	
	public String getLetter() {
		return letter;
	}
	
	public void setLetter(String letter) {
		this.letter = letter;
	}
}
