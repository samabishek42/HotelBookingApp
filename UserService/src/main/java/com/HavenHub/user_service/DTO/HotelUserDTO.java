package com.HavenHub.user_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelUserDTO {

      private String name;
      private String email;
      private String password;
      private String mobile;
      private String type;
      private String photo;


}
