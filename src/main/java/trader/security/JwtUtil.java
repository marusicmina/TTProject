package trader.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtUtil {
    private String secretKey = "tajniKljuc";  // Koristi sigurniji ključ u stvarnom okruženju!

    // Metoda za generisanje tokena
    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(username)  // Postavljamo korisničko ime kao subjekat
                .withIssuedAt(new Date())  // Postavljamo datum izdavanja tokena
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Token važi 1 sat
                .sign(algorithm);  // Potpisujemo token
    }


}
