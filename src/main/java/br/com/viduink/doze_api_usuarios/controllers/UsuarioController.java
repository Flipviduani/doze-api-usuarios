package br.com.viduink.doze_api_usuarios.controllers;

import br.com.viduink.doze_api_usuarios.dtos.CriarUsuarioRequest;
import br.com.viduink.doze_api_usuarios.entities.Usuario;
import br.com.viduink.doze_api_usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("criar")
    public ResponseEntity<?> criar(@RequestBody CriarUsuarioRequest request) {
        try {
            var response = usuarioService.criarUsuario(request);

            //HTTP 201 (CREATED)
            return ResponseEntity.status(201).body(response);

        } catch (Exception e) {
            //HTTP 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
}
