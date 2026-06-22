package br.com.viduink.doze_api_usuarios.configurations;

import br.com.viduink.doze_api_usuarios.filters.AuthenticationFilter;
import jakarta.servlet.FilterRegistration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfiguration {

    @Value("${jwt.secret}")
    private String jwtSecret;

    //Metodo para configurar o filter de autenticação

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> RegistrationFilter() {

        //Registrar o filter de autenticação
        var filter = new FilterRegistrationBean<AuthenticationFilter>();
        filter.setFilter(new AuthenticationFilter(jwtSecret));

        //Aplicando o filtro para os endpoints desejados
        filter.addUrlPatterns("/api/v1/usuario/obter-dados");

        return filter;

    }
}
