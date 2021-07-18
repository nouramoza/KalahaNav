//package com.game.kalaha.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableAutoConfiguration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig  extends WebSecurityConfigurerAdapter {
//    @Autowired
//    public void configureGlobal(AuthenticationManager authenticationManager) {
//
//    }
//
//                                @Override
//    public void configure(WebSecurity http) throws Exception {
////        http
////                .ignoring()
////                .antMatchers("/api**")
////                ;
////                .anyRequest().authenticated()
////                .and()
////                .headers()
////                .xssProtection()
////                .and()
////                .contentSecurityPolicy("script-src 'self'");
//
//    }
//}
