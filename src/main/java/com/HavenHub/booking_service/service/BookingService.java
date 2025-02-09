//package com.HavenHub.booking_service.service;
//
//import com.HavenHub.booking_service.DTO.BookingDTO;
//import com.HavenHub.booking_service.entity.Booking;
//import com.HavenHub.booking_service.repository.BookingRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.time.LocalDate;
//import java.util.List;
//
//@Service
//public class BookingService {
//
//      @Autowired
//      BookingRepo br;
//
//      public String addBooking(BookingDTO booking) {
//            Booking book=new Booking(booking.getUser_id(),booking.getHotel_id()
//                    ,booking.getSingle_room(), booking.getDouble_room(),booking.getBookingDate(),
//                    booking.getCheckInDate(),booking.getCheckOutDate(),"booked",
//                    booking.getTotalAmount(),booking.getPayments());
//            br.save(book);
//            return "Successfully Booked";
//      }
//
//      public List<Booking> bookedRoomsByUser(int userId) {
//            return br.findByUserId(userId);
//      }
//
//      public String update(int bookingId) {
//            Booking b=br.findByBooking_id(bookingId);
//            b.setStatus("cancelled");
//            br.save(b);
//            return "Successfully Cancelled";
//      }
//
//      public List<Booking> getAllRoomsHotel(int hotelId, LocalDate date) {
//            return br.findByHotel_idAndCheckInDate(hotelId,date);
//      }
//}
package com.HavenHub.booking_service.service;

import com.HavenHub.booking_service.DTO.BookingDTO;
import com.HavenHub.booking_service.entity.Booking;
import com.HavenHub.booking_service.repository.BookingRepo;
import com.HavenHub.booking_service.DTO.SecondaryCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

      @Autowired
      BookingRepo br;

      @Autowired
      private SecondaryCache<String, List<Booking>> bookingCache; // Cache for bookings

      @Autowired
      KafkaTemplate<String,Object> template;

      public Booking addBooking(BookingDTO booking) {
            Booking book = new Booking(booking.getUser_id(), booking.getHotel_id(),
                    booking.getSingle_room(), booking.getDouble_room(), booking.getBookingDate(),
                    booking.getCheckInDate(), booking.getCheckOutDate(), "booked",
                    booking.getTotalAmount(), booking.getPayments());
            br.save(book);
//            invalidateBookingCache(booking.getUser_id());  // Invalidate cache after adding booking
            return book;
      }

      public List<Booking> bookedRoomsByUser(int userId) {
//            String cacheKey = "user-bookings-" + userId;
//
//            // Check secondary cache for bookings
//            List<Booking> cachedBookings = bookingCache.get(cacheKey);
//            if (cachedBookings != null) {
//                  return cachedBookings; // Return cached data
//            }

            // Fetch from database if not in cache
            List<Booking> bookings = br.findByUserId(userId);
//            bookingCache.put(cacheKey, bookings); // Update the cache

            return bookings;
      }

      public Booking update(int bookingId) {
            Booking b = br.findByBooking_id(bookingId);
            b.setStatus("cancelled");
            br.save(b);
//            invalidateBookingCache(b.getUser_id());  // Invalidate cache after canceling booking
            return b;
      }

//      private void invalidateBookingCache(int userId) {
//            // Invalidate the cache for bookings of this specific user
//            String cacheKey = "user-bookings-" + userId;
//            bookingCache.invalidate(cacheKey);
//      }
//
      public List<Booking> getAllRoomsHotel(int hotelId, LocalDate date) {
            return br.findByHotel_idAndCheckInDate(hotelId,date);
      }
}
