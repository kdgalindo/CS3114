package section;

import data.Student;

public class SectionEnrollment {
	private final int sectionNumber;
	private final Student[] students;
	
	public SectionEnrollment(int sectionNumber, Student[] students) {
		this.sectionNumber = sectionNumber;
		this.students = students;
	}
	
	public int getSectionNumber() {
		return sectionNumber;
	}
	
	public Student[] getStudents() {
		return students;
	}
}
