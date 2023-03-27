package project.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.MenuItemRequest;
import project.dto.response.MenuItemResponse;
import project.dto.response.SimpleResponse;
import project.entity.MenuItem;
import project.service.inter.MenuItemService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/menuItems")
public class MenuItemApi {
    private final MenuItemService menuItemService;

    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @GetMapping("/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<MenuItemResponse>getAllMenuItem(
            @PathVariable Long subCategoryId,
            @RequestParam (required = false) String keyWord
    ){
        return menuItemService.getAllMenuItemService(subCategoryId,keyWord);
    }
    @PostMapping("/{restaurantId}/{subCategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveMenuItem (@PathVariable Long restaurantId,
                                        @PathVariable Long subCategoryId,
                                        @RequestBody MenuItemRequest menuItemRequest){
        return menuItemService.saveMenuItem(restaurantId,subCategoryId,menuItemRequest);
    }

    @PutMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateMenuItem(
                                   @PathVariable Long menuItemId,
                                   @RequestBody MenuItemRequest menuItemRequest){
         return menuItemService.updateMenuItem(menuItemId,menuItemRequest);
    }
    @DeleteMapping("/{menuItemId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public String deleteMenuItem(@PathVariable Long menuItemId){
        return menuItemService.deleteMenuItem(menuItemId);
    }
    @GetMapping("/{menuItemId}")
    public MenuItemResponse getByIdMenuItem(@PathVariable Long menuItemId){
        return menuItemService.getByIdMenuItem(menuItemId);
    }

    @GetMapping("/sort")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<MenuItemResponse>sortAscAndDesc(@RequestParam String ascOrDesc){
        return menuItemService.sortAscAndDesc(ascOrDesc);
    }
}
