package com.eltae.compareTout.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@Data
public class WrongParameters extends RuntimeException{
    private HttpStatus httpStatus;

    public WrongParameters(HttpStatus exceptionStatus, String description) {
        super("Wrong parameters " + description);
        this.httpStatus = exceptionStatus;
        log.warn(description);
    }
}
