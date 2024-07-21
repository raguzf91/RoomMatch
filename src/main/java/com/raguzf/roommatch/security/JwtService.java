package com.raguzf.roommatch.security;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Class for generating and validating JWT tokens
 * Handle token creation, claims extraction and validation
 * @author raguzf
 */
@Service
public class JwtService {


    private long jwtExpiration = 8640000; 

    /**
     * Extracts the username (subject) from the given JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Extracts a specific claim from the given JWT token
     *
     * @param token the JWT token from which to extract the claim
     * @param claimResolver a function that specifies how to resolve the claim from the token
     * @param <T> the type of the claim
     * @return the claim extracted from the token
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        // parse the JWT and get all claims
        final Claims claims = extractAllClaims(token);
        // take an argument of Claims and return a result of type we want to extract (String or Date...)
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token from which to extract the claims
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        //parse the JWT and return the CLAIMS
        return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }

    /**
     * Generates a JWT token for the given user details
     * Calls another overloaded generateToken method
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token for the given user details
     * 
     * @param claims additional claims to include in the token
     * @param userDetails the user details for which to generate the token
     * @return the generated JWT token
     */
    public String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpiration);
    }
    
    /**
     * Builds a JWT token with the specified claims, user details, and expiration time.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details for which to generate the token
     * @param jwtExpiration the expiration time for the token
     * @return the built JWT token
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long jwtExpiration2) {

        // use var for better and readibility when it returns a list of strings
        // retrieve a collection of GrantedAuthority objects
        var authorities = userDetails.getAuthorities()
            .stream()
            // mao each GrantedAuthority object into authority string
            .map(GrantedAuthority::getAuthority)
            .toList();
        return Jwts
            .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                // serialize into string format that can be sent to the client
                .compact();
    }

    /**
     * Validates the given JWT token against the provided user details.
     *
     * @param token the JWT token to validate
     * @param userDetails the user details to validate 
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token from which to extract the expiration date
     * @return the expiration date extracted from the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Retrieves the signing key used for generating and verifying JWT tokens.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        // generate a new secret key used for borth signing and verifying the token
        // extract the key's raw byte representation and store it in a byte array
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
        // convert a byte array to a key
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
