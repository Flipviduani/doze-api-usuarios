package br.com.viduink.doze_api_usuarios.dtos;

import java.time.LocalDateTime;

public record AutenticarUsuarioResponse(
        Integer Id,
        String nome,
        String email,
        String perfil,
        LocalDateTime dataHoraAcesso,
        String accessToken
) {
}
