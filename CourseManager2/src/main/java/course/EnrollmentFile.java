package course;

import data.CourseEnrollment;

public class EnrollmentFile {
	public static CourseEnrollment readFrom(String filename) {
		CourseEnrollment cEnrollment = null;
		switch (getFileType(filename)) {
			case ".csv":
				cEnrollment = EnrollmentTextFile.readFrom(filename);
				break;
			case ".data":
				cEnrollment = EnrollmentBinaryFile.readFrom(filename);
				break;
			default:
				cEnrollment = null;
				break;
		}
        return cEnrollment;
	}
	
	public static void writeTo(CourseEnrollment cEnrollment, String filename) {
		switch (getFileType(filename)) {
			case ".data":
				EnrollmentBinaryFile.writeTo(cEnrollment, filename);
				break;
			default:
				break;
		}
	}
	
	private static String getFileType(String filename) {
		return filename.substring(filename.lastIndexOf('.'));
	}
}
