package com.HavenHub.hotel_service.service;

import com.HavenHub.hotel_service.DTO.HotelDTO;
import com.HavenHub.hotel_service.entity.Hotel;
import com.HavenHub.hotel_service.repository.HotelRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class HotelService {

      @Autowired
      HotelRepo hr;


      public String addHotel(HotelDTO hotel) {
            Hotel h=new Hotel(hotel.getName(), hotel.getRatings(),hotel.getAddress(),
                    hotel.getFeatures(), hotel.getCity(),
                    hotel.getHotel_photo(),hotel.getMobile(),hotel.getLocation(),hotel.getPrice());
            hr.save(h);
            return h.getName();
      }

      public List<Hotel> getHotels() {
            return hr.findAll();
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
