/*package trader.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String username;
    private String token;

    // Konstruktor sa parametrima
    public JwtAuthenticationToken(String username, String token) {
        super(null);  // Super klasa traži podatke o autorizaciji, ovde možete postaviti null
        this.username = username;
        this.token = token;
        setAuthenticated(true);  // Postavi autentifikaciju kao uspešnu
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;  // token je kredencijal
    }

    @Override
    public Object getPrincipal() {
        return username;  // username je glavni entitet
    }
}*/
