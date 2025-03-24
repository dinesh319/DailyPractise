package com.example.DailyPractise.auth;

import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.services.JwtService;
import com.example.DailyPractise.services.UserService;
import com.example.DailyPractise.services.UserServiceImp;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomizedSecurityFilterChain extends OncePerRequestFilter {

    private  final JwtService jwtService;

    private final UserService userService;


    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try{

            final String requestTokenHeader = request.getHeader("Authorization");


            if(requestTokenHeader == null || ! requestTokenHeader.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];

            Long userId = jwtService.getUserIdByToken(token);

            if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){

                UserEntity user = userService.getUserById(userId);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user,null,null);

            /*
                Creates an Authentication object with:

                user (principal) → UserEntity (logged-in user).

                null (credentials) → No password required since authentication is via JWT.

                null (authorities) → No roles/permissions assigned here.

             */
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
            /*
                Enhances the authentication object with request metadata (e.g., IP address, session details).

             */

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request,response);
        } catch (Exception e) {
            handlerExceptionResolver.resolveException(request,response,null,e);
        }

    }
}
