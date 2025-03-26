package trader.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Oznaka za globalno upravljanje izuzecima u aplikaciji
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Obrada izuzetka ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // HTTP status koji će biti vraćen (BAD_REQUEST)
    public Map<String, Object> handleResponseStatusException(ResponseStatusException ex) {
        // Kreiraj mapu sa detaljima greške
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());  // Vreme kada je greška nastala
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());  // Status greške
        errorDetails.put("error", "Bad Request");  // Tip greške
        errorDetails.put("message", ex.getReason());  // Poruka greške koja je postavljena u ResponseStatusException
        return errorDetails;  // Vraća detalje greške kao JSON
    }

    // Obrada svih ostalih izuzetaka (ne specifičnih za ResponseStatusException)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // HTTP status za internu grešku
    public Map<String, Object> handleGenericException(Exception ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorDetails.put("error", "Internal Server Error");
        errorDetails.put("message", "An unexpected error occurred: " + ex.getMessage());  // Opšti tekst greške
        return errorDetails;
    }

    // (Opcionalno) Možeš dodati još izuzetaka po potrebi, npr. za validaciju, autentifikaciju itd.
    
}
