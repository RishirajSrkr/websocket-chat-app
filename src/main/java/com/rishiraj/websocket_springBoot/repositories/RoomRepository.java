package com.rishiraj.websocket_springBoot.repositories;

import com.rishiraj.websocket_springBoot.entities.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepository extends MongoRepository<Room, String> {

    Room findByRoomId(String roomId);
}
