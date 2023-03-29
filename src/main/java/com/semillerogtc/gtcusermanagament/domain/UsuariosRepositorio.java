package com.semillerogtc.gtcusermanagament.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByName(String name);

    Usuario findByEmail(Email email);
}
