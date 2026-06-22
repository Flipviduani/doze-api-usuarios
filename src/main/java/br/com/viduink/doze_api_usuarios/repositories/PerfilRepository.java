package br.com.viduink.doze_api_usuarios.repositories;

import br.com.viduink.doze_api_usuarios.entities.Perfil;
import br.com.viduink.doze_api_usuarios.factories.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PerfilRepository {

    @Autowired //Injeção de dependência
    private ConnectionFactory connectionFactory;

    public void inserir(Perfil perfil) throws Exception {
        try (var connection = connectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                    insert into perfis (nome) values (?)
                    """);
            statement.setString(1, perfil.getNome());
            statement.execute();
        }
    }

    public boolean atualizar(Perfil perfil) throws Exception {
        try (var connection = connectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                    update perfis set nome = ?
                    where id = ?
                    """);
            statement.setString(1, perfil.getNome());
            statement.setInt(2, perfil.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean excluir(Integer id) throws Exception {
        try (var connection = connectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                            delete from perfis
                            where id = ?
                    """);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    public List<Perfil> listar() throws Exception {
        try (var connection = connectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                            select id, nome from perfis
                            order by nome
                    """);
            var result = statement.executeQuery();
            var lista = new ArrayList<Perfil>();
            while (result.next()) {
                var perfil = new Perfil();
                perfil.setId(result.getInt("id"));
                perfil.setNome(result.getString("nome"));
                lista.add(perfil);
            }
            return lista;
        }
    }

    public Perfil obterPorId(Integer id) throws Exception {
        try (var connection = connectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                                select id, nome from perfis
                                where id = ?
                    """);
            statement.setInt(1, id);
            var result = statement.executeQuery();

            Perfil perfil = null;

            if (result.next()) {
                perfil = new Perfil();
                perfil.setId(result.getInt("id"));
                perfil.setNome(result.getString("nome"));
            }

            return perfil;
        }
    }

    public Perfil obterPorNome(String nome) throws Exception {
        try (var connection = connectionFactory.getConnection()) {
            var statement = connection.prepareStatement("""
                                select id, nome from perfis
                                where nome = ?
                    """);
            statement.setString(1, nome);
            var result = statement.executeQuery();

            Perfil perfil = null;

            if (result.next()) {
                perfil = new Perfil();
                perfil.setId(result.getInt("id"));
                perfil.setNome(result.getString("nome"));
            }
            return perfil;
        }
    }
}