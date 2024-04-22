package com.hommies.linkgeneratorsystem.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String errorMessage;
    private String debugMessage;
    private HttpStatus status;
    private LocalDateTime date;

    public ErrorResponse(HttpStatus status){
        this.status = status;
    }
}
