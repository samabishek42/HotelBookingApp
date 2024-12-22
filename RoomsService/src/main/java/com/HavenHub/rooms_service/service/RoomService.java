package com.HavenHub.rooms_service.service;

import com.HavenHub.rooms_service.DTO.RoomsDTO;
import com.HavenHub.rooms_service.entity.Rooms;
import com.HavenHub.rooms_service.repository.RoomsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RoomService {

      @Autowired
      RoomsRepo rr;

      public String addRooms(RoomsDTO rooms) {
            Rooms r=new Rooms(rooms.getHotel_id(),rooms.getRoom_number(),rooms.getRoomType(),
                    rooms.getPrice(),rooms.getIsAvailable(),rooms.getRoom_photo());
            rr.save(r);
            return "Success";
      }

      public List<Rooms> getRooms(int hotel_id) {
            return rr.findByHotelId(hotel_id);
      }
}
