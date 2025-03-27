package trader.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.scheduling.annotation.Async;

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
    
    @Async
    public CompletableFuture<List<Trader>> getAllTraders() {
        return CompletableFuture.supplyAsync(traderRepository::findAll);
    }

    @Async
    public CompletableFuture<Trader> saveTrader(Trader trader) {
        trader.setPassword(passwordEncoder.encode(trader.getPassword()));  
        return CompletableFuture.supplyAsync(() -> traderRepository.save(trader));
    }

    @Async
    public CompletableFuture<String> loginTrader(String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            Trader trader = traderRepository.findByUsername(username)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found"));

            if (!passwordEncoder.matches(password, trader.getPassword())) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            }

            return jwtUtil.generateToken(username);
        });
    }

    @Async
    public CompletableFuture<Trader> registerTrader(TraderRegistrationDto traderRegistrationDto) {
        return CompletableFuture.supplyAsync(() -> {
            if (traderRepository.existsByUsername(traderRegistrationDto.getUsername())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is already taken");
            }

            Trader trader = new Trader();
            trader.setFirstName(traderRegistrationDto.getFirstName());
            trader.setLastName(traderRegistrationDto.getLastName());
            trader.setUsername(traderRegistrationDto.getUsername());
            trader.setPassword(passwordEncoder.encode(traderRegistrationDto.getPassword()));

            return traderRepository.save(trader);
        });
    }

    @Async
    public CompletableFuture<Trader> findTraderByUsername(String username) {
        return CompletableFuture.supplyAsync(() -> 
            traderRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trader not found with username: " + username))
        );
    }
}



