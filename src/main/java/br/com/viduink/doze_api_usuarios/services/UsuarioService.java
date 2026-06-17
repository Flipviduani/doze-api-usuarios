package br.com.viduink.doze_api_usuarios.services;

import br.com.viduink.doze_api_usuarios.dtos.CriarUsuarioRequest;
import br.com.viduink.doze_api_usuarios.dtos.CriarUsuarioResponse;
import br.com.viduink.doze_api_usuarios.entities.Usuario;
import br.com.viduink.doze_api_usuarios.repositories.PerfilRepository;
import br.com.viduink.doze_api_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Service
public class UsuarioService {

    @Autowired //inicialização automática
    private UsuarioRepository usuarioRepository;

    @Autowired //inicialização automática
    private PerfilRepository perfilRepository;

    //Metodo para implementar um fluxo de criação de usuário no sistema (novo usuário)
    public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) throws Exception {

        //Capturar o nome e email do usuário:
        var usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());

        //Capturar e criptografar a senha do usuário:
        usuario.setSenha(criptografarSenha(request.senha()));

        //Buscar no banco de dados o registro do perfil 'Operador':
        var perfil = perfilRepository.obterPorNome("Operador");

        //Associar usuário ao perfil
        usuario.setPerfilId(perfil.getId());

        //Salvar no banco de dados
        usuarioRepository.inserir(usuario);

        //Retornando os dados da resposta
        return new CriarUsuarioResponse(
                "Usuário criado com sucesso!",
                usuario.getNome(),
                usuario.getEmail(),
                perfil.getNome()
        );
    }

    //Metodo para fazer a criptografia da senha do usuário:

    private String criptografarSenha(String senha) throws Exception {

        var digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(senha.getBytes(StandardCharsets.UTF_8));

        var hexString = new StringBuilder();

        for (byte b : hashBytes) {
            String hex = String.format("%02x", b);
            hexString.append(hex);
        }

        return hexString.toString();
    }
}