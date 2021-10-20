/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */
package za.ac.cput.factory.physical;

import za.ac.cput.entity.physical.Building;

public class BuildingFactory {

    public static Building build(String buildingID, String buildingName, int roomCount, String buildingAddress) {

        if(buildingID.isEmpty() || roomCount <= 0 || buildingName.isEmpty() || buildingAddress.isEmpty())
            return null;

        return new Building.BuildingBuilder().setBuildingID(buildingID).setBuildingName(buildingName).setRoomCount(roomCount).setBuildingAddress(buildingAddress).build();
    }
}
