package com.HavenHub.NotificationService.controller;

import com.HavenHub.NotificationService.configuration.Feign.UserInterface;
import com.HavenHub.NotificationService.entity.Booking;
import com.HavenHub.NotificationService.entity.HotelUser;
import com.HavenHub.NotificationService.entity.NotificationRequest;
import com.HavenHub.NotificationService.service.EmailService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/notification")
public class NotificationController {

      @Autowired
      private EmailService emailService;
      @Autowired
      private UserInterface ur;

      @Autowired
      private ThreadPoolTaskScheduler taskScheduler;



      private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);


      // Twilio credentials from your dashboard
      public static final String ACCOUNT_SID = "AC340bbd34c29132b914e8f013f3037da4"; // Replace with your Account SID
      public static final String AUTH_TOKEN = "00816ed146415ab190259f5d2f2e40ad";   // Replace with your Auth Token


      @PostMapping("/register")
      public void save(@RequestBody HotelUser user) {
            logger.info("User being sent to Notification Service: {}", user);
            if ( user.getEmail() == null || user.getEmail() .isEmpty()) {
                  logger.error("Invalid user or email is null.");
                  throw new IllegalArgumentException("Email address cannot be null or empty.");
            }

            // Customized welcome email
            String emailSubject = "Welcome to HavenHub, " + user.getName() + "!";

            String emailBody = String.format(
                    "Dear %s,\n\n"
                            + "🎉 We’re absolutely delighted to welcome you to HavenHub – your ultimate destination for hassle-free hotel bookings! 🏨✨\n\n"
                            + "🌟 Explore the best deals, manage your bookings with ease, and enjoy a seamless experience tailored just for you. 🛏️📅\n\n"
                            + "💬 If you have any questions or need assistance, our team is here to help anytime! You can reach us at 📧 havenhub657@gmail.com.\n\n"
                            + "🚀 We’re thrilled to have you onboard and can’t wait to make your travel experiences unforgettable. ✈️🌍\n\n"
                            + "Warm regards, 💖\n"
                            + "The HavenHub Team 🌐\n\n"
                            + "P.S. 🎁 Don’t forget to check out our exclusive offers for new users!"
                    , user.getName()
            );


            // Send email
            emailService.sendSimpleMessage(user.getEmail(), emailSubject, emailBody);

            // Customized welcome SMS
            String smsMessage = String.format(
                    "Hello %s! 🌟 Welcome to HavenHub 🎉.\n"
                            + "We’re absolutely delighted to have you with us! 🏨✨ Start exploring amazing hotel deals today.\n"
                            + "For assistance, reach out to our support team anytime. 💬📞 Happy booking! 🚀\n"
                            + "- The HavenHub Team ❤️",
                    user.getName()
            );


            // Initialize Twilio and send SMS
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                    new PhoneNumber("+919080513846"), // Recipient's phone number
                    new PhoneNumber("+12315887906"),   // Your Twilio phone number
                    smsMessage
            ).create();

            logger.info("Welcome SMS sent to {} with SID: {}", user.getMobile(), message.getSid());



      }
      @PostMapping(path = "/booking")
      public void booking(@RequestBody NotificationRequest notificationRequest) {
            HotelUser user=notificationRequest.getUser();
            Booking booking=notificationRequest.getBooking();
            if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
                  logger.error("Invalid user or email is null.");
                  throw new IllegalArgumentException("Email address cannot be null or empty.");
            }

            // Customized booking confirmation email
            String emailSubject = "Booking Confirmation - HavenHub";
            String emailBody = String.format(
                    "Dear %s,\n\n"
                            + "🎉 Thank you for choosing HavenHub! 🏨 Your hotel booking has been successfully confirmed ✅.\n\n"
                            + "We’re excited to help make your travel experience seamless and unforgettable. ✈️✨ Start planning your stay, and if you need assistance, feel free to reach out at 📧 havenhub657@gmail.com.\n\n"
                            + "🌟 Wishing you a fantastic stay ahead!\n\n"
                            + "Warm regards, 💖\n"
                            + "The HavenHub Team 🌐",
                    user.getName()
            );


            emailService.sendSimpleMessage(user.getEmail(), emailSubject, emailBody);
            logger.info("Booking confirmation email sent to {}", user.getEmail());

            // Customized booking confirmation SMS
            String smsMessage = String.format(
                    "Hi %s! 🎉 Your hotel booking has been confirmed. Thank you for choosing HavenHub. Have a great stay!",
                    user.getName()
            );

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                    new PhoneNumber("+919080513846"), // Recipient's phone number
                    new PhoneNumber("+12315887906"),   // Your Twilio phone number
                    smsMessage
            ).create();

            logger.info("Booking confirmation SMS sent to {} with SID: {}", user.getMobile(), message.getSid());
      }


      @PostMapping(path = "/cancel")
      public void cancel(@RequestBody HotelUser user) {

            if (user == null || user.getEmail() == null || user.getEmail().isEmpty()) {
                  logger.error("Invalid user or email is null.");
                  throw new IllegalArgumentException("Email address cannot be null or empty.");
            }

            // Schedule email for tomorrow at 9 AM
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime tomorrow9AM = now.toLocalDate().plusDays(1).atTime(LocalTime.of(9, 0));
            Duration delay = Duration.between(now, tomorrow9AM);

            taskScheduler.schedule(() -> {
                  try {
                        // Tempting re-engagement email
                        String emailSubject = "We'd Love to Have You Back at HavenHub!";

                                String emailBody = String.format(
                                        "Dear %s,\n\n"
                                                + "👋 We noticed you canceled your booking recently. While we’re sad to see you go, we’d love to help you find the perfect option for your stay! 🏨❤️\n\n"
                                                + "As a valued customer, we’re offering exclusive deals just for you 🎁. Explore new destinations and make the most of your travel plans. ✈️🌍\n\n"
                                                + "👉 Click here to check out our latest deals: [Link to offers]\n\n"
                                                + "Looking forward to welcoming you back! 😊\n\n"
                                                + "Warm regards, 💖\n"
                                                + "The HavenHub Team 🌟",
                                        user.getName()
                                );


                        emailService.sendSimpleMessage(user.getEmail(), emailSubject, emailBody);
                        logger.info("Re-engagement email sent to {}", user.getEmail());

                  } catch (Exception e) {
                        logger.error("Failed to send re-engagement email: {}", e.getMessage());
                  }
            }, Instant.now().plus(delay));

            logger.info("Re-engagement email scheduled for {} at 9 AM", tomorrow9AM);
      }

}