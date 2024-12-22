package com.HavenHub.booking_service.service;

import com.HavenHub.booking_service.DTO.BookingDTO;
import com.HavenHub.booking_service.entity.Booking;
import com.HavenHub.booking_service.repository.BookingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

      @Autowired
      BookingRepo br;



      public String addBooking(BookingDTO booking) {
            Booking book=new Booking(booking.getUser_id(),booking.getHotel_id()
                    ,booking.getSingle_room(), booking.getDouble_room(),booking.getBookingDate(),
                    booking.getCheckInDate(),booking.getCheckOutDate(),"booked",
                    booking.getTotalAmount(),booking.getPayments());
            br.save(book);
            return "Successfully Booked";
      }

      public List<Booking> bookedRoomsByUser(int userId) {
            return br.findByUserId(userId);
      }

      public String update(int bookingId) {
            Booking b=br.findByBooking_id(bookingId);
            b.setStatus("cancelled");
            br.save(b);
            return "Successfully Cancelled";
      }

      public List<Booking> getAllRoomsHotel(int hotelId, LocalDate date) {
            return br.findByHotel_idAndCheckInDate(hotelId,date);
      }
}
