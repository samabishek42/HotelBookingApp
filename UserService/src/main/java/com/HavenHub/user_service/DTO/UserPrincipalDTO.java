package com.HavenHub.user_service.DTO;

import com.HavenHub.user_service.entity.HotelUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;


public class UserPrincipalDTO implements UserDetails {

      private HotelUser user;

      public UserPrincipalDTO(HotelUser user) {
            this.user=user;
      }


      @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
            return Collections.singleton(new SimpleGrantedAuthority(user.getType()));
      }

      @Override
      public String getPassword() {
            return user.getPassword();
      }



      @Override
      public String getUsername() {
            return user.getName();
      }

      @Override
      public boolean isAccountNonExpired() {
            return true;
      }

      @Override
      public boolean isAccountNonLocked() {
            return true;
      }

      @Override
      public boolean isCredentialsNonExpired() {
            return true;
      }

      @Override
      public boolean isEnabled() {
            return true;
      }
}
