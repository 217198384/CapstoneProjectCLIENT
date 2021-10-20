package za.ac.cput.factory.curriculum;

/**
 *
 * ExaminationFactoryTest.java
 * Test case for Examination entity
 * @author Dylan Koevort 218088159
 * 9 June 2021
 *
 */

/**
 *
 * ExaminationFactoryTest.java
 * Refactored date to localDate by Dinelle Kotze 219089302
 * 31 July 2021
 *
 */


import org.junit.jupiter.api.Test;
import za.ac.cput.entity.curriculum.Examination;
import za.ac.cput.factory.curriculum.ExaminationFactory;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ExaminationFactoryTest {

    //Date date = new Date(121, Calendar.JUNE, 14);
    @Test
    void test() {
        Examination exam = ExaminationFactory.build( 10012, "Mathematics", "2021/08/20");
        System.out.println(exam);
        assertNotNull(exam);
    }

}