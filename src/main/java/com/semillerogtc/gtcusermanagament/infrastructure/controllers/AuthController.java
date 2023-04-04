package com.semillerogtc.gtcusermanagament.infrastructure.controllers;

import com.semillerogtc.gtcusermanagament.domain.*;
import com.semillerogtc.gtcusermanagament.infrastructure.security.JWTAuthResponseDTO;
import com.semillerogtc.gtcusermanagament.infrastructure.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private RolesRepositorio rolesRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponseDTO> autenticarUsuario(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get token from jwtTokenProvider
        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registarUsuario(@RequestBody UsuarioNuevoDto usuarioNuevoDto) {
        if(usuariosRepositorio.existsByName(usuarioNuevoDto.getNombre())){
            return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
        }


        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setName(usuarioNuevoDto.nombre);
        usuarioNuevo.setEmail(new Email(usuarioNuevoDto.email));
        usuarioNuevo.setPassword(passwordEncoder.encode(usuarioNuevoDto.getPassword()));
        usuarioNuevo.setEdad(usuarioNuevoDto.edad);

        UsuarioTelefono usuarioTelefono = new UsuarioTelefono();
        usuarioTelefono.setTelefono(usuarioNuevoDto.telefonos.get(0));
        Set<UsuarioTelefono> telefonosSet = new HashSet<UsuarioTelefono>();
        telefonosSet.add(usuarioTelefono);

        usuarioNuevo.setTelefonos(telefonosSet);

        Rol rol = rolesRepositorio.findByNombre("ROLE_ADMIN").orElseThrow(
                () -> new UsernameNotFoundException("Role no encontrado con este nombre: " + "ROLE_ADMIN"));
        rol.setNombre("ROLE_ADMIN");

        usuarioNuevo.setRol(rol);

        usuariosRepositorio.save(usuarioNuevo);
        return new ResponseEntity<>("El usuario ha sido registrado exitosamente",HttpStatus.OK);

    }


}
