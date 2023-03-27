package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.dto.response.MenuItemResponse;
import project.entity.MenuItem;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    @Query("select new project.dto.response.MenuItemResponse " +
            "(m.id,m.name,m.image,m.price,m.isVegetarian,m.description) from MenuItem m order by m.price desc ")
    List<MenuItemResponse> sortDesc();
    @Query("select new project.dto.response.MenuItemResponse " +
            "(m.id,m.name,m.image,m.price,m.isVegetarian,m.description) from MenuItem  m order by m.price asc ")
    List<MenuItemResponse> sortAsc();
     @Query("select distinct new project.dto.response.MenuItemResponse" +
             "(m.id,m.name,m.image,m.price,m.isVegetarian,m.description) from MenuItem m" +
             " join StopList s on s.menuItem.id = m.id where s.date != current date ")
    List<MenuItemResponse> getAllMenuItemService();
    List<MenuItemResponse> getMenuItemByStopListsNull();
    @Query("select new project.dto.response.MenuItemResponse " +
            "(m.id,m.name,m.image,m.price,m.description,m.isVegetarian) from MenuItem m " +
            "where m.name ilike concat('%', :keyWord, '%' ) or m.description ilike " +
            "concat('%', :keyWord, '%') or m.subcategory.name ilike concat('%', :keyWord, '%') " +
            "or m.subcategory.category.name ilike concat('%', :keyWord , '%') ")
    List<MenuItemResponse> globalSearch (String keyWord);
    Optional<MenuItemResponse> getMenuItemById(Long menuItemId);
}
