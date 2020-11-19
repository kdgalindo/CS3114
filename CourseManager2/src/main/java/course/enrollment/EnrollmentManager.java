package course.enrollment;

import util.BST;

public class EnrollmentManager {
	private BST<Long, Integer> idSectionNumDB;
	
	public EnrollmentManager() {
		idSectionNumDB = new BST<Long, Integer>();
	}
	
	public boolean isEnrolled(long personalID) {
		return idSectionNumDB.find(personalID) != null;
	}
	
	public Integer findEnrollment(long personalID) {
		return idSectionNumDB.find(personalID);
	}
	
	public void enroll(long personalID, int sectionNum) {
		idSectionNumDB.insert(personalID, sectionNum);
	}
	
	public void unenroll(long personalID) {
		idSectionNumDB.remove(personalID);
	}
	
	public void clear() {
		idSectionNumDB.clear();
	}
}
