package com.tp.udde.config;

import com.tp.udde.domain.dto.UserDto;
import com.tp.udde.domain.enums.UserType;
import com.tp.udde.filter.JWTAuthorizationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                //.antMatchers("/client/**").hasAuthority("CLIENT")
                .antMatchers("/**").hasAuthority("CLIENT")
                //.antMatchers("/tariff/**").hasRole(UserType.CLIENT.toString())
                //.antMatchers("/client/**").hasRole("CLIENT")
                //.antMatchers("/client/**").access("userType('CLIENT')")
                //.antMatchers(HttpMethod.POST, "/user/employee").permitAll()//metodos que se puede ejecutar sin token
                //.antMatchers(HttpMethod.POST, "/measurements").permitAll()
                //.antMatchers("/client/**").permitAll()
                .anyRequest().authenticated();
    }

   /* @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable()
                    //.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    //.antMatchers(HttpMethod.POST, "/login").permitAll()
                    //.antMatchers("/api/").access("userType('CLIENT')")
                    //.mvcMatchers(HttpMethod.POST, "/api/**").permitAll()
                    .antMatchers("/**").permitAll();
            //.anyRequest().permitAll();
            //.anyRequest().authenticated();
        }
    }*/

}