package com.semillerogtc.gtcusermanagament.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Builder
@Data
public class UsuarioNuevoDto {
    @NotEmpty(message = "Nombre es obligatorio")
    public String nombre;
    @NotEmpty(message = "Email es obligatorio")
    public String email;
    @NotEmpty(message = "La contraseña es obligatoria")
    public String password;
    public int edad;
    public List<String> telefonos;
}
