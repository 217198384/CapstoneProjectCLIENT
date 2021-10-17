package za.ac.cput.factory.physical;
/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */

import org.junit.jupiter.api.Test;
import za.ac.cput.entity.physical.Building;
import za.ac.cput.factory.physical.BuildingFactory;

import static org.junit.jupiter.api.Assertions.*;

class BuildingFactoryTest {

    @Test
    void build() {
        Building build = BuildingFactory.build("001", 100, "Administration Building", "01 Sesame street, Sesame Town, ABC123");
        System.out.println(build);
        assertNotNull(build);
        assertEquals(build, build);
    }

}