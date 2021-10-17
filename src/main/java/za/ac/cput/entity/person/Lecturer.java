package za.ac.cput.entity.person;
/**
 * Lecturer.java
 * Created Entity for the Lecturer using builder pattern.
 * Author: Shane Knoll (218279124)
 * Date:01 June 2021
 */
public class Lecturer {
    //this is my instance variables/attributes for th entity class Lecturer:
    private String lecturerID;
    private String firstName,lastName;
    private int age;
    private String emailAddress;
    private String contactNo;

    private Lecturer(){}

    private Lecturer(LecturerBuilder b){
        this.lecturerID=b.lecturerID;
        this.firstName=b.firstName;
        this.lastName=b.lastName;
        this.age= b.age;
        this.emailAddress= b.emailAddress;
        this.contactNo= b.contactNo;

    }
    public String getLecturerID() {
        return lecturerID;
    }
    //this is the static class LecturerBuilder/builder class in the Lecturer Entity

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    //toString for the Subject class
    @Override
    public String toString() {
        return "Lecturer{" +
                "lecturerID=" + lecturerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", emailAddress='" + emailAddress + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }



    public static class LecturerBuilder{
        private String lecturerID;
        private String firstName,lastName;
        private int age;
        private String emailAddress;
        private String contactNo;

        //all the setters in LecturerBuilder static class:
        public LecturerBuilder setlecturerID(String lecturerID){

            this.lecturerID=lecturerID;
            return this;
        }

        public LecturerBuilder setfirstName(String firstname){

            firstName=firstname;
            return this;
        }

        public LecturerBuilder setlastName(String lastname){

            lastName=lastname;
            return this;
        }
        public LecturerBuilder setage(int age){

            this.age=age;
            return this;
        }
        public LecturerBuilder setemailAddress(String emailaddress){

            emailAddress=emailaddress;
            return this;
        }

        public LecturerBuilder setcontactNo(String contactno){

            contactNo=contactno;
            return this;
        }
        public LecturerBuilder copy (Lecturer l){
            this.lecturerID= l.lecturerID;
            this.firstName=l.firstName;
            this.lastName=l.lastName;
            this.age=l.age;
            this.emailAddress=l.emailAddress;
            this.contactNo=l.contactNo;

            return this;
        }

        public Lecturer build(){

            return new Lecturer(this);
        }


    }




}
