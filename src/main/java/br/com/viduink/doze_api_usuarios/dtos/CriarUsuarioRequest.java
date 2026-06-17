package br.com.viduink.doze_api_usuarios.dtos;

public record CriarUsuarioRequest(
        String nome,    //Nome do usuario
        String email,   //Email do usuario
        String senha    //Senha do usuario
) {
}
