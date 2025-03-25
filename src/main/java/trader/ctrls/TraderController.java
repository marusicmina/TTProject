package trader.ctrls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import trader.dto.TradeOrderDTO;
import trader.dto.TraderDTO;
import trader.models.TradeOrder;
import trader.models.Trader;
import trader.service.TraderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/traders")  // Putanja za pristup svim traderima
public class TraderController {

    @Autowired
    private TraderService traderService;

    // Endpoint za dobijanje svih tradera
  /*  @GetMapping
    public List<TraderDTO> getAllTraders() {
        return traderService.getAllTraders();
    }*/
    @GetMapping
    public List<TraderDTO> getAllTrader() {
        return traderService.getAllTraders()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private TraderDTO convertToDTO(Trader trader) {
        return new TraderDTO(
        trader.getFirstName(),
        trader.getLastName(),
        trader.getUsername()
        // Opcionalno, ne šaljemo password iz sigurnosnih razloga
        );
    }
    
    
}
