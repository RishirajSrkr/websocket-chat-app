package com.rishiraj.websocket_springBoot.services;

import com.rishiraj.websocket_springBoot.entities.Message;
import com.rishiraj.websocket_springBoot.entities.Room;

import java.util.List;

public interface RoomService {

    Room createRoom(String roomId);

    Room getRoomByRoomId(String roomID);

    List<Message> getAllMessagesOfARoom(String roomId);

}
