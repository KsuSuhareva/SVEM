package by.suhareva.svem.controllers.handler;

import by.suhareva.svem.exceptions.DAOException;
import by.suhareva.svem.exceptions.ResponseNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@Slf4j
public class SvemExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMassage> catchMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Catch exception  MethodArgumentNotValidException {}", e.getMessage());
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorMassage massage = new ErrorMassage(new Date(), status.value(),"The request must have  format: for legal entities '1234567890', for individuals- '12AB123456'", e.getClass().getSimpleName());
        return new ResponseEntity<>(massage, status);
    }

    @ExceptionHandler(ResponseNotFoundException.class)
    public ResponseEntity<ErrorMassage> catchResponseNotFoundException(ResponseNotFoundException e) {
        log.error("Catch exception  ResponseNotFoundException with cause: {}", e.getMessage());
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorMassage massage = new ErrorMassage(new Date(), status.value(), e.getMessage(), e.getClass().getSimpleName());
        return new ResponseEntity<>(massage, status);
    }

    @ExceptionHandler(DAOException.class)
    public ResponseEntity<ErrorMassage> catchDAOException(DAOException e) {
        log.error("Catch exception  DAOException with cause: {}", e.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMassage massage = new ErrorMassage(new Date(), status.value(), e.getMessage(),e.getClass().getSimpleName());
        return new ResponseEntity<>(massage, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMassage> catchAnyException(Exception e) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMassage massage = new ErrorMassage(new Date(), status.value(), e.getMessage(),e.getClass().getSimpleName());
        return new ResponseEntity<>(massage, status);
    }


}
