package za.ac.cput.factory.tertiaryInstitution;
import za.ac.cput.entity.tertiaryInstitution.Semester;

import java.util.Date;

/**
 * SemesterFactory.java
 * TTD for SemesterFactory
 * Author: Tisetso Kotoana
 * Date:10 June 2021
 */

public class SemesterFactory {
   public static Semester build(String semesterID, Date semesterStart, Date semesterEnd){
       if(semesterID.equals("")||semesterStart.equals("")  || semesterEnd.equals(""))
           return null;

       return new Semester.SemesterBuilder()
               .setSemesterID(semesterID)
               .setSemesterStart(semesterStart)
               .setSemesterEnd(semesterEnd)
               .build();
   }
}
