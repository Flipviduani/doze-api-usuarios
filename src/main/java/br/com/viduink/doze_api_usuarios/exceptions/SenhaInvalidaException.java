package br.com.viduink.doze_api_usuarios.exceptions;

public class SenhaInvalidaException extends RuntimeException {

    //Implementar um metoro para retornar a mensagem de erro padrão desta classe de exceção

    @Override
    public String getMessage() {
        return "A senha deve conter pelo menos uma letra maiúscula, " +
                "minúscula, número, símbolo e 8 caracteres.";
    }
}
