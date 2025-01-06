package com.HavenHub.hotel_service.service;

import com.HavenHub.hotel_service.DTO.HotelDTO;
import com.HavenHub.hotel_service.DTO.SecondaryCache;
import com.HavenHub.hotel_service.entity.Hotel;
import com.HavenHub.hotel_service.repository.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HotelService {

      @Autowired
      HotelRepo hr;

      @Autowired
      private SecondaryCache<String, List<Hotel>> hotelCache; // Correctly typed cache


      public String addHotel(HotelDTO hotel) {
            Hotel h=new Hotel(hotel.getName(), hotel.getRatings(),hotel.getAddress(),
                    hotel.getFeatures(), hotel.getCity(),
                    hotel.getHotel_photo(),hotel.getMobile(),hotel.getLocation(),hotel.getPrice());
            hr.save(h);
            return h.getName();
      }


      public List<Hotel> getHotels() {
            String cacheKey = "hotels";

            // Check secondary cache
            List<Hotel> cachedHotels = hotelCache.get(cacheKey);
            if (cachedHotels != null) {
                  return cachedHotels; // Return cached data
            }

            // Fetch from database and update cache
            List<Hotel> hotels = hr.findAll();
            hotelCache.put(cacheKey, hotels);

            return hotels;
      }


      public Hotel getOnId(int id) {
            return hr.findById(id);
      }

      public List<Hotel> getOnCity(String city) {
            return hr.findByCity(city);
      }

      public String  delete(int id) {
            hr.deleteById(id);
            return "success";
      }
}
