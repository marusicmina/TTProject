/*package trader.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import antlr.collections.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationProvider(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String token = (String) authentication.getCredentials(); // Dobijamo token iz autentifikacije

        // Proveravamo da li je token validan
        if (jwtUtil.isValidToken(token)) {
            // Ako je token validan, ekstraktujemo korisničko ime i role
            String username = jwtUtil.extractUsername(token);

            // Pravimo korisnički objekat sa rolama (ovde postavljamo samo ROLE_USER kao primer)
            return new UsernamePasswordAuthenticationToken(
                new User(username, "", List.of(new SimpleGrantedAuthority("ROLE_USER"))), 
                token, 
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        // Ako je token nevalidan, vraćamo null, što znači da autentifikacija nije uspela
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}*/
