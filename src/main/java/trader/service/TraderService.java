package trader.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import trader.dto.TraderRegistrationDto;
import trader.models.Trader;
import trader.repositories.TraderRepository;

@Service
public class TraderService {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // BCryptPasswordEncoder se injektuje ovde

    // Metoda za dobijanje svih tradera
    public List<Trader> getAllTraders() {
        return traderRepository.findAll();
    }

    // Metoda za kreiranje novog tradera
    public Trader saveTrader(Trader trader) {
        trader.setPassword(passwordEncoder.encode(trader.getPassword()));  // Šifrovanje lozinke
        return traderRepository.save(trader);
    }

    // Metoda za logovanje tradera
    public Trader loginTrader(String username, String password) {
        Trader trader = traderRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Trader not found"));

        // Proveravamo lozinku
        if (!passwordEncoder.matches(password, trader.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        return trader;
    }
    public Trader registerTrader(TraderRegistrationDto traderRegistrationDto) {
        // Proveri da li korisničko ime već postoji
        if (traderRepository.existsByUsername(traderRegistrationDto.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }

        // Kreiraj novog tradera
        Trader trader = new Trader();
        trader.setFirstName(traderRegistrationDto.getFirstName());
        trader.setLastName(traderRegistrationDto.getLastName());
        trader.setUsername(traderRegistrationDto.getUsername());
        trader.setPassword(traderRegistrationDto.getPassword());  // Lozinka je uneta, biće šifrovana pri snimanju
       // trader.setEmail(traderRegistrationDto.getEmail());

        // Šifrovanje lozinke pre nego što se sačuva
        return saveTrader(trader);
    }
    
}


