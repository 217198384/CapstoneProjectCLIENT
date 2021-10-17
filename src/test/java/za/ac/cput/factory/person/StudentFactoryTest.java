package za.ac.cput.factory.person;

/**
 *
 * StudentFactoryTest.java
 * Test case for Student entity
 * @author Dylan Koevort 218088159
 * 9 June 2021
 *
 */

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.person.Student;
import za.ac.cput.factory.person.StudentFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentFactoryTest {

    @Test
    void test() {
        Student s = StudentFactory.build("Dylan", "Koevort","218088159@mycput.ac.za", "0736085710", 21 );
        System.out.println(s);
        assertNotNull(s);
    }

}