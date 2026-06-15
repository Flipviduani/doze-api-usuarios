package br.com.viduink.doze_api_usuarios.repositories;

import br.com.viduink.doze_api_usuarios.factories.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepository {

    @Autowired
    private ConnectionFactory connectionFactory;
}
