package com.example.capstone_backend.common.jwt;

import com.example.capstone_backend.common.Response;
import com.example.capstone_backend.domain.account.exception.UserPermissionDeniedException;
import com.example.capstone_backend.domain.user.entity.UserInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        //request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader("Authorization");

        //Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            try {
                filterChain.doFilter(request, response);
            } catch (ServletException | IOException e) {
                setErrorResponse(response, "권한이 없습니다.");
            }
            return;
        }

        System.out.println("authorization now");
        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            System.out.println("token expired");
            try {
                filterChain.doFilter(request, response);
            } catch (ServletException | IOException | ExpiredJwtException e ) {
                setErrorResponse(response, "토큰의 유효기간이 만료되었습니다.");
            }

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 username과 role 획득
        String email = jwtUtil.getUsername(token);

        //UserDetails에 회원 정보 객체 담기
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, token, customUserDetails.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        try {
            filterChain.doFilter(request, response);
        } catch (ServletException | IOException e) {
            setErrorResponse(response, "인증 과정에서 문제가 발생했습니다.");
        }

    }

    private void setErrorResponse(
            HttpServletResponse response,
            String message
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(403);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(Response.error(message, HttpStatus.FORBIDDEN)));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
