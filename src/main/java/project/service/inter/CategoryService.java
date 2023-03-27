package project.service.inter;

import project.dto.request.CategoryRequest;
import project.dto.response.CategoryResponse;
import project.dto.response.SimpleResponse;
import project.entity.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryResponse> getAllCategory();

    SimpleResponse saveCategory(CategoryRequest categoryRequest);

    CategoryResponse getByIdCategory(Long categoryId);

    SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest);

    String deleteCategory(Long categoryId);
}
