package com.eltae.compareTout.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApplicationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private HttpStatus errCode;
    private String errMsg;

    public ApplicationException(HttpStatus errCode, String errMsg) {
        super();
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
