package com.raguzf.roommatch.auth;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import com.raguzf.roommatch.email.EmailService;
import com.raguzf.roommatch.email.EmailTemplateName;
import com.raguzf.roommatch.model.Gender;
import com.raguzf.roommatch.role.Role;
import com.raguzf.roommatch.role.RoleRepository;
import com.raguzf.roommatch.security.JwtService;
import com.raguzf.roommatch.user.Token;
import com.raguzf.roommatch.user.TokenRepository;
import com.raguzf.roommatch.user.User;
import com.raguzf.roommatch.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

/**
 * Service class that handles user registration, authentication and account activation processes.
 * It interacts with repositories to manage user data tokens, and roles and uses an EmailService to send validation emails.
 * @author raguzf
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private String activationUrl = "http://localhost:4200/activate-account";

    /**
     * Registers a new user and sends a validation email.
     *
     * @param request the registration request containing user details
     * @throws MessagingException if there is an error sending the validation email
     */
    public void register(RegistrationRequest request) throws MessagingException {
        
        Role userRole = roleRepository.findByName("USER")
            .orElseThrow(() -> new IllegalStateException("ROLE USER NOT INITIALIZED"));
        User user = User.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .dateOfBirth(LocalDate.parse(request.getDateOfBirth()))
            .email(request.getEmail())
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .gender(Gender.fromString(request.getGender()))
            .bio(request.getBio())
            .address(request.getAddress())
            .phoneNumber(request.getPhoneNumber())
            .accountLocked(false)
            .enabled(true)
            .roles(Set.of(userRole))
            .build();
        userRepository.save(user);
        sentValidationEmail(user);

    }

    /**
     * Sends a validation email to the user with an activation token.
     *
     * @param user the user to whom the validation email is to be sent
     * @throws MessagingException if there is an error sending the email
     */
    private void sentValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);

        emailService.sendEmail(
            user.getEmail(),
            user.getFullName(),
            EmailTemplateName.ACTIVATE_ACCOUNT,
            activationUrl,
            newToken,
            "Account Activation"
        );
        
    }

    /**
     * Generates and saves an activation token for the user.
     *
     * @param user the user for whom the token is to be generated
     * @return the generated activation token
     */
    private String generateAndSaveActivationToken(User user) {
        // generate a token
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                           .token(generatedToken)
                           .createdAt(LocalDateTime.now())
                           .expiresAt(LocalDateTime.now().plusMinutes(15))
                           .user(user)
                           .build();
        tokenRepository.save(token);
        return generatedToken;                
        
    }

    /**
     * Generates a random activation code of the specified length.
     *
     * @param length the length of the activation code
     * @return the generated activation code
     */
    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for(int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }

    /**
     * Authenticates the user and generates a JWT token.
     *
     * @param request the authentication request containing email and password
     * @return the authentication response containing the JWT token
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
    
        var auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        var claims = new HashMap<String, Object>();
        var user = ((User) auth.getPrincipal());
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, (User) auth.getPrincipal());

        return AuthenticationResponse.builder()
                                     .token(jwtToken)
                                     .build();
   
}


    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Invalid token"));

        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sentValidationEmail(savedToken.getUser());
            throw new RuntimeCryptoException("Activation token has expired. A new token has be sent to the email adress");
        }

        User user = userRepository.findById(savedToken.getUser().getId())
                                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }
}
