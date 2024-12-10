package com.rishiraj.websocket_springBoot.Controllers;

import com.rishiraj.websocket_springBoot.ServiceImplementations.RoomServiceImpl;
import com.rishiraj.websocket_springBoot.entities.Message;
import com.rishiraj.websocket_springBoot.entities.Room;
import com.rishiraj.websocket_springBoot.services.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")

@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<?> createNewRoom(@RequestBody String roomId) {

        log.info(roomId);

        try {
            Room createdRoom = roomService.createRoom(roomId);
            log.info("{}", createdRoom);

            return new ResponseEntity<>(createdRoom, HttpStatus.CREATED);
        } catch (
                RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (
                Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId) {
        try {
            Room room = roomService.getRoomByRoomId(roomId);
            log.info("room found {}", room);
            return new ResponseEntity<>(room, HttpStatus.OK);
        } catch (
                RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getAllMessagesOfARoom(
            @PathVariable String roomId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize

    ) {
        try {
            List<Message> messages = roomService.getAllMessagesOfARoom(roomId);

            int start = Math.max(0, messages.size() - (page + 1) * pageSize);
            int end = Math.min(messages.size(), start + pageSize);

            List<Message> paginatedMessages = messages.subList(start, end);

            return new ResponseEntity<>(paginatedMessages, HttpStatus.OK);

        } catch (
                RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
