package com.example.DailyPractise.handlers;

import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.services.implementations.JwtService;
import com.example.DailyPractise.services.implementations.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        if (email == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Email not found in OAuth2 response");
            return;
        }

        UserEntity user = userService.findUserByEmail(email)
                .orElseGet(() -> userService.saveUser(UserEntity.builder()
                        .username(oAuth2User.getAttribute("name"))
                        .email(email)
                        .build()));

        System.out.println("User Email: " + email + ("email"));



        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);

        response.addCookie(refreshTokenCookie);

        String frontEndUrl = "http://localhost:8080/home.html?token=" + accessToken;
        getRedirectStrategy().sendRedirect(request, response, frontEndUrl);
    }
}
