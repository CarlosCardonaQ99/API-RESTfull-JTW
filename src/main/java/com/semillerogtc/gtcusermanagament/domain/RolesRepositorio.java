package com.semillerogtc.gtcusermanagament.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepositorio extends JpaRepository<Rol, String> {
     Optional<Rol> findByNombre(String nombre);
}
