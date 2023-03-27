package project.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.dto.request.StopListRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.StopListResponse;
import project.entity.MenuItem;
import project.entity.StopList;
import project.exception.NotFoundException;
import project.repositories.MenuItemRepository;
import project.repositories.StopListRepository;
import project.service.inter.StopListService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@Slf4j
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    public StopListServiceImpl(StopListRepository stopListRepository,
                               MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse saveStopList(Long menuItemId,StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> {
            log.error("Menu item with id - " + menuItemId + " is not found!");
            throw new NotFoundException("Menu item with id - " + menuItemId + " is not found!");
        });
        for (StopList stopList: menuItem.getStopLists()){
               if(stopList.getDate().equals(stopListRequest.getDate())){
                   log.info("Stop list for this day already exists!");
                   return SimpleResponse.builder()
                           .status(HttpStatus.BAD_REQUEST)
                           .message("Stop list for this day already exists!")
                           .build();}
        }
        {
        }
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopList.setMenuItem( menuItem);
        stopListRepository.save(stopList);
        log.info("Stop List successfully saved");
        return SimpleResponse.builder().status(HttpStatus.CREATED)
                .message(String.format("This is : %s : stop list successfully saved",stopList.getDate())).build();
    }

    @Override
    public StopListResponse getStopListById(Long stopListId) {
        return stopListRepository.getStopListsById(stopListId).
                orElseThrow(()->new NotFoundException("Stop List with id " + stopListId + "  doesn't exist"));
    }

    @Override
    public List<StopListResponse> getAllStopList(Long menuItemId) {
        return stopListRepository.findAllByMenuItem(menuItemId);
    }

    @Override
    public SimpleResponse updateStopList(Long menuItemId,Long stopListId,StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).orElseThrow(() -> {
            log.error("Menu item with id - " + menuItemId);
            throw new NotFoundException("Menu item with id - " + menuItemId);
        });

        for (StopList stopList:menuItem.getStopLists()){
           if(stopList.getDate().equals(stopListRequest.getDate())){
               log.info("Stop list for this day already exists!");
               return SimpleResponse.builder()
                       .status(HttpStatus.BAD_REQUEST)
                       .message("Stop list for this day already exists!")
                       .build();
           }
        }
              {
        }

        StopList stopList1 = stopListRepository.findById(stopListId).
                orElseThrow(() -> new NotFoundException("Stop List with id " + stopListId + "  doesn't exist"));
        log.error("Stop List doesn't exist");
        stopList1.setReason(stopListRequest.getReason());
        stopList1.setDate(stopListRequest.getDate());
        stopListRepository.save(stopList1);
        log.info("Stop List successfully updated");
        return SimpleResponse.builder().status(HttpStatus.CREATED).
                message(String.format("This is : %s : stop list successfully saved",stopList1.getDate())).build();
    }

    @Override
    public String deleteStopList(Long stopListId) {
        boolean exists = stopListRepository.existsById(stopListId);
        if(!exists){
            throw  new NotFoundException("Stop List with id "+stopListId+" doesn't exist");
        }
        stopListRepository.deleteById(stopListId);
        log.info("Stop List deleted");
        return "Stop List with id "+stopListId+" is deleted";
    }
}
