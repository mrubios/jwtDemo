package org.iesch.ad.jwtDemo.servicio;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Creamos los usuarios
        Map<String, String> usuario = Map.of(
                "Michel", "USER",
                "Admin", "ADMIN"
        );
        String rol = usuario.get(username);
        if (rol != null){
            User.UserBuilder userBuilder = User.withUsername(username);
            String pass = "{noop}" + "1234";
            userBuilder.password(pass).roles(rol);
            return userBuilder.build();
        }else {
            throw new UsernameNotFoundException(username);
        }
    }
}
