package com.rishiraj.websocket_springBoot.ServiceImplementations;

import com.rishiraj.websocket_springBoot.entities.Message;
import com.rishiraj.websocket_springBoot.entities.Room;
import com.rishiraj.websocket_springBoot.repositories.RoomRepository;
import com.rishiraj.websocket_springBoot.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Room createRoom(String roomId) {
        //check if roomId is only created or not
        Room room = roomRepository.findByRoomId(roomId);
        if (room != null) {
            //it means a room with this Id already exists.
            throw new RuntimeException("Room ID : " + roomId + " already exists, try different Room ID");
        }

        //create a room with this room Id
        Room newRoom = new Room();
        newRoom.setRoomId(roomId);

        Room saveRoom = roomRepository.save(newRoom);
        return saveRoom;
    }


    @Override
    public Room getRoomByRoomId(String roomId) {
        //this method will be called when a user is trying to join a room by entering a room id

        Room room = roomRepository.findByRoomId(roomId);

        if (room == null) {
            //it means a room with this Id already exists.
            throw new RuntimeException("Room ID : " + roomId + " doesn't exist, please enter the correct Room ID");
        }

        return room;
    }

    @Override
    public List<Message> getAllMessagesOfARoom(String roomId) {

        Room room = roomRepository.findByRoomId(roomId);
        if (room == null) {
            //it means a room with this Id already exists.
            throw new RuntimeException("Room ID : " + roomId + " doesn't exist.");
        }

        return room.getMessages();

    }
}
