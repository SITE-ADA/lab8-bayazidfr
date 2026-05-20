package az.edu.ada.wm2.courseservice.exception;

public class PrerequisiteNotSatisfiedException extends RuntimeException {

    public PrerequisiteNotSatisfiedException(Long courseId, Long prerequisiteCourseId, Long studentId) {
        super("Student " + studentId
                + " cannot enroll in course " + courseId
                + " because prerequisite course " + prerequisiteCourseId + " is required.");
    }
}