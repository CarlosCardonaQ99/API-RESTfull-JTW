package com.semillerogtc.gtcusermanagament.app;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("carlos".equals(username)) {
            String encodedPassword = passwordEncoder.encode("admin");
            return User.builder()
                    .username("carlos")
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }

    public boolean authenticate(String username, String password) {
        if ("carlos".equals(username) && "admin".equals(password)) {
            return true;
        } else {
            return false;
        }
    }
}


   /* @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Usuario usuario = usuariosRepositorio.findByName(name).orElseThrow(
                () -> new UsernameNotFoundException("Usuario no encontrado con este nombre: " + name));
        Set<Rol> roles = Set.of(usuario.getRol());
        return new User(usuario.getName(), usuario.getPassword(), mapRoles(roles));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Set<Rol> roles) {
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());
    }
    */
  /*  private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
        return roles.stream()
                .map(rol -> new SimpleGrantedAuthority(rol.getNombre()))
                .collect(Collectors.toList());
    }*/

