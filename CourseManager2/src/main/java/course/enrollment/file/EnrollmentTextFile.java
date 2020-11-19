package course.enrollment.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import course.enrollment.CourseEnrollment;
import course.section.SectionEnrollment;
import data.*;
import grade.Grade;
import identity.FullName;
import identity.Identity;

/**
 * EnrollmentTextFile Class
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2020-11-05
 */
public class EnrollmentTextFile {
	private static class Enrollment {
		private final int sectionNumber;
		private final Student student;
		
		public Enrollment(int sectionNumber, Student student) {
			this.sectionNumber = sectionNumber;
			this.student = student;
		}
		
		public int getSectionNumber() {
			return sectionNumber;
		}
		
		public Student getStudent() {
			return student;
		}
	}
	
	public static CourseEnrollment readFrom(String filename) {
		Enrollment[] enrollments = readEnrollmentsFrom(filename);
		return new CourseEnrollment(makeSEnrollmentsFrom(enrollments));
	}
	
	private static Enrollment[] readEnrollmentsFrom(String filename) {
		ArrayList<Enrollment> enrollments = new ArrayList<Enrollment>();
		try {
			Scanner s = new Scanner(new File(filename));
			while (s.hasNextLine()) {
				enrollments.add(readEnrollmentFrom(s.nextLine()));
			}
			s.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return enrollments.toArray(new Enrollment[enrollments.size()]);
	}
	
	private static Enrollment readEnrollmentFrom(String line) {
    	Scanner s = new Scanner(line);
    	s.useDelimiter("\\s*,\\s*");
    	Enrollment enrollment = new Enrollment(s.nextInt(), readStudentFrom(s));
    	s.close();
    	return enrollment;
	}
    
	private static Student readStudentFrom(Scanner s) {
        return new Student(readIdentityFrom(s), readGradeFrom(s));
	}
    
	private static Identity readIdentityFrom(Scanner s) {
        return new Identity(s.nextLong(), readFullNameFrom(s));
	}
    
	private static FullName readFullNameFrom(Scanner s) {
        return new FullName(s.next(), s.next());
	}
	
	private static Grade readGradeFrom(Scanner s) {
		if (s.hasNextInt()) {
			return new Grade(s.nextInt(), s.next());
		}
		return new Grade();
	}
    
    private static SectionEnrollment[] makeSEnrollmentsFrom(Enrollment[] enrollments) {
    	ArrayList<SectionEnrollment> sEnrollments;
    	sEnrollments = new ArrayList<SectionEnrollment>();
		// Initialize
		int prevSectionNum;
		int currSectionNum = enrollments[0].getSectionNumber();
		ArrayList<Student> sStudents = new ArrayList<Student>();
		sStudents.add(enrollments[0].getStudent());
		// Work
		for (int i = 1; i < enrollments.length; ++i) {
			prevSectionNum = currSectionNum;
			currSectionNum = enrollments[i].getSectionNumber();
			if (prevSectionNum != currSectionNum) {
				sEnrollments.add(makeSEnrollmentFrom(prevSectionNum, sStudents));
				sStudents = new ArrayList<Student>();
			}
			sStudents.add(enrollments[i].getStudent());
		}
		// Finalize
		sEnrollments.add(makeSEnrollmentFrom(currSectionNum, sStudents));
		return sEnrollments.toArray(new SectionEnrollment[sEnrollments.size()]);
    }
    
    private static SectionEnrollment makeSEnrollmentFrom(int sNumber, ArrayList<Student> sStudents) {
		Student[] students = sStudents.toArray(new Student[sStudents.size()]);
		return new SectionEnrollment(sNumber, students);
    }
}
