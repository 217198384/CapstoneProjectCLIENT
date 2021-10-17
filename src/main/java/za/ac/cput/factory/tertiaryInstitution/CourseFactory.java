package za.ac.cput.factory.tertiaryInstitution;

import za.ac.cput.entity.tertiaryInstitution.Course;

/**
 * CourseFactory.java
 * Factory class for Course.java
 * @author Jonathan Kleynhans 217268153
 * 09 June 2021
 */

public class CourseFactory {

    public static Course build (String courseCode, String title, String departmentId, int credit, int duration,boolean fullTime){

        if(courseCode.isEmpty() || title.isEmpty() || departmentId.isEmpty() || credit <= 0 || duration <= 0){
            return null;
        }

        return new Course.CourseBuilder()
                .setCourseCode(courseCode)
                .setTitle(title)
                .setDepartmentId(departmentId)
                .setCredit(credit)
                .setDuration(duration)
                .setFullTime(fullTime)
                .build();
    }
}