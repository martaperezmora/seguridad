package com.llv.exament4.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.llv.exament4.models.Permission;
import com.llv.exament4.models.User;
import com.llv.exament4.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<GrantedAuthority> permisosAut = new ArrayList<GrantedAuthority>();
        User usuarioEncontrado = userRepository.findByName(username);
        List<Permission> permisos = usuarioEncontrado.getPermissions();

        for (Permission permiso : permisos) {
            permisosAut.add(new SimpleGrantedAuthority(permiso.getName()));
        }

        UserDetails user = org.springframework.security.core.userdetails.User.builder()
                .username(usuarioEncontrado.getName())
                .password(usuarioEncontrado.getPassword())
                .authorities(permisosAut)
                .build();

        return user;
    }
}
