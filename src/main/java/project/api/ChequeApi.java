package project.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.ChequeRequest;
import project.dto.response.ChequeResponse;
import project.dto.response.SimpleResponse;
import project.dto.response.SumAllChequeOfDay;
import project.service.inter.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/checks")
public class ChequeApi {
    private final ChequeService chequeService;

    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    List<ChequeResponse>getAllCheque(@PathVariable Long userId){
        return chequeService.getAll(userId);
    }

    @PostMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    SimpleResponse saveCheque (@PathVariable Long userId,@RequestBody ChequeRequest chequeRequest){
        return chequeService.saveCheque(userId,chequeRequest);
    }

    @PutMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    SimpleResponse update (@PathVariable Long chequeId,
                           @RequestBody ChequeRequest chequeRequest){
        return chequeService.update(chequeId,chequeRequest);
    }

    @DeleteMapping("/{chequeId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    public SimpleResponse deleteCheque (@PathVariable Long chequeId){
        return chequeService.deleteCheque(chequeId);
    }

    @GetMapping("/sumAllChequeOfDay")
    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    SumAllChequeOfDay sumAllChequeOfDay(@PathVariable Long userId,
                                        @RequestParam LocalDate date){
return    chequeService.sumAllChequeOfDay(userId,date);
    }
}
