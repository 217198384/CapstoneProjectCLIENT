package za.ac.cput.factory.curriculum;

import za.ac.cput.entity.curriculum.Subject;

import java.util.UUID;

/**
 * SubjectFactory.java
 * Created Factory class called SubjectFactory for Subject entity
 * Author: Shane Knoll (218279124)
 * Date:10 June 2021
 */


public class SubjectFactory {

    public static Subject build( String subjectName, int lecturerID, String courseCode, int semesterID) {

        if(subjectName.isEmpty()||lecturerID<=0||courseCode.isEmpty()||semesterID<=0){
            return null;}

        String subjectCode = UUID.randomUUID().toString();

        return new Subject.SubjectBuilder()
                .setsubjectCode(subjectCode).setsubjectName(subjectName).setlecturerID(lecturerID).setcourseCode(courseCode).setsemesterID(semesterID).build();

    }


}
