package no.group.spotifystatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidColumnNameException extends RuntimeException{
    public InvalidColumnNameException(String message) {
        super(message);
    }
}
