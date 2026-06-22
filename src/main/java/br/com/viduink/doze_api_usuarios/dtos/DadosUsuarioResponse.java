package br.com.viduink.doze_api_usuarios.dtos;

public record DadosUsuarioResponse(
        Integer id,
        String nome,
        String email,
        String perfil
) {
}
