package com.example.demo.config.handler;

import com.example.demo.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        boolean isAdmin = user.getRoles()
                .stream()
                .anyMatch(x -> x.getRole()
                        .contains("ROLE_ADMIN"));
        String redirect = isAdmin ? "/admin" : "/user";
        response.sendRedirect(redirect);
    }
}
