package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.user.JwtResponse;
import com.eltae.compareTout.dto.user.LoginDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.security.JwtTokenUtil;
import com.eltae.compareTout.services.AuthService;
import com.eltae.compareTout.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.LOGIN)
@Api(value = "Connexion", description = "Connexion", tags = {"Connexion"})
public class AuthController extends ExceptionCatcher {
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userDetailsService;

//    @PostMapping()
//    public ResponseEntity<?> auth(@RequestBody LoginDto login) {
//        return ResponseEntity.status(200).body(this.authService.login(login));
//    }

    @PostMapping()
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginDto login) {
        authenticate(login.getEmail(), login.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(login.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "USER_DISABLED");
        } catch (BadCredentialsException e) {
            throw new ApplicationException(HttpStatus.NOT_FOUND, "INVALID_CREDENTIALS");
        }
    }
}
