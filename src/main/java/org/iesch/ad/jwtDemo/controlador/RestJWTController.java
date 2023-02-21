package org.iesch.ad.jwtDemo.controlador;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.iesch.ad.jwtDemo.modelo.AuthenticationReq;
import org.iesch.ad.jwtDemo.modelo.TokenInfo;
import org.iesch.ad.jwtDemo.servicio.JwtService;
import org.iesch.ad.jwtDemo.servicio.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class RestJWTController {

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDetailService userDetailService;

    @GetMapping("/publico/genera")
    public ResponseEntity<?> genera(){
        String jwt = jwtService.creaJWT();

        Map<String, String> contenido = new HashMap<>();
        contenido.put("jwt", jwt);

        return ResponseEntity.ok(contenido);
    }

    @GetMapping("/publico/comprueba")
    public ResponseEntity<?> comprueba(@RequestParam String jwt){
        Jws nuestroJwt = jwtService.parseJwt(jwt);
        return ResponseEntity.ok(nuestroJwt);
    }

    @GetMapping("/admin")
    public ResponseEntity<?> getMensajeAdmin(){
        //Esto nos mostrará la autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Datos del usuario: {}", auth.getPrincipal());
        log.info("Datos de los permisos: {}", auth.getAuthorities());
        log.info("Esta autenticado: {}", auth.isAuthenticated());
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("Contenido", "Mensaje que solo puede ver el administrador");
        return ResponseEntity.ok(mensaje);
    }
    //Endpoint para usar user y passwd
    //Se usa post porque hay que añadir usuarios y contraseñas
    @PostMapping("/publico/authenticate")
    public ResponseEntity<?> authenticate (@RequestBody AuthenticationReq authenticationReq){
        log.info("Autenticando al usuario{} ", authenticationReq.getUsuario());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationReq.getUsuario(), authenticationReq.getClave()));
        final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationReq.getUsuario());

        final String jwt = JwtService.generateToken(userDetails);

        log.info(userDetails.toString());
        TokenInfo tokenInfo = new TokenInfo(jwt);
        return ResponseEntity.ok(tokenInfo);

    }
}
