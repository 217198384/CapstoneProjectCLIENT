package za.ac.cput.entity.person;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Student.java
 * Student entity created using builder pattern.
 * @author Dylan Koevort 218088159
 * 31 May 2021
 */

@Entity
public class Student {

    @Id
    private String studentId;
    private int age;
    private String firstName, lastName, emailAddress, contactNo;

    public Student() {}

    private Student(StudentBuilder builder) {
        this.studentId = builder.studentId;
        this.age = builder.age;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.emailAddress = builder.emailAddress;
        this.contactNo = builder.contactNo;
    }

    public String getStudentId() {
        return studentId;
    }

    public int getAge() {
        return age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getContactNo() {
        return contactNo;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", age=" + age +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", contactNo='" + contactNo + '\'' +
                '}';
    }

    public static class StudentBuilder {

        private int age;
        private String studentId, firstName, lastName;
        private String emailAddress, contactNo;

        public StudentBuilder setStudentId(String studentId) {
            this.studentId = studentId;
            return this;
        }

        public StudentBuilder setAge(int age) {
            this.age = age;
            return this;
        }

        public StudentBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public StudentBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public StudentBuilder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public StudentBuilder setContactNo(String contactNo) {
            this.contactNo = contactNo;
            return this;
        }

        public Student build() {
            return new Student(this);
        }

        public StudentBuilder copy(Student student) {
            this.studentId = student.studentId;
            this.age = student.age;
            this.firstName = student.firstName;
            this.lastName = student.lastName;
            this.emailAddress = student.emailAddress;
            this.contactNo = student.contactNo;

            return this;
        }
    }
}
