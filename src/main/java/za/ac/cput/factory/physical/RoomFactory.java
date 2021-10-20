/**
 * author: Llewelyn Klaase
 * student no: 216267072
 */
package za.ac.cput.factory.physical;

import za.ac.cput.entity.physical.Room;

public class RoomFactory {

    public static Room build(String roomCode, String roomType, int roomCapacity, int roomFloor, int buildingID) {

        if(roomCode.isEmpty() || roomType.isEmpty() ||  roomCapacity <= 0 || roomFloor <= 0 || buildingID <= 0)
            return null;

        return new Room.RoomBuilder().setRoomCode(roomCode).setRoomType(roomType).setRoomCapacity(roomCapacity).setRoomFloor(roomFloor).setBuildingID(buildingID).build();
    }
}
