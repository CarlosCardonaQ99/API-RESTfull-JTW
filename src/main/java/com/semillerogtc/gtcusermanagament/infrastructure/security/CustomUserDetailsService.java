package com.semillerogtc.gtcusermanagament.infrastructure.security;

import com.semillerogtc.gtcusermanagament.domain.Rol;
import com.semillerogtc.gtcusermanagament.domain.RolesRepositorio;
import com.semillerogtc.gtcusermanagament.domain.Usuario;
import com.semillerogtc.gtcusermanagament.domain.UsuariosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RolesRepositorio rolesRepositorio;

    private UsuariosRepositorio usuariosRepositorio;

    public CustomUserDetailsService(UsuariosRepositorio usuariosRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = usuariosRepositorio.findByName(name).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado con este nombre: " + name));
        Set<Rol> roles = Set.of(usuario.getRol());
        return new User(usuario.getName(), usuario.getPassword(), mapRoles(roles));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Set<Rol> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }
}
