package za.ac.cput.factory.tertiaryInstitution;

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.tertiaryInstitution.Course;
import za.ac.cput.factory.tertiaryInstitution.CourseFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * CourseFactoryTest.java
 * @author Jonathan Kleynhans 217268153
 * 09 June 2021
 */

class CourseFactoryTest {

    @Test
    void test(){
        Course course = CourseFactory.build("ADP", "Applications Development Practise", "057", 20, 3, true);
        System.out.println(course);
        assertNotNull(course);
    }
}