package com.ilhak.musicstudio.config;

import javax.sql.DataSource;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/account/register", "/css/**","/lib/**", "/js/**", "/board/list", "/board/view/**",
                        "/api/boards/**", "/api/board/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/account/login").permitAll()
                .usernameParameter("email")
                .and()
            .logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .dataSource(dataSource)
        .passwordEncoder(passwordEncoder())
        .usersByUsernameQuery("select email,password,enabled "
            + "from user "
            + "where email = ?")
        .authoritiesByUsernameQuery("select u.email, r.name "
            + "from user_role ur inner join user u on u.id = ur.user_id "
            + "inner join role r on ur.role_id = r.id "
            + "where u.email = ?");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
