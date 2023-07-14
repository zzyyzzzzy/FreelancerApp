package com.mercury.FreelancerApp.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor // create a constructor using any final field we created
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // the filterChain here is a design pattern, contains a list of other filters
    // it will call the next filter within the chain

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try{
            // get jwt token from the header
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;


            // continue to the next filter if no token
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            jwt = authHeader.substring(7); // "Bearer " has 7 chars
            username = jwtService.extractUsername(jwt); // extract the username from JWT token

            // if we have a username, and the user is not authenticated
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // get username from the DB see config

                // if the token is valid
                if(jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }catch (Exception e){
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            response.getWriter().write("Error message: " + e.getMessage());
            System.out.println(e.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}
