package br.com.viduink.doze_api_usuarios.controllers;

import br.com.viduink.doze_api_usuarios.components.JwtComponent;
import br.com.viduink.doze_api_usuarios.dtos.AutenticarUsuarioRequest;
import br.com.viduink.doze_api_usuarios.dtos.CriarUsuarioRequest;
import br.com.viduink.doze_api_usuarios.entities.Usuario;
import br.com.viduink.doze_api_usuarios.exceptions.AcessoNegadoException;
import br.com.viduink.doze_api_usuarios.exceptions.EmailJaCadastradoException;
import br.com.viduink.doze_api_usuarios.exceptions.SenhaInvalidaException;
import br.com.viduink.doze_api_usuarios.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtComponent jwtComponent;

    @PostMapping("autenticar")
    public ResponseEntity<?> autenticar(@RequestBody AutenticarUsuarioRequest request) {
        try {
            var response = usuarioService.autenticarUsuario(request);
            //HTTP 200 (OK)
            return ResponseEntity.ok(response);

        } catch (AcessoNegadoException e) {
            //HTTP 401 (UNAUTHORIZED)
            return ResponseEntity.status(401).body(e.getMessage());

        } catch (Exception e) {
            //HTTP 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("obter-dados")
    public ResponseEntity<?> obterDados(HttpServletRequest http) {
        try {
            var email = jwtComponent.getEmailUsuario(http);

            var response = usuarioService.obterDadosUsuario(email);

            return ResponseEntity.status(200).body(response); //Provisório

        } catch (Exception e) {
            //HTTP 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @PostMapping("criar")
    public ResponseEntity<?> criar(@RequestBody CriarUsuarioRequest request) {
        try {
            var response = usuarioService.criarUsuario(request);

            //HTTP 201 (CREATED)
            return ResponseEntity.status(201).body(response);

        } catch (SenhaInvalidaException e) {
            //HTTP 400 (BAD REQUEST)
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (EmailJaCadastradoException e) {

            //HTTP 409 (CONFLICT)
            return ResponseEntity.status(409).body(e.getMessage());

        } catch (Exception e) {
            //HTTP 500 (INTERNAL SERVER ERROR)
            return ResponseEntity.status(500).body(e.getMessage());
        }

    }
}
