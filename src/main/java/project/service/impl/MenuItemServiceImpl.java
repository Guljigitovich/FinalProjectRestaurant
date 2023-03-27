package project.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.dto.request.MenuItemRequest;
import project.dto.response.MenuItemResponse;
import project.dto.response.SimpleResponse;
import project.entity.Cheque;
import project.entity.MenuItem;
import project.entity.Restaurant;
import project.entity.Subcategory;
import project.exception.NotFoundException;
import project.repositories.MenuItemRepository;
import project.repositories.RestaurantRepository;
import project.repositories.SubcategoryRepository;
import project.service.inter.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;

    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository, SubcategoryRepository subcategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public List<MenuItemResponse> getAllMenuItemService(Long subCategoryId,String keyWord) {
            List<MenuItemResponse>list =new ArrayList<>();
            if(keyWord == null){
              list.addAll(menuItemRepository.getAllMenuItemService());
               list.addAll(menuItemRepository.getMenuItemByStopListsNull());
               return list;
            }
             list.addAll(menuItemRepository.globalSearch(keyWord));
        return list;
    }

    @Override
    public SimpleResponse saveMenuItem(Long restaurantId,Long subCategoryId ,MenuItemRequest menuItemRequest) {
         if(menuItemRequest.getPrice().intValue() < 0){
             return SimpleResponse.builder()
                     .status(HttpStatus.BAD_REQUEST)
                     .message("Price can't be negative number!")
                     .build();
         }
        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setIsVegetarian(menuItemRequest.getIsVegetarian());

        Restaurant restaurant = restaurantRepository.findById(restaurantId).
                orElseThrow(()->{log.error("Menu item doesn't exist");
                    throw  new NotFoundException("Restaurant with id " + restaurantId + "  doesn't exist");
        });
        Subcategory subcategory = subcategoryRepository.findById(subCategoryId).orElseThrow(() -> {
            log.error("Sub category not found!");
            throw new NotFoundException("Sub category not found!");
        });

        menuItem.setRestaurant(restaurant);
         menuItem.setSubcategory(subcategory);
        menuItemRepository.save(menuItem);
        log.info("Menu item successfully saved");
        return SimpleResponse.builder().status(HttpStatus.CREATED).
                message(String.format("This is : %s : menu item successfully saved",menuItem.getName())).build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest) {
        MenuItem newMenuItem = menuItemRepository.findById(menuItemId).
                orElseThrow(() -> new NotFoundException("Restaurant with id " + menuItemId + "  doesn't exist"));
        log.error("Menu item doesn't exist");
          newMenuItem.setName(menuItemRequest.getName());
          newMenuItem.setImage(menuItemRequest.getImage());
          newMenuItem.setDescription(menuItemRequest.getDescription());
          newMenuItem.setPrice(menuItemRequest.getPrice());
          newMenuItem.setIsVegetarian(menuItemRequest.getIsVegetarian());
        menuItemRepository.save(newMenuItem);
        log.info("Menu item successfully updated");
         return SimpleResponse.builder().status(HttpStatus.CREATED).
                 message(String.format("This is : %s : menu item successfully updated",menuItemRequest.getName())).build();
    }

    @Override
    public String deleteMenuItem(Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId).
                orElseThrow(() -> new NotFoundException("Restaurant with id " + menuItemId + "  doesn't exist"));
        log.error("Menu item doesn't exist");
        for (Cheque cheque : menuItem.getCheques()) {
            cheque.getMenuItems().remove(menuItem);
        }
        menuItemRepository.delete(menuItem);
        log.info("Menu item deleted");
        return String.format("This is menu item : %s : deleted",menuItem.getName());
    }

    @Override
    public MenuItemResponse getByIdMenuItem(Long menuItemId) {
        log.error("Menu item doesn't exist");
        return menuItemRepository.getMenuItemById(menuItemId).
                orElseThrow(() -> new NotFoundException("Restaurant with id " + menuItemId + "  doesn't exist"));
    }

    @Override
    public List<MenuItemResponse> sortAscAndDesc(String ascOrDesc) {
        if (ascOrDesc.equals("desc")){
        return menuItemRepository.sortDesc();
        }
        return menuItemRepository.sortAsc();
    }
}
