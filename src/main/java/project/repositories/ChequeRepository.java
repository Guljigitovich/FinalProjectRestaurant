package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.dto.response.ChequeResponse;
import project.dto.response.MenuItemForCheque;
import project.entity.Cheque;

import java.util.List;

public interface ChequeRepository extends JpaRepository<Cheque, Long> {
    @Query("select new project.dto.response.ChequeResponse(c.id, c.createdAt," +
            " concat(c.user.firstName,' ',c.user.lastName), sum(m.price), m.restaurant.service)" +
            " from Cheque c join c.menuItems m where c.user.id = ?1 group by c.id, c.createdAt," +
            " c.user.firstName, c.user.lastName, m.restaurant.service")
    List<ChequeResponse> getAllChequeByUserId (Long userId);
@Query("select new project.dto.response.MenuItemForCheque (m.id,m.name,m.price," +
        "count (m)) from MenuItem m join m.cheques c where  c.id =?1 group by m.id , m.name, m.price")
    List<MenuItemForCheque> getMeals(Long id);
}