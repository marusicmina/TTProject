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
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import javax.validation.Valid;

@Valid
@RestController
@RequestMapping("/traders")
public class TraderController {

    @Autowired
    private TraderService traderService;

    @GetMapping
    public CompletableFuture<ResponseEntity<List<TraderDTO>>> getAllTraders() {
        return traderService.getAllTraders()
                .thenApply(traders -> traders.stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.toList()))
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<TraderDTO>> registerTrader(
            @Valid @RequestBody TraderRegistrationDto traderRegistrationDto) {
        return traderService.registerTrader(traderRegistrationDto)
                .thenApply(this::convertToDTO)
                .thenApply(traderDTO -> new ResponseEntity<>(traderDTO, HttpStatus.CREATED));
    }



    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<String>> loginTrader(@RequestBody TraderLoginDto traderLoginDto) {
        return traderService.loginTrader(traderLoginDto.getUsername(), traderLoginDto.getPassword())
                .thenApply(ResponseEntity::ok);
    }

    

    private TraderDTO convertToDTO(Trader trader) {
        return new TraderDTO(
                trader.getFirstName(),
                trader.getLastName(),
                trader.getUsername()
        );
    }
}

