package org.iesch.ad.jwtDemo.controlador;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.iesch.ad.jwtDemo.servicio.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class RestJWTController {

    @Autowired
    JwtService jwtService;

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
}
