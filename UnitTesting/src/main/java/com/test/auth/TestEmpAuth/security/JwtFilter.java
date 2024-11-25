//package com.test.auth.TestEmpAuth.security;
//
//import com.test.auth.TestEmpAuth.service.CustomUserDetailsService;
//import com.test.auth.TestEmpAuth.service.JWTService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JWTService jwtService;
//
//    @Autowired
//    ApplicationContext context;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//
//        String token = null;
//
//        String username = null;
//
//        if (authHeader != null && authHeader.startsWith("Bearer "))
//        {
//            token = authHeader.substring(7);
//            username = jwtService.extractUserName(token);
//        }
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null)
//        {
//            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
//
//            if (jwtService.validateToken(token, userDetails))
//            {
//                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//            }
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
