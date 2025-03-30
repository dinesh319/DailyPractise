package com.example.DailyPractise.auth;

import com.example.DailyPractise.entities.UserEntity;
import com.example.DailyPractise.services.imp.JwtService;
import com.example.DailyPractise.services.imp.UserService;
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
public class CustomSecurityFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            try{
                String tokenWithHeader = request.getHeader("Authorization");
                if(tokenWithHeader == null || !tokenWithHeader.startsWith("Bearer ")){
                    filterChain.doFilter(request,response);
                    return;
                }

                String token = tokenWithHeader.split("Bearer ")[1];

                Long userId = jwtService.getIdByToken(token);

                if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserEntity user = userService.findById(userId);
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                    authenticationToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

                filterChain.doFilter(request,response);

            } catch (Exception e) {
                handlerExceptionResolver.resolveException(request,response,null,e);
            }
    }
}
