package br.com.viduink.doze_api_usuarios.dtos;

public record CriarUsuarioResponse(
        String mensagem,
        String nomeUsuario,
        String emailUsuario,
        String perfilUsuario
) {
}
