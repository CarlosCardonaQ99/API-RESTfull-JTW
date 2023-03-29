package com.semillerogtc.gtcusermanagament.aplication.services;


import com.semillerogtc.gtcusermanagament.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsersService {

    UsuariosRepositorio usuariosRepositorio;


    UsersService(
            UsuariosRepositorio usuariosRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
    }

    public Usuario registrarUsuario(UsuarioNuevoDto usuarioNuevoDto) {
        var pass = this.generarPassword();
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setName(usuarioNuevoDto.nombre);
        usuarioNuevo.setEmail(new Email(usuarioNuevoDto.email));
        usuarioNuevo.setPassword(usuarioNuevoDto.password);
        usuarioNuevo.setEdad(usuarioNuevoDto.edad);

        UsuarioTelefono usuarioTelefono = new UsuarioTelefono();
        usuarioTelefono.setTelefono(usuarioNuevoDto.telefonos.get(0));
        Set<UsuarioTelefono> telefonosSet = new HashSet<UsuarioTelefono>();
        telefonosSet.add(usuarioTelefono);

        usuarioNuevo.setTelefonos(telefonosSet);

        var userRegistrado = this.usuariosRepositorio.save(usuarioNuevo);
        return userRegistrado;
    }

    public String generarPassword() {
        return "clavesegura";
    }

    public void elinminarUsuario(String userId) {
        this.usuariosRepositorio.deleteById(userId);
    }

    public Usuario consultarUsuarioXEmail(String email) {
        return this.usuariosRepositorio.findByEmail(new Email(email));
    }

    public void actualizarUsuario(String userId, UsuarioNuevoDto usuarioNuevoDto) {
        Usuario usuario1 = this.usuariosRepositorio.findById(userId).orElseThrow(
                () -> new IllegalStateException("Usuario no encotrado"));
        usuario1.setEmail(new Email(usuarioNuevoDto.email));
        usuario1.setPassword(usuarioNuevoDto.password);
        usuariosRepositorio.save(usuario1);
    }
}