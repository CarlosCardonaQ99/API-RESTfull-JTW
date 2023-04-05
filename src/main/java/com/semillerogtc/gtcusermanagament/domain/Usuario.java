package com.semillerogtc.gtcusermanagament.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Entity
@Table(name="usuarios", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Usuario {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotEmpty(message = "El nombre es obligatorio")
    private String name;
    @NotEmpty(message = "La contraseña es obligatoria")
    private String password;
    @Convert(converter = EmailAttributeConverter.class)
    @Column(nullable = false, unique = true)
    private Email email;
    private Integer edad;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    Set<UsuarioTelefono> telefonos;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "rol_id")
    private Rol rol;


}