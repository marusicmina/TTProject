package trader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import trader.dto.TraderRegistrationDto;
import trader.models.Trader;
import trader.repositories.TraderRepository;
import trader.security.JwtUtil;

@Service
public class TraderService {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  

    @Autowired
    private JwtUtil jwtUtil;
    
    
    public List<Trader> getAllTraders() {
        return traderRepository.findAll();
    }

    
    public Trader saveTrader(Trader trader) {
        trader.setPassword(passwordEncoder.encode(trader.getPassword()));  
        return traderRepository.save(trader);
    }

    
    public String loginTrader(String username, String password) {
        Trader trader = traderRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found"));

        
        if (!passwordEncoder.matches(password, trader.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return jwtUtil.generateToken(username);
    }

   
    public Trader registerTrader(TraderRegistrationDto traderRegistrationDto) {
        if (traderRepository.existsByUsername(traderRegistrationDto.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
        }

        Trader trader = new Trader();
        trader.setFirstName(traderRegistrationDto.getFirstName());
        trader.setLastName(traderRegistrationDto.getLastName());
        trader.setUsername(traderRegistrationDto.getUsername());
        
        
        trader.setPassword(passwordEncoder.encode(traderRegistrationDto.getPassword()));

        return traderRepository.save(trader);
    }

    public Trader findTraderByUsername(String username) {
        return traderRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found with username: " + username));
    }
}




