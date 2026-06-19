package br.com.viduink.doze_api_usuarios.services;

import br.com.viduink.doze_api_usuarios.dtos.AutenticarUsuarioRequest;
import br.com.viduink.doze_api_usuarios.dtos.AutenticarUsuarioResponse;
import br.com.viduink.doze_api_usuarios.dtos.CriarUsuarioRequest;
import br.com.viduink.doze_api_usuarios.dtos.CriarUsuarioResponse;
import br.com.viduink.doze_api_usuarios.entities.Usuario;
import br.com.viduink.doze_api_usuarios.exceptions.AcessoNegadoException;
import br.com.viduink.doze_api_usuarios.exceptions.EmailJaCadastradoException;
import br.com.viduink.doze_api_usuarios.exceptions.SenhaInvalidaException;
import br.com.viduink.doze_api_usuarios.repositories.PerfilRepository;
import br.com.viduink.doze_api_usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired //inicialização automática
    private UsuarioRepository usuarioRepository;

    @Autowired //inicialização automática
    private PerfilRepository perfilRepository;

    //Metodo para implementar um fluxo de autenticação de usuário no sistema (login do usuário)
    public AutenticarUsuarioResponse autenticarUsuario(AutenticarUsuarioRequest request) throws Exception {

        //Buscar o usuário no banco de dados através do email:
        var usuario = usuarioRepository.obterPorEmail(request.email());

        //Verificar se o usuário foi encontrado e se a senha é igual ao valor enviado na requisição
        if (usuario != null && usuario.getSenha().equals(criptografarSenha(request.senha()))) {

            //Recuperar o perfil do usuário no banco de dados:
            var perfil = perfilRepository.obterPorId(usuario.getId());

            //Retornar os dados do usuário autenticado
            return new AutenticarUsuarioResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    perfil.getNome(),
                    LocalDateTime.now(),
                    "Seu token jwt será gerado aqui."
            );
        }

        throw new AcessoNegadoException();
    }

    //Metodo para implementar um fluxo de criação de usuário no sistema (novo usuário)
    public CriarUsuarioResponse criarUsuario(CriarUsuarioRequest request) throws Exception {

        //Condição de segurança para verificar se a senha é uma senha forte
        if (!validarSenhaForte(request.senha())) {
            throw new SenhaInvalidaException();
        }

        //Condição de segurança para verificar se o email já está cadastrado
        if (usuarioRepository.obterPorEmail(request.email()) != null) {
            throw new EmailJaCadastradoException();
        }

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

    //Metodo para verificar se a senha é forte:
    private boolean validarSenhaForte(String senha) throws Exception {
        if (senha == null) {
            throw new Exception("A senha não pode ser nula.");
        }

        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";

        return senha.matches(regex);
    }
}