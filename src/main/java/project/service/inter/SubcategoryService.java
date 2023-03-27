package project.service.inter;

import project.dto.request.SubcategoryRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.SubcategoryResponse;
import project.entity.Subcategory;

import java.util.List;

public interface SubcategoryService {
    List<SubcategoryResponse> getAllSubcategories();

    SimpleResponse saveSubcategory(Long categoryId, Long subcategoryId, SubcategoryRequest subcategoryRequest);

    List<SubcategoryResponse> groupingBySubcategories();

    SimpleResponse updateSubcategory(Long categoryId,Long subcategoryId, SubcategoryRequest subcategoryRequest);

    SubcategoryResponse getSubcategoryById( Long subcategoryId);

    String deleteSubcategory(Long subcategoryId);
}
