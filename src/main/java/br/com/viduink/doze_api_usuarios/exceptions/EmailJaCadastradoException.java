package br.com.viduink.doze_api_usuarios.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    //Implementando metodo para retornar a mensagem de erro padrão da classe de exceção

    @Override
    public String getMessage() {
        return "E-Mail informado já cadastrado. Favor escolher outro.";
    }
}
