package trader.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import trader.dto.TraderLoginDto;
import trader.dto.TraderRegistrationDto;
import trader.dto.TraderDTO;
import trader.models.Trader;
import trader.service.TraderService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Valid
@RestController
@RequestMapping("/traders")  
public class TraderController {

    @Autowired
    private TraderService traderService;


    @GetMapping
    public List<TraderDTO> getAllTrader() {
        return traderService.getAllTraders()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    @PostMapping("/register")
    public ResponseEntity<TraderDTO> registerTrader(@RequestBody TraderRegistrationDto traderRegistrationDto) {
        Trader trader = traderService.registerTrader(traderRegistrationDto);
        TraderDTO traderDTO = convertToDTO(trader);
        return new ResponseEntity<>(traderDTO, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public String loginTrader(@RequestBody TraderLoginDto traderLoginDto) {
        return traderService.loginTrader(traderLoginDto.getUsername(), traderLoginDto.getPassword());
    }


    
    @PostMapping
    public Trader createTrader(@RequestBody Trader trader) {
        return traderService.saveTrader(trader);
    }

    
    private TraderDTO convertToDTO(Trader trader) {
        return new TraderDTO(
                trader.getFirstName(),
                trader.getLastName(),
                trader.getUsername()
                
        );
    }
}
