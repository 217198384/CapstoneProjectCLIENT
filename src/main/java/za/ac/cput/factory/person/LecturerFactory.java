package za.ac.cput.factory.person;

import za.ac.cput.entity.person.Lecturer;

import java.util.UUID;

/**
 * LecturerFactory.java
 * Created Factory class called LecturerFactory for Lecturer entity
 * Author: Shane Knoll (218279124)
 * Date:10 June 2021
 */

public class LecturerFactory {

    public static Lecturer build(String firstName, String lastName, int age, String emailAddress, String contactNo) {



        String lecturerID = UUID.randomUUID().toString();

        return new Lecturer.LecturerBuilder()
                .setlecturerID(lecturerID).setfirstName(firstName).setlastName(lastName).setage(age).setemailAddress(emailAddress).setcontactNo(contactNo).build();

    }
}
