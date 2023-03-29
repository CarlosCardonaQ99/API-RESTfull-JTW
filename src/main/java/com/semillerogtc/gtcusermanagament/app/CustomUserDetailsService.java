package com.semillerogtc.gtcusermanagament.app;

import com.semillerogtc.gtcusermanagament.domain.Email;
import com.semillerogtc.gtcusermanagament.domain.Rol;
import com.semillerogtc.gtcusermanagament.domain.Usuario;
import com.semillerogtc.gtcusermanagament.domain.UsuariosRepositorio;
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

    private UsuariosRepositorio usuariosRepositorio;

    public CustomUserDetailsService(UsuariosRepositorio usuariosRepositorio) {
        this.usuariosRepositorio = usuariosRepositorio;
    }

    //get.email - getValue?
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = usuariosRepositorio.findByName(name).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado con este nombre: " + name));
        return new User(usuario.getEmail().getValue(), usuario.getPassword(), mapearRoles(usuario.getRoles()));

    }
    private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());
    }
}
