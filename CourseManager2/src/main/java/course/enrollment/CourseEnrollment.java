package course.enrollment;

import course.section.SectionEnrollment;

public class CourseEnrollment {
	private final SectionEnrollment[] sectionEnrollments;
	
	public CourseEnrollment(SectionEnrollment[] sectionEnrollments) {
		this.sectionEnrollments = sectionEnrollments;
	}
	
	public SectionEnrollment[] getSectionEnrollments() {
		return sectionEnrollments;
	}
}
