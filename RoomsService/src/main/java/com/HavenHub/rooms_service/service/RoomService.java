//package com.HavenHub.rooms_service.service;
//
//import com.HavenHub.rooms_service.DTO.RoomsDTO;
//import com.HavenHub.rooms_service.entity.Rooms;
//import com.HavenHub.rooms_service.repository.RoomsRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import java.util.List;
//
//@Service
//public class RoomService {
//
//      @Autowired
//      RoomsRepo rr;
//
//      public String addRooms(RoomsDTO rooms) {
//            Rooms r=new Rooms(rooms.getHotel_id(),rooms.getRoom_number(),rooms.getRoomType(),
//                    rooms.getPrice(),rooms.getIsAvailable(),rooms.getRoom_photo());
//            rr.save(r);
//            return "Success";
//      }
//
//      public List<Rooms> getRooms(int hotel_id) {
//            return rr.findByHotelId(hotel_id);
//      }
//}
package com.HavenHub.rooms_service.service;

import com.HavenHub.rooms_service.DTO.RoomsDTO;
import com.HavenHub.rooms_service.entity.Rooms;
import com.HavenHub.rooms_service.repository.RoomsRepo;
import com.HavenHub.rooms_service.DTO.SecondaryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

      @Autowired
      RoomsRepo rr;

      @Autowired
      private SecondaryCache<String, List<Rooms>> roomCache; // Cache for rooms

      @CacheEvict(value = "rooms", key = "'rooms_' + #rooms.hotel_id")
      public String addRooms(RoomsDTO rooms) {
            Rooms r = new Rooms(rooms.getHotel_id(), rooms.getRoom_number(), rooms.getRoomType(),
                    rooms.getPrice(), rooms.getIsAvailable(), rooms.getRoom_photo());
            rr.save(r);
//            invalidateRoomCache(rooms.getHotel_id());  // Invalidate cache after adding rooms
            return "Success";
      }


      @Cacheable(value = "rooms", key = "'rooms_' + #hotelId")
      public List<Rooms> getRooms(int hotelId) {
            return  rr.findByHotelId(hotelId);
      }


      private void invalidateRoomCache(int hotelId) {
            // Invalidate the cache for rooms of this specific hotel
            String cacheKey = "hotel-rooms-" + hotelId;
            roomCache.invalidate(cacheKey);
      }
}

//
//public List<Rooms> getRooms(int hotelId) {
//      String cacheKey = "hotel-rooms-" + hotelId;
//
//      // Check secondary cache for rooms
//      List<Rooms> cachedRooms = roomCache.get(cacheKey);
//      if (cachedRooms != null) {
//            return cachedRooms; // Return cached data
//      }
//
//      // Fetch from database if not in cache
//      List<Rooms> rooms = rr.findByHotelId(hotelId);
//      roomCache.put(cacheKey, rooms); // Update the cache
//
//      return rooms;
//}