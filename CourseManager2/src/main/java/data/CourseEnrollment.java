package data;

import section.SectionEnrollment;

public class CourseEnrollment {
	private final SectionEnrollment[] sectionEnrollments;
	
	public CourseEnrollment(SectionEnrollment[] sectionEnrollments) {
		this.sectionEnrollments = sectionEnrollments;
	}
	
	public SectionEnrollment[] getSectionEnrollments() {
		return sectionEnrollments;
	}
}
