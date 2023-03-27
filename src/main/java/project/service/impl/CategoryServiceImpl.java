package project.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.dto.request.CategoryRequest;
import project.dto.response.CategoryResponse;
import project.dto.response.SimpleResponse;
import project.entity.Category;
import project.exception.NotFoundException;
import project.repositories.CategoryRepository;
import project.service.inter.CategoryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.getAllCategories();
    }

    @Override
    public SimpleResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
         categoryRepository.save(category);
         log.info("Category successfully saved");
         return SimpleResponse.builder().status(HttpStatus.CREATED).
                 message(String.format("This is : %s : category successfully saved",category.getName())).build();
    }

    @Override
    public CategoryResponse getByIdCategory(Long categoryId) {
        log.error("Category doesn't exist");
        return categoryRepository.getCategoryById(categoryId).orElseThrow(()->new NotFoundException("Category with id " + categoryId + "  doesn't exist"));
    }

    @Override
    public SimpleResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        Category newCategory = categoryRepository.findById(categoryId).
                orElseThrow(()->new NotFoundException("Category with id " + categoryId + "  doesn't exist"));
        log.error("Category doesn't exist");
        newCategory.setName(categoryRequest.getName());
         categoryRepository.save(newCategory);
         log.info("Category successfully updated");
         return SimpleResponse.builder().status(HttpStatus.CREATED).
                 message(String.format("This is : %s : category successfully updated",newCategory.getName())).build();
    }

    @Override
    public String deleteCategory(Long categoryId) {
        boolean exists = categoryRepository.existsById(categoryId);
        if(!exists){
            throw new NotFoundException("Category with id "+categoryId+" doesn't exist");
        }
        categoryRepository.deleteById(categoryId);
        log.info("Category deleted");
        return "Category with id "+categoryId+" is deleted";
    }
}
