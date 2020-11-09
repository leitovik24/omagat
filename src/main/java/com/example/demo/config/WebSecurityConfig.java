package com.example.demo.config;

import com.example.demo.config.handler.LoginSuccessHandler;
import com.example.demo.oauth2.CustomOAuth2UserService;
import com.example.demo.oauth2.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfig(@Qualifier("userDetailsImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .successHandler(new LoginSuccessHandler())
                    .loginProcessingUrl("/login")
                    .usernameParameter("j_username")
                    .passwordParameter("j_password")
                    .permitAll()
                .and()
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                    .userService(oAuth2UserService)
                    .and()
                    .successHandler(oAuth2LoginSuccessHandler)
                .and();

//        http.authorizeRequests()
//                .antMatchers("/oauth2/**").permitAll()
//                .antMatchers("/login").anonymous()
//                .antMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
//                .antMatchers("/user/**").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .and();
//
//
//        http
//                .formLogin()
//                .loginPage("/login")
//                .successHandler(new LoginSuccessHandler())
//                .loginProcessingUrl("/login")
//                .usernameParameter("j_username")
//                .passwordParameter("j_password")
//                .permitAll();
//




        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .and().csrf().disable();
    }

    @Autowired
    private CustomOAuth2UserService oAuth2UserService;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
}
