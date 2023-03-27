package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.dto.response.CategoryResponse;
import project.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select new project.dto.response.CategoryResponse (c.id,c.name) from Category c")
    List<CategoryResponse>getAllCategories();
    Optional<CategoryResponse> getCategoryById (Long categoryId);
}