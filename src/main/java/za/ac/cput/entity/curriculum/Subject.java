package za.ac.cput.entity.curriculum;
/**
 * Subject.java
 * Created Entity for the Subject using builder pattern.
 * Author: Shane Knoll (218279124)
 * Date:01 June 2021
 */

public class Subject {
    //this is my instance variables/attributes for th entity class Subject:
    private String subjectCode;
    private String subjectName;
    private int lecturerID;
    private String courseCode;
    private int semesterID;

    private Subject(){}

    //toString for the Subject class
    @Override
    public String toString() {
        return "Subject{" +
                "subjectCode=" + subjectCode +
                ", subjectName='" + subjectName + '\'' +
                ", lecturerID=" + lecturerID +
                ", courseCode='" + courseCode + '\'' +
                ", semesterID=" + semesterID +
                '}';
    }

    private Subject(SubjectBuilder b){
        this.subjectCode=b.subjectCode;
        this.subjectName=b.subjectName;
        this.lecturerID=b.lecturerID;
        this.courseCode=b.courseCode;
        this.semesterID=b.semesterID;

    }
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getSemesterID() {
        return semesterID;
    }




    //this is the static class SubjectBuilder/builder class in the Subject Entity
    public static class SubjectBuilder{
        private String subjectCode;
        private String subjectName;
        private int lecturerID;
        private String courseCode;
        private int semesterID;

        //all the setters in SubjectBuilder static class:
        public SubjectBuilder setsubjectCode(String subjectCode){

            this.subjectCode=subjectCode;
            return this;
        }

        public SubjectBuilder setsubjectName(String subjectname){

            subjectName=subjectname;
            return this;
        }

        public SubjectBuilder setlecturerID(int lectureriD){

            lecturerID=lectureriD;
            return this;
        }
        public SubjectBuilder setcourseCode(String coursecode){

            this.courseCode=coursecode;
            return this;
        }
        public SubjectBuilder setsemesterID(int semesteriD){

            semesterID=semesteriD;
            return this;
        }

        public SubjectBuilder copy (Subject s){
            this.subjectCode= s.subjectCode;
            this.subjectName=s.subjectName;
            this.lecturerID=s.lecturerID;
            this.courseCode=s.courseCode;
            this.semesterID=s.semesterID;



            return this;
        }

        public Subject build(){

            return new Subject(this);
        }


    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }




}
