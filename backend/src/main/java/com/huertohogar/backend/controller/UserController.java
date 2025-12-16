package com.huertohogar.backend.controller;

import com.huertohogar.backend.model.User;
import com.huertohogar.backend.service.UserService;
import com.huertohogar.backend.utils.JwtUtil;
import com.huertohogar.backend.dto.PasswordChangeRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtUtil jwtUtil;

    // ================== LISTAR USUARIOS ==================
    @GetMapping
    public List<User> list() {
        return service.getAll();
    }

    // ================== REGISTRO ==================
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return service.register(user);
    }

    // ================== LOGIN (CORREGIDO) ==================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User credentials) {

        User user = service.login(
                credentials.getCorreo(),
                credentials.getContrasena()
        );

        // ❌ Credenciales incorrectas → 401
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas"));
        }

        // ✅ Credenciales correctas
        String token = jwtUtil.generateToken(
                user.getCorreo(),
                user.getRol()
        );

        user.setContrasena(null); // nunca devolver password

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", user);

        return ResponseEntity.ok(response);
    }

    // ================== ACTUALIZAR PERFIL ==================
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return service.update(id, user);
    }

    // ================== ELIMINAR CUENTA ==================
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ================== CAMBIO DE CONTRASEÑA ==================
    @PutMapping("/{id}/password")
    public ResponseEntity<Map<String, String>> cambiarContrasena(
            @PathVariable Long id,
            @RequestBody PasswordChangeRequest request
    ) {

        boolean ok = service.cambiarContrasena(
                id,
                request.getPasswordActual(),
                request.getPasswordNueva()
        );

        Map<String, String> response = new HashMap<>();

        if (ok) {
            response.put("mensaje", "Contraseña actualizada correctamente");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensaje", "Contraseña actual incorrecta");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
}
