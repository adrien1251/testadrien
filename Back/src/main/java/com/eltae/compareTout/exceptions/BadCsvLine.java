package com.eltae.compareTout.exceptions;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

@Log4j2
@Data
public class BadCsvLine extends RuntimeException{
    private HttpStatus httpStatus;

    public BadCsvLine(HttpStatus exceptionStatus, String description) {
        super("Bad csv error: " + description);
        this.httpStatus = exceptionStatus;
        log.warn(description);
    }
}
