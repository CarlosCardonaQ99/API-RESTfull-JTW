package com.semillerogtc.gtcusermanagament.infrastructure.controllers;

import com.semillerogtc.gtcusermanagament.aplication.services.UsersService;
import com.semillerogtc.gtcusermanagament.domain.RespuestaRegistroDto;
import com.semillerogtc.gtcusermanagament.domain.UsuarioNuevoDto;
import com.semillerogtc.gtcusermanagament.domain.UsuariosRepositorio;
import com.semillerogtc.gtcusermanagament.infrastructure.environment.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/usuarios")
public class UsersController {
    @Autowired
    UsersService _user;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    EnvironmentService _environmentService1;
    // JWtManagerService jWtManagerService;

    public final Logger logger = LoggerFactory.getLogger(UsersController.class);


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    public ResponseEntity consultarTodosLosUsuarios(){
        var usuarios = _user.consultarTodosLosUsuarios();
        return new ResponseEntity(usuarios, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity consultarUsuarioPorEmail(@PathVariable("email") String email) {
        var usuario = _user.consultarUsuarioXEmail(email);
        return new ResponseEntity(usuario, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/headers")
    public ResponseEntity consultarUsuarioPorHeader(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestHeader("") String userId) {
        logger.info(token + "- " + userId);
        UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
        return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/query")
    public ResponseEntity consultarUsuarioPorQueryString(@RequestParam String email) {
        logger.info(email);
        UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
        return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/uritemplate/{email}/{id}")
    public ResponseEntity consultarUsuarioPorPathOUriTemplate(@PathVariable("email") String email, @PathVariable("id") String userId) {
        logger.info(email + "- " + userId);
        UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
        return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/v1")
    public ResponseEntity registrarUsuario(@Valid @RequestBody UsuarioNuevoDto usuarioDto) {
        logger.info(usuarioDto.email);
        var usuarioRegistrado = _user.registrarUsuario(usuarioDto);
        RespuestaRegistroDto respuesta = new RespuestaRegistroDto(
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()


        );
        respuesta.setCreado(LocalDateTime.now());
        respuesta.setModificado(LocalDateTime.now());
        respuesta.setUltimoAcceso(LocalDateTime.now());


        return new ResponseEntity<>(respuesta , HttpStatus.CREATED);
    }

    @PostMapping("/v2")
    public ResponseEntity registrarUsuario2(@Valid @RequestBody UsuarioNuevoDto usuarioDto) throws Exception {
        var usuarioRegistrado = _user.registrarUsuario(usuarioDto);

        return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update/{userId}")
    public ResponseEntity actualizarUsuario(@RequestBody UsuarioNuevoDto usuarioNuevoDto,
                                            @PathVariable("userId") String userId) {
        try {
            _user.actualizarUsuario(userId, usuarioNuevoDto);
            return new ResponseEntity("usuario actualizado con éxito", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity eliminarUsuario(@PathVariable String id) {
        try {
            _user.elinminarUsuario(id);
            return new ResponseEntity("Usuario eliminado con éxito", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.NO_CONTENT);
        }
    }
}