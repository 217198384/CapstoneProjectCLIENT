package za.ac.cput.factory.tertiaryInstitution;
import za.ac.cput.entity.tertiaryInstitution.Enroll;

import java.util.Date;

/**
 * EnrollFactory.java
 * TTD for EnrollFactory
 * Author: Tisetso Kotoana
 * Date:10 June 2021
 */

public class EnrollFactory {
    public static Enroll build (String studentID, String courseCode, Date date, boolean paymentReceived){

        if(studentID.equals("")||courseCode.equals("") || date.equals("") ) {
            return null;
        }

        return new Enroll.EnrollBuilder()

                .setStudentID(studentID)
                .setCourseCode(courseCode)
                .setDate(date)
                .setPaymentReceived(paymentReceived)
                .build();
    }
}
