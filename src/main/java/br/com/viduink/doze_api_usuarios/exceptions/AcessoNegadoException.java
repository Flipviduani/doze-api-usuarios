package br.com.viduink.doze_api_usuarios.exceptions;

public class AcessoNegadoException extends RuntimeException {

  //Metodo para retornar mensagem de erro quando usuário não for autenticado:
  @Override
  public String getMessage() {
    return "Acesso negado.";
  }
}