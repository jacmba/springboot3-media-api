package net.jazflix.springboot3mediaapi.error;

import net.jazflix.springboot3mediaapi.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleAllException(
            Exception ex,
            WebRequest request
    ) {
        ErrorDetails errorDetails = buildDetails(ex, request);
        return new ResponseEntity<>(errorDetails,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(
            UserNotFoundException ex,
            WebRequest request
    ) {
        ErrorDetails details = buildDetails(ex, request);
        return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ErrorDetails details = buildDetails(ex, request);

        StringBuilder errMsg = new StringBuilder();
        errMsg.append("Total errors: ");
        errMsg.append(ex.getFieldErrorCount());
        if (ex.getFieldErrorCount() > 0) {
            errMsg.append(". First error: ");
            errMsg.append(ex.getFieldError().getDefaultMessage());
        }

        details.setMessage(errMsg.toString());
        return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
    }

    private ErrorDetails buildDetails(Exception ex, WebRequest request) {
        return new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }
}
