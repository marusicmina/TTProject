package trader.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trader.models.Trader;
import trader.repositories.TraderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TraderService {

    @Autowired
    private TraderRepository traderRepository;

    // Metoda za dobijanje svih tradera
    public List<Trader> getAllTraders() {
        return traderRepository.findAll();
    }

}
