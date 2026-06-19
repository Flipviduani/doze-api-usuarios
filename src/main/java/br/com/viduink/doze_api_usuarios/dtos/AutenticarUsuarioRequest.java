package br.com.viduink.doze_api_usuarios.dtos;

public record AutenticarUsuarioRequest(
        String email,
        String senha
) {
}
