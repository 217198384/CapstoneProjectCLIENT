package za.ac.cput.entity.tertiaryInstitution;

/**
 * Course.java
 * Course entity created using builder pattern.
 * @author Jonathan Kleynhans 217268153
 * 01 June 2021
 * Updated: 28 August 2021
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Course")
public class Course {

    @Id
    private String departmentId;
    private String courseCode;
    private String title;
    private int credit;
    private int duration;
    private boolean fullTime;

    public Course(){}

    public Course(CourseBuilder course) {
        this.courseCode = course.courseCode;
        this.title = course.title;
        this.departmentId = course.departmentId;
        this.credit = course.credit;
        this.duration = course.duration;
        this.fullTime = course.fullTime;
    }

    public String getdepartmentId(){
        return departmentId;
    }

    public String getcourseCode() {
        return courseCode;
    }

    public String getcourseTitle(){
        return title;
    }

    public int getCredit(){
        return credit;
    }

    public int getDuration(){
        return duration;
    }

    public boolean getFullTime(){
        return fullTime;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                ", departmentID=" + departmentId +
                ", credit=" + credit +
                ", duration=" + duration +
                ", fullTime=" + fullTime +
                '}';
    }

    public static class CourseBuilder {

        private String courseCode, title;
        private String departmentId;
        private int credit;
        private int duration;
        private boolean fullTime;

        public CourseBuilder setCourseCode(String courseCode) {
            this.courseCode = courseCode;
            return this;
        }

        public CourseBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public CourseBuilder setDepartmentId(String departmentId) {
            this.departmentId = departmentId;
            return this;
        }

        public CourseBuilder setCredit(int credit) {
            this.credit = credit;
            return this;
        }

        public CourseBuilder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public CourseBuilder setFullTime(boolean fullTime) {
            this.fullTime = fullTime;
            return this;
        }

        public Course build() {
            return new Course(this);
        }

        public CourseBuilder copy (Course course){
            this.courseCode = course.courseCode;
            this.title = course.title;
            this.departmentId = course.departmentId;
            this.credit = course.credit;
            this.duration = course.duration;
            this.fullTime = course.fullTime;

            return this;
        }
    }
}
