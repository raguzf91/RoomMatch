package com.raguzf.roommatch.security;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Custom filter that processes incoming HTTP requests to authenticate users based on JWT tokens
 * Extends OncePerRequestFilter to ensure the filter is executed only once per request
 * @author raguzf
 */
@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{
    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    
    /**
     * Processes the incoming request to validate the JWT token and authenticate the user.
     * If the request is for the authentication endpoint, it bypasses the filter chain.
     * Otherwise, it extracts the JWT token from the Authorization header, validates it, and sets the authentication context.
     *
     * @param request the HttpServletRequest object that contains the request the client has made to the servlet
     * @param response the HttpServletResponse object that contains the response the servlet sends to the client
     * @param filterChain the FilterChain for invoking the next filter or the resource
     * @throws ServletException if an input or output error occurs while the filter is processing the request
     * @throws IOException if the request for the POST could not be handled
     */
    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain) throws ServletException, IOException {
            
            // check if the request path contains a valid path
            if(request.getServletPath().contains("/api/v1/auth")) {
                filterChain.doFilter(request, response);
                return;
            }
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            final String jwt;
            final String userEmail;

            // if the authorization header is missing or does not start with Bearer
            // bypass further processing and pass the req and res to the next filter
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // extract the JWT TOKEN
            jwt = authHeader.substring(7);
            // extract the email
            userEmail = jwtService.extractUsername(jwt);
            // proceed with user authentication if contains the email and there is no existing authentications
            if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
                // validate the token
                if(jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                        );
                        authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        // Set the authentication token
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            // continue with the next filter in the chain
            filterChain.doFilter(request, response);
        
    }

}
