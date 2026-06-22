package br.com.viduink.doze_api_usuarios.components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtComponent {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    //Metodo para retornar a data de expiração do TOKEN

    public Date getExpiration() {
        var dataAtual = new Date(); //Caputando a data atual do sistema
        //Retornando uma nova data adicionando o tempo de expiração:
        return new Date(dataAtual.getTime() + Integer.parseInt(jwtExpiration));
    }

    //Metodo para gerar e retornar o TOKEN JWT:
    public String getAccessToken(String emailUsuario, String perfil) {
        return Jwts.builder()
                .setSubject(emailUsuario) //SUBJECT: Identificação do usuário
                .claim("perfil", perfil) //CLAIMS: Informações adicionais do usuário
                .setIssuedAt(new Date()) //Data e hora de geração do TOKEN
                .setExpiration(getExpiration()) //Data e hora de expiração do TOKEN
                .signWith(SignatureAlgorithm.HS256, jwtSecret) //Assinatura do TOKEN utilizando o algoritmo e a chave secreta
                .compact(); //Gerando o TOKEN
    }

    //Metodo para ler o email do usuário contido no TOKEN JWT

    public String getEmailUsuario(HttpServletRequest http) throws Exception {
        String authorization = http.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        String token = authorization.substring(7);

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
