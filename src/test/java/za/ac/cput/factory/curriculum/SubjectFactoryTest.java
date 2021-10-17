package za.ac.cput.factory.curriculum;

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.curriculum.Subject;
import za.ac.cput.factory.curriculum.SubjectFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SubjectFactoryTest.java
 * TTD for SubjectFactoryTest
 * Author: Shane Knoll (218279124)
 * Date:10 June 2021
 */
class SubjectFactoryTest {

    @Test
    void build() {

        Subject b = SubjectFactory.build("Applications Development 3",8732635,"363",321);
        System.out.print(b);
        assertNotNull(b);
        assertEquals(b,b);
        assertEquals(b,b);
        assertSame(b,b);

    }
    @Test
    void fail(){
    }
}