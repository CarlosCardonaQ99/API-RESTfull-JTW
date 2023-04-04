package com.semillerogtc.gtcusermanagament.infrastructure.controllers;

import com.semillerogtc.gtcusermanagament.aplication.services.UsersService;
import com.semillerogtc.gtcusermanagament.domain.UsuarioNuevoDto;
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

@RestController
@RequestMapping("/usuarios")
public class UsersController {
    @Autowired
    UsersService _user;

    EnvironmentService _environmentService1;
    // JWtManagerService jWtManagerService;

    public final Logger logger = LoggerFactory.getLogger(UsersController.class);

    /* UsersController(JWtManagerService jWtManagerService) {
         this.jWtManagerService = jWtManagerService;
     }
 */
    @GetMapping("/ping")

    public String ping(
            @RequestHeader("Environment") String token
    ) {

        logger.info(token);
        return "Hola desde controlador usuarios";
    }

    @GetMapping("/{email}")
    public ResponseEntity consultarUsuarioPorEmail(@PathVariable("email") String email) {
        var usuario = _user.consultarUsuarioXEmail(email);
        return new ResponseEntity(usuario, HttpStatus.OK);
    }

    @GetMapping("/headers")
    public ResponseEntity consultarUsuarioPorHeader(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestHeader("") String userId) {
        logger.info(token + "- " + userId);
        UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
        return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
    }

    @GetMapping("/query")
    public ResponseEntity consultarUsuarioPorQueryString(@RequestParam String email) {
        logger.info(email);
        UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
        return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
    }

    @GetMapping("/uritemplate/{email}/{id}")
    public ResponseEntity consultarUsuarioPorPathOUriTemplate(@PathVariable("email") String email, @PathVariable("id") String userId) {
        logger.info(email + "- " + userId);
        UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
        return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/v1")
    public ResponseEntity registrarUsuario(@Valid @RequestBody UsuarioNuevoDto usuarioDto) {
        logger.info(usuarioDto.email);
        var usuarioRegistrado = _user.registrarUsuario(usuarioDto);

        return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
    }

    @PostMapping("/v2")
    public ResponseEntity registrarUsuario2(@Valid @RequestBody UsuarioNuevoDto usuarioDto) throws Exception {
        var usuarioRegistrado = _user.registrarUsuario(usuarioDto);

        return new ResponseEntity(usuarioRegistrado, HttpStatus.CREATED);
    }

 /*   @PostMapping("/login")
    public ResponseEntity login() {
        return new ResponseEntity(this.jWtManagerService.generate(), HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity auth(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        try {
            var jwt = this.jWtManagerService.validate(token);
            return new ResponseEntity(jwt.toString(), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity("Token expirado o no válido", HttpStatus.UNAUTHORIZED);
        }
    }*/

    /*   @PatchMapping("/{id}")
       public ResponseEntity actualizarUsuario(@RequestBody UsuarioNuevoDto usuarioDto) {
           UsuarioNuevoDto user = UsuarioNuevoDto.builder().email("Jeffrey").build();
           return new ResponseEntity(_user.registrarUsuario(user), HttpStatus.OK);
       }*/
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