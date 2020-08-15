package student;

public class Grade {
	private int percentage;
	private String letter;
	
	public Grade() {
		percentage = 0;
		letter = "F";
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
