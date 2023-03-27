package project.service.inter;

import project.dto.request.MenuItemRequest;
import project.dto.response.MenuItemResponse;
import project.dto.response.SimpleResponse;
import project.entity.MenuItem;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getAllMenuItemService(Long subCategoryId,String keyWord);

    SimpleResponse saveMenuItem(Long restaurantId,Long subCategoryId, MenuItemRequest menuItemRequest);

    SimpleResponse updateMenuItem(Long menuItemId, MenuItemRequest menuItemRequest);

    String deleteMenuItem(Long menuItemId);

    MenuItemResponse getByIdMenuItem(Long menuItemId);

    List<MenuItemResponse> sortAscAndDesc(String ascOrDesc);
}
