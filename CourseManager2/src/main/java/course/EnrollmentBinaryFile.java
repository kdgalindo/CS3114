package course;

import java.io.IOException;
import java.io.RandomAccessFile;

import data.*;

public class EnrollmentBinaryFile {
	private final static String HEADER = "CS3114atVT";
	private final static String DELIMITER = "GOHOKIES";	
	
    public static CourseEnrollment readFrom(String filename) {
    	CourseEnrollment enrollment = null;
    	try {
			RandomAccessFile raf = new RandomAccessFile(filename, "r");
			raf.seek(HEADER.length());
			enrollment = new CourseEnrollment(readSEnrollmentsFrom(raf));
			raf.close();
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    	return enrollment;
    }
    
    private static SectionEnrollment[] readSEnrollmentsFrom(RandomAccessFile raf) throws IOException {
    	SectionEnrollment[] enrollments = new SectionEnrollment[raf.readInt()];
    	for (int i = 0; i < enrollments.length; ++i) {
    		enrollments[i] = new SectionEnrollment(i + 1, readStudentsFrom(raf));
    		raf.seek(raf.getFilePointer() + DELIMITER.length());
    	}
    	return enrollments;
    }
    
    private static Student[] readStudentsFrom(RandomAccessFile raf) throws IOException {
    	Student[] students = new Student[raf.readInt()];
    	for (int i = 0; i < students.length; ++i) {
    		students[i] = readStudentFrom(raf);
    	}
    	return students;
    }

    private static Student readStudentFrom(RandomAccessFile raf) throws IOException {
    	return new Student(readIdentityFrom(raf), readGradeFrom(raf));
    }
    
    private static Identity readIdentityFrom(RandomAccessFile raf) throws IOException {
    	long personalID = raf.readLong();
    	return new Identity(personalID, readFullNameFrom(raf));
    }
    
    private static FullName readFullNameFrom(RandomAccessFile raf) throws IOException {
		String firstName = readNameFrom(raf);
        String lastName = readNameFrom(raf);
        return new FullName(firstName, lastName);
    }
    
	private static String readNameFrom(RandomAccessFile raf) throws IOException {
		StringBuilder sb = new StringBuilder();
		char letter;
		while ((letter = (char)raf.readByte()) != '$') {
			sb.append(letter);
		}
		return sb.toString();
	}
	
	private static Grade readGradeFrom(RandomAccessFile raf) throws IOException {
		int percentageGrade = raf.readInt();
		return new Grade(percentageGrade, readLetterGradeFrom(raf));
	}
	
	private static String readLetterGradeFrom(RandomAccessFile raf) throws IOException {
		byte[] letterGrade = new byte[2];
		raf.read(letterGrade);
		return new String(letterGrade);
	}
	
	public static void writeTo(CourseEnrollment enrollment, String filename) {
		try {
			RandomAccessFile raf = new RandomAccessFile(filename, "rw");
			raf.writeBytes(HEADER);
			writeSEnrollmentsTo(enrollment.getSectionEnrollments(), raf);
			raf.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void writeSEnrollmentsTo(SectionEnrollment[] enrollments, RandomAccessFile raf) throws IOException {
		int numOfSections = enrollments.length;
		raf.writeInt(numOfSections);
		for (int i = 0; i < numOfSections; ++i) {
			writeStudentsTo(enrollments[i].getStudents(), raf);
			raf.writeBytes(DELIMITER);
		}
	}
	
	private static void writeStudentsTo(Student[] students, RandomAccessFile raf) throws IOException {
		int numOfStudents = students.length;
		raf.writeInt(numOfStudents);
		for (int i = 0; i < numOfStudents; ++i) {
			writeStudentTo(students[i], raf);
		}
	}

	private static void writeStudentTo(Student student, RandomAccessFile raf) throws IOException {
		writeIdentityTo(student.getIdentity(), raf);
		writeGradeTo(student.getGrade(), raf);
	}
	
	private static void writeIdentityTo(Identity identity, RandomAccessFile raf) throws IOException {
		raf.writeLong(identity.getPersonalID());
		writeFullNameTo(identity.getFullName(), raf);
	}
	
	private static void writeFullNameTo(FullName fullName, RandomAccessFile raf) throws IOException {
		writeNameTo(fullName.getFirstName(), raf);
		writeNameTo(fullName.getLastName(), raf);
	}
	
    private static void writeNameTo(String name, RandomAccessFile raf) throws IOException {
    	raf.writeBytes(name + "$");
    }

	private static void writeGradeTo(Grade grade, RandomAccessFile raf) throws IOException {
		raf.writeInt(grade.getPercentage());
		raf.writeBytes(grade.getLetter());
	}
}
