package za.ac.cput.factory.tertiaryInstitution;

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.tertiaryInstitution.Department;
import za.ac.cput.factory.tertiaryInstitution.DepartmentFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * CourseFactoryTest.java
 * @author Jonathan Kleynhans 217268153
 * 09 June 2021
 */

class DepartmentFactoryTest {

    @Test
    void test(){
        Department department = DepartmentFactory.build("021", "Information Technology", " ");
        System.out.println(department);
        assertNotNull(department);
    }
}