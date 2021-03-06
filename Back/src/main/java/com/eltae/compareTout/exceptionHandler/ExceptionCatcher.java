package com.eltae.compareTout.exceptionHandler;

import com.eltae.compareTout.dto.exception.ExceptionResponseDto;
import com.eltae.compareTout.exceptions.ApplicationException;
import com.eltae.compareTout.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.validation.UnexpectedTypeException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

public class ExceptionCatcher {

    @ExceptionHandler({NotFoundException.class})
    public final ResponseEntity<Object> notFoundException(NotFoundException ex){
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public final ResponseEntity<Object> userNameNotFoundException(UsernameNotFoundException ex){
        return ResponseEntity.status(422).body(ex.getMessage());
    }
//A faire generic
//    @ExceptionHandler({BadCredentialException.class})
//    public final ResponseEntity<Object> badCredentialException(BadCredentialException ex){
//        return ResponseEntity.status(401).body(ex.getMessage());
//    }


    @ExceptionHandler(value = {ApplicationException.class})
    public ResponseEntity<ExceptionResponseDto> applicationException(ApplicationException ex, WebRequest request) {
        return ResponseEntity.status(ex.getErrCode())
                .body(ExceptionResponseDto.builder()
                        .statusErrorCode(ex.getErrCode().value())
                        .statusErrorMessage(ex.getErrCode().getReasonPhrase())
                        .errorMessage(ex.getErrMsg())
                        .build()
                );
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    public final ResponseEntity<Object> BadArgumentException(UnexpectedTypeException ex){
        return ResponseEntity.status(404).body(ex.getMessage());
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity genericExceptionHandler(Exception ex, WebRequest request) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ExceptionResponseDto.builder()
                .statusErrorCode(INTERNAL_SERVER_ERROR.value())
                .statusErrorMessage(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .errorMessage(ex.getMessage())
                .build());
    }}
