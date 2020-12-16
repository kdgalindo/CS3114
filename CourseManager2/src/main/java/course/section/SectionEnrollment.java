package course.section;

import student.Student;

/** 
 * SectionEnrollment Class
 * 
 * TODO Change students from array to immutable list.
 *
 * @author kyleg997 Kyle Galindo
 * @version 2020-12-08
 */
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
