package hotelKarlita.model;

public class FreeRoom extends Room {
    public FreeRoom() {
    }

    public FreeRoom(int roomNumber, RoomType roomType) {
        super(roomNumber, 0.0, roomType);
    }

    @Override
    public Double getRoomPrice() {
        return 0.0;
    }

    @Override
    public boolean isFree() {
        return false;
    }
}
