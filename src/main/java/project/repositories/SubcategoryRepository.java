package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.dto.response.SubcategoryResponse;
import project.entity.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {
     @Query("select s.name from Subcategory s group by s.name order by s.name desc ")
    List<SubcategoryResponse> groupingBySubcategories();
     @Query("select new project.dto.response.SubcategoryResponse (s.id,s.name) from Subcategory s")
     List<SubcategoryResponse>getAllSubcategories();
     Optional<SubcategoryResponse> getSubcategoryById(Long subcategoryId);
}