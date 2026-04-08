package com.spring_reddit_project.spring_reddit_project.service;

import com.spring_reddit_project.spring_reddit_project.dto.AuthenticationResponse;
import com.spring_reddit_project.spring_reddit_project.dto.LoginRequest;
import com.spring_reddit_project.spring_reddit_project.dto.RefreshTokenRequest;
import com.spring_reddit_project.spring_reddit_project.dto.RegisterRequest;
import com.spring_reddit_project.spring_reddit_project.exceptions.SpringRedditException;
import com.spring_reddit_project.spring_reddit_project.model.NotificationEmail;
import com.spring_reddit_project.spring_reddit_project.model.User;
import com.spring_reddit_project.spring_reddit_project.model.VerificationToken;
import com.spring_reddit_project.spring_reddit_project.repository.UserRepository;
import com.spring_reddit_project.spring_reddit_project.repository.VerificationTokenRepository;
import com.spring_reddit_project.spring_reddit_project.security.JwtProvider;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {


    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;


    @Transactional
    public void signup(RegisterRequest registerRequest){

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),
                "Thank you for signing up to spring Reddit, " + "please click on the url below to activate you account : "+
                        "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
        return token;
    }


    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken =  verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
//        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
    }

    @Transactional
    protected void fetchUserAndEnable(VerificationToken verificationToken) {
        String username =  verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);

//        return new AuthenticationResponse(token, loginRequest.getUsername());
        return AuthenticationResponse
                .builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();
    }

    public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getUsername());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .build();
    }

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User springUser) {
            return userRepository.findByUsername(springUser.getUsername())
                    .orElseThrow(() ->
                            new UsernameNotFoundException(
                                    "User not found: " + springUser.getUsername()));
        }

        throw new IllegalStateException("Authentication principal is not a User");
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
              &&  authentication.isAuthenticated()
             && !(authentication instanceof AnonymousAuthenticationToken);

//        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }


}
