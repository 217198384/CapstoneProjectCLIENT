package za.ac.cput.entity.tertiaryInstitution;

import java.util.Date;

/**
 * Enroll.java
 * Entity for semester using builder pattern
 * Author: Tisetso Kotoana
 * Date:01 June 2021
 */
public class Semester {
   private String semesterID;
   private Date semesterStart;
   private Date semesterEnd;

   public Semester(SemesterBuilder semester) {
      this.semesterID = semester.semesterID;
      this.semesterStart = semester.semesterStart;
      this.semesterEnd = semester.semesterEnd;
   }
   @Override
   public String toString() {
      return "SemesterBuilder{" +
              "semesterID=" + semesterID +
              ", semesterStart=" + semesterStart +
              ", semesterEnd=" + semesterEnd +
              '}';
   }

   public String getSemesterID() {

      return semesterID;
   }
   public Date getSemesterStart(){
      return semesterStart;
   }
   public Date getSemesterEnd(){
      return semesterEnd;
   }

   public static class SemesterBuilder{
      private String semesterID;
      private Date semesterStart;
      private Date semesterEnd;

      public SemesterBuilder setSemesterID(String semesterID) {
         this.semesterID = semesterID;
         return this;
      }

      public SemesterBuilder setSemesterStart(Date semesterStart) {
         this.semesterStart = semesterStart;
         return this;
      }

      public SemesterBuilder setSemesterEnd(Date semesterEnd) {
         this.semesterEnd = semesterEnd;
         return this;
      }

      public Semester build(){

         return new Semester(this);
      }
      public SemesterBuilder copy(Semester semester){
         this.semesterID=semester.semesterID;
         this.semesterStart=semester.semesterStart;
         this.semesterEnd=semester.semesterEnd;

         return this;
      }


   }
}
