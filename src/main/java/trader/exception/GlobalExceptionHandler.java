package trader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    Map<String, Object> errors = new HashMap<>();
	    errors.put("timestamp", LocalDateTime.now());
	    errors.put("status", HttpStatus.BAD_REQUEST.value());
	    errors.put("error", "Validation Failed");

	    Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
	            .collect(Collectors.toMap(
	                    error -> error.getField(),
	                    error -> error.getDefaultMessage(),
	                    (existing, replacement) -> existing
	            ));

	    errors.put("messages", fieldErrors);
	    return errors;
	}


   @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  
    public Map<String, Object> handleGenericException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", "An unexpected error occurred: " + ex.getMessage());  
        return errorDetails;
    }

  
}
