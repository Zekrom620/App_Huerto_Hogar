package com.huertohogar.backend.service;

import com.huertohogar.backend.model.User;
import com.huertohogar.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User user = repo.findByCorreo(correo);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + correo);
        }
        return user;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User register(User user) {
        if (repo.findByCorreo(user.getCorreo()) != null) {
            return null;
        }

        String encodedPassword = passwordEncoder.encode(user.getContrasena());
        user.setContrasena(encodedPassword);
        user.setRol("cliente");

        return repo.save(user);
    }

    public User login(String correo, String contrasena) {
        User user = repo.findByCorreo(correo);
        if (user != null && passwordEncoder.matches(contrasena, user.getContrasena())) {
            return user;
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public User update(Long id, User userDetails) {
        User user = repo.findById(id).orElse(null);
        if (user == null) return null;

        user.setNombre(userDetails.getNombre());
        user.setRegion(userDetails.getRegion());
        user.setComuna(userDetails.getComuna());
        user.setRut(userDetails.getRut());
        user.setDireccion(userDetails.getDireccion());

        return repo.save(user);
    }

    // ===== NUEVO: CAMBIO DE CONTRASEÑA =====
    public boolean cambiarContrasena(Long id, String passwordActual, String passwordNueva) {

        User user = repo.findById(id).orElse(null);
        if (user == null) return false;

        // Validar contraseña actual
        if (!passwordEncoder.matches(passwordActual, user.getContrasena())) {
            return false;
        }

        // Guardar nueva contraseña encriptada
        user.setContrasena(passwordEncoder.encode(passwordNueva));
        repo.save(user);

        return true;
    }
}
