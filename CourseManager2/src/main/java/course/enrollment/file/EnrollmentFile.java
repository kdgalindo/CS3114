package course.enrollment.file;

import course.enrollment.CourseEnrollment;

/**
 * EnrollmentFile Class
 * 
 * 
 * 
 * @author kyleg997 Kyle Galindo
 * @version 2021-01-05
 */
public final class EnrollmentFile {
	private EnrollmentFile() {
		// Empty
	}
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
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
	
	/**
	 * 
	 * @param cEnrollment
	 * @param filename
	 */
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
