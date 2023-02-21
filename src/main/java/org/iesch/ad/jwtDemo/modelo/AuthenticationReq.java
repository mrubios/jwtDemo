package org.iesch.ad.jwtDemo.modelo;

import lombok.Data;

@Data
public class AuthenticationReq {
    private String usuario;
    private String clave;
}
