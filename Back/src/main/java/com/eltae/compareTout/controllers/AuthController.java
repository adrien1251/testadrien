package com.eltae.compareTout.controllers;

import com.eltae.compareTout.constants.Routes;
import com.eltae.compareTout.dto.user.LoginDto;
import com.eltae.compareTout.exceptionHandler.ExceptionCatcher;
import com.eltae.compareTout.services.AuthService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping()
    public ResponseEntity<?> auth(@RequestBody LoginDto login) {
        return ResponseEntity.status(200).body(this.authService.login(login));
    }
}
