package project.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return new ExceptionResponse(HttpStatus.NOT_FOUND,
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }


    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse handleAlreadyExistException(AlreadyExistException exception) {
        return new ExceptionResponse(HttpStatus.CONFLICT,
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }


    @ExceptionHandler(BadCredentialException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handleBadCredentialException(BadCredentialException exception) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN,
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(BadRequestException exception) {
        return new ExceptionResponse(HttpStatus.BAD_REQUEST,
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }

    @ExceptionHandler(NoValidException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse noValidException(NoValidException e) {
        return new ExceptionResponse(HttpStatus.CONFLICT,
                e.getClass().getSimpleName(),
                e.getMessage());
    }
}
