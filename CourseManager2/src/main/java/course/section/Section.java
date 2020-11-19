package course.section;

public class Section {
	private final int number;
	private boolean modifiable;
	
	public Section(int number) {
		this.number = number;
		this.modifiable = true;
	}
	
	public int getNumber() {
		return number;
	}
	
	public boolean isModifiable() {
		return modifiable;
	}
	
	public void setModifiable(boolean modifiable) {
		this.modifiable = modifiable;
	}
}
