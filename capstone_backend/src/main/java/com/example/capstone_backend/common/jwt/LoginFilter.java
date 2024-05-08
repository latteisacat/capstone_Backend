package com.example.capstone_backend.common.jwt;

import com.example.capstone_backend.domain.account.dto.request.LoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper om;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String name = null;
        String password = null;

        if(request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)){
            try{
                LoginRequestDTO dto = om.readValue(request.getReader().lines().collect(Collectors.joining()), LoginRequestDTO.class);

                name = dto.username();
                password = dto.password();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(request.getMethod().equals("POST")){
            name = obtainUsername(request);
            password = obtainPassword(request);
        } else {
            throw new AuthenticationServiceException("Not Supported Request Method : " + request.getMethod());
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(name, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        //UserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String email = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(email, role, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("UserId", "" + customUserDetails.getUserId());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        failed.printStackTrace();

        response.setStatus(401);
    }
}
