package za.ac.cput.factory.tertiaryInstitution;

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.tertiaryInstitution.Enroll;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * EnrollFactoryTest.java
 * TTD for EnrollFactoryTest
 * Author: Tisetso Kotoana
 * Date:10 June 2021
 */

class EnrollFactoryTest {


    @Test
    void Enroll() {

        Date d1 = new Date(2021,06,11);
        Enroll build = EnrollFactory.build("20201ABC","ADP467","28/02/2021","Yes");
        System.out.print(build);
        assertNotNull(build);
        assertEquals(build,build);


    }
}