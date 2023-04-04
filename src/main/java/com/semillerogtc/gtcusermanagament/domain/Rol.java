package com.semillerogtc.gtcusermanagament.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "rol")
public class Rol {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name =  "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @Column(length = 50)
    private String nombre;
}
