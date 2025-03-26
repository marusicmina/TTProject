/*package trader.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import trader.models.Trader;

import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "yourSecretKey"; // Tajni ključ, obavezno ga menaj u proizvodnji!

    // Generisanje JWT tokena
    public String generateToken(Trader trader) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);  // Koristi HMAC256 za potpisivanje tokena
        return JWT.create()
                .withSubject(trader.getUsername())  // Postavljanje korisničkog imena u subjekat
                .withIssuedAt(new Date())  // Postavljanje trenutnog vremena kao vremena izdavanja
                .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))  // Token važi 1 sat
                .sign(algorithm);  // Potpisivanje tokena koristeći algoritam
    }

    // Validacija JWT tokena
    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);  // Postavi algoritam za verifikaciju
            JWT.require(algorithm)  // Kreiraj verifikator koji koristi algoritam
                .build()
                .verify(token);  // Pokušaj da verifikuješ token
            return true;  // Ako je verifikacija uspešna
        } catch (Exception e) {
            return false;  // Ako se desi greška pri verifikaciji
        }
    }

    // Ekstraktovanje korisničkog imena iz tokena
    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);  // Dekodiranje tokena bez verifikacije
        return decodedJWT.getSubject();  // Vraća korisničko ime iz subjekta tokena
    }
}*/
