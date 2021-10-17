package za.ac.cput.factory.person;

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.person.Lecturer;
import za.ac.cput.factory.person.LecturerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LecturerFactoryTest.java
 * TTD for LecturerFactoryTest
 * Author: Shane Knoll (218279124)
 * Date:10 June 2021
 */
class LecturerFactoryTest {

    @Test
    void build() {
        Lecturer build = LecturerFactory.build("Max", "white", 43, "maxwhite@gmail.com", "0785556432");
        System.out.print(build);
        assertNotNull(build);
        assertEquals(build,build);
        assertSame(build,build);

    }
    @Test
    void fail(){
    }
}