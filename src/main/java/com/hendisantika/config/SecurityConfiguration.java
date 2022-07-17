package com.hendisantika.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/18/22
 * Time: 05:36
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**/favicon.ico", "/css/**", "/js/**", "/images/**", "/webjars/**", "/login", "/register").permitAll()
                .antMatchers("/movies/list").access("hasRole('USER')")
                .antMatchers("/movies/edit/**", "/movies/cast", "/movies/new/**", "/movies/update/**", "/movies/delete/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource);
    }
}
