package project.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.dto.request.ChequeRequest;
import project.dto.response.ChequeResponse;
import project.dto.response.SimpleResponse;
import project.dto.response.SumAllChequeOfDay;
import project.entity.Cheque;
import project.entity.MenuItem;
import project.entity.User;
import project.exception.NotFoundException;
import project.repositories.ChequeRepository;
import project.repositories.MenuItemRepository;
import project.repositories.UserRepository;
import project.service.inter.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private  final ChequeRepository chequeRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    public ChequeServiceImpl(ChequeRepository chequeRepository, MenuItemRepository menuItemRepository, UserRepository userRepository) {
        this.chequeRepository = chequeRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SimpleResponse saveCheque(Long userId, ChequeRequest chequeRequest) {
       List<MenuItem> menuItems = new ArrayList<>();
        Cheque cheque = new Cheque();
         cheque.setCreatedAt(LocalDate.now());
        for (Long mealId: chequeRequest.getMealsId()) {
            MenuItem menuItem = menuItemRepository.findById(mealId).orElseThrow(() -> {
                log.error("Meal with id - " + mealId + " is not found!");
                throw new NotFoundException("Meal with id - " + mealId + " is not found!");
            });
            menuItems.add(menuItem);
        }
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.error("User with id - " + userId + " not found!");
            throw new NotFoundException("User with id - " + userId + " not found!");
        });
           cheque.setUser(user);
        for (MenuItem menuItem:menuItems) {
             menuItem.getCheques().add(cheque);
        }
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your check has been accepted!")
                .build();
    }

    @Override
    public SimpleResponse deleteCheque(Long chequeId) {
        if(!chequeRepository.existsById(chequeId)){
            throw new NotFoundException("Cheque with id - " + chequeId +" doesn't exists!");
        }
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(() -> {
            log.error("Cheque with id - " + chequeId + " doesn't exists!");
            throw new NotFoundException("Cheque with id - " + chequeId + " doesn't exists!");
        });
             cheque.getMenuItems().forEach(menuItem -> menuItem.getCheques().remove(cheque));
             chequeRepository.deleteById(chequeId);
        return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Cheque with id - " + chequeId + " is deleted!")
                .build();
    }

    @Override
    public List<ChequeResponse> getAll(Long userId) {
          List<ChequeResponse> chequeResponses =new ArrayList<>();
        for (ChequeResponse che: chequeRepository.getAllChequeByUserId(userId)){
             BigDecimal total = che.getAveragePrice().multiply(new BigDecimal(che.getService())).
                     divide(new BigDecimal(100)).add(che.getAveragePrice());
             che.setGrandTotal(total);
             che.setMeals(chequeRepository.getMeals(che.getId()));
             chequeResponses.add(che);
        }
        return chequeResponses;
    }

    @Override
    public SimpleResponse update(Long chequeId, ChequeRequest chequeRequest) {
        Cheque cheque = chequeRepository.findById(chequeId).orElseThrow(() -> {
            log.error("Cheque with id - " + chequeId + " is not found!");
            throw new NotFoundException("Cheque with id - " + chequeId + " is not found!");
        });
        List<MenuItem> menuItems = chequeRequest.getMealsId().stream().map(mealId ->
                menuItemRepository.findById(mealId).orElseThrow(() -> {
                    log.error("Meal with id - " + mealId + " is not found!");
                    throw new NotFoundException("Meal with id - " + mealId + " is not found!");
                })).toList();

        for (MenuItem menuItem: cheque.getMenuItems()) {
            menuItem.getCheques().remove(cheque);
        }
        for (MenuItem menuItem:menuItems) {
              menuItem.getCheques().add(cheque);
        }
         return SimpleResponse.builder()
                .status(HttpStatus.OK)
                .message("Your check has been updated!")
                .build();
    }

    @Override
    public SumAllChequeOfDay sumAllChequeOfDay(Long userId, LocalDate date) {
        List<Cheque> chequeList = chequeRepository.findAll();
        SumAllChequeOfDay sumAllChequeOfDay = new SumAllChequeOfDay();
        Long count= 0L;
        BigDecimal totalSum = new BigDecimal(0);
         int number =1;
        for (Cheque che:chequeList) {
     if(che.getUser().getId().equals(userId) && che.getCreatedAt().equals(date)){
          sumAllChequeOfDay.setWaiter(che.getUser().getFirstName()+" "+che.getUser().getLastName());
           sumAllChequeOfDay.setDate(che.getCreatedAt());
           count ++;

         for (MenuItem menuItem: che.getMenuItems()) {
             totalSum =new BigDecimal(totalSum.intValue()+ menuItem.getPrice().intValue());
             number =menuItem.getRestaurant().getService();
         }
     }
        }
        BigDecimal service =totalSum.multiply(new BigDecimal(number)).divide(new BigDecimal(100));
         sumAllChequeOfDay.setCounterOfCheque(count);
         sumAllChequeOfDay.setTotalSumma(totalSum.add(service));
        return sumAllChequeOfDay;
    }
}
