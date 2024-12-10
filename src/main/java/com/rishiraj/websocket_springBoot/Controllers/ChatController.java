package com.rishiraj.websocket_springBoot.Controllers;

import com.rishiraj.websocket_springBoot.entities.Message;
import com.rishiraj.websocket_springBoot.entities.Room;
import com.rishiraj.websocket_springBoot.payload.MessageRequest;
import com.rishiraj.websocket_springBoot.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Nested;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private RoomRepository roomRepository;

    @MessageMapping("/sendMessage/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public Message sendMessage(
            @DestinationVariable String roomId,
            @RequestBody MessageRequest messageRequest) {

        Room room = roomRepository.findByRoomId(roomId);

        Message message = new Message();
        message.setContent(messageRequest.getContent());
        message.setSender(messageRequest.getSender());
        message.setTimeStamp(LocalDateTime.now());

        log.info("{}", message);
        if (room != null) {
            room.getMessages().add(message);
            roomRepository.save(room);
        }
        else{
            throw new RuntimeException("Room with room ID: " + roomId + " not found");
        }
        return message;
    }
}
