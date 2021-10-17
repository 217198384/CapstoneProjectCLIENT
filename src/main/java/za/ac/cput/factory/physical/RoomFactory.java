package za.ac.cput.factory.physical;

import za.ac.cput.entity.physical.Room;

/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */

public class RoomFactory {

    public static Room build(String roomType, String roomCode, int roomCapacity, int roomFloor, int buildingID) {

        if(roomType.isEmpty() || roomCode.isEmpty() || roomCapacity <= 0 || roomFloor <= 0 || buildingID <= 0)
            return null;

        return new Room.RoomBuilder().setRoomType(roomType).setRoomCode(roomCode).setRoomCapacity(roomCapacity).setRoomFloor(roomFloor).setBuildingID(buildingID).build();
    }
}
