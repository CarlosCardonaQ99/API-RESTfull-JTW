package com.semillerogtc.gtcusermanagament.aplication.services;


import com.semillerogtc.gtcusermanagament.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UsersService {

    UsuariosRepositorio usuariosRepositorio;

    RolesRepositorio rolesRepositorio;

   /* @Autowired
    PasswordEncoder passwordEncoder;
*/
    UsersService(
            UsuariosRepositorio usuariosRepositorio,
            RolesRepositorio rolesRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
        this.rolesRepositorio = rolesRepositorio;

    }

    public Usuario registrarUsuario(UsuarioNuevoDto usuarioNuevoDto) {

      /* if (usuariosRepositorio.existsByEmail(usuarioNuevoDto.email)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El correo ya está registrado");
        }*/
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
        Rol rol = rolesRepositorio.findByNombre("ROLE_ADMIN").orElseThrow(
                () -> new UsernameNotFoundException("Rol no encontrado con este nombre: " + "ROLE_ADMIN"));
        rol.setNombre("ROLE_ADMIN");

        usuarioNuevo.setRol(rol);

        var userRegistrado = this.usuariosRepositorio.save(usuarioNuevo);
        return userRegistrado;
    }


    public List<Usuario> consultarTodosLosUsuarios() {
        return this.usuariosRepositorio.findAll();
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

    public void elinminarUsuario(String userId) {
        this.usuariosRepositorio.deleteById(userId);
    }
}