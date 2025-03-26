package trader.security;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private JwtUtil jwtUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicijalizuj JwtUtil
        jwtUtil = new JwtUtil();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String token = getTokenFromRequest(httpRequest);

        if (token == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Token is missing");
            return;
        }

        try {
            // Validacija tokena pomoću JwtUtil klase
            jwtUtil.verifyToken(token);
            
            // Ekstrakcija korisničkog imena iz tokena
            String username = jwtUtil.extractUsername(token);
            
            // Postavljanje korisničkog imena u request atribute (može biti korisno za dalje procesiranje)
            httpRequest.setAttribute("username", username);

        } catch (JWTVerificationException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Invalid or expired token");
            return;
        }

        chain.doFilter(request, response);  // Nastavi sa filtriranjem ako je token validan
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        // Token se obično šalje u Authorization headeru u formatu "Bearer <token>"
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);  // Ukloni "Bearer " prefiks
        }
        return null;  // Ako nije prisutan
    }

    @Override
    public void destroy() {
    }
}


