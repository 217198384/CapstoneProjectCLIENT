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
        Building build = BuildingFactory.build("1", "Engineering Building", 100, "01 Sesame street");
        System.out.println(build);
        assertNotNull(build);
        assertEquals(build, build);
    }

}