package com.semillerogtc.gtcusermanagament.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepositorio extends JpaRepository<Rol, String> {
     Optional<Rol> findByNombre(String nombre);
}
