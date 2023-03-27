package project.service.impl;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import project.dto.request.SubcategoryRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.SubcategoryResponse;
import project.entity.Category;
import project.entity.Subcategory;
import project.exception.NotFoundException;
import project.repositories.CategoryRepository;
import project.repositories.SubcategoryRepository;
import project.service.inter.SubcategoryService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<SubcategoryResponse> getAllSubcategories() {
        return subcategoryRepository.getAllSubcategories();
    }

    @Override
    public SimpleResponse saveSubcategory(Long categoryId, Long subcategoryId, SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRequest.getName());

        Category category = (categoryRepository.findById(categoryId).
                orElseThrow(() -> new NotFoundException("Category with id " + categoryId + "  doesn't exist")));
        log.error("Subcategory doesn't exist");
//           category.addSubcategory(subcategory);
        subcategory.setCategory(category);
         subcategoryRepository.save(subcategory);
         log.info("Subcategory successfully saved");
         return SimpleResponse.builder().status(HttpStatus.CREATED).
                 message(String.format("This is : %s : subcategory successfully saved",subcategory.getName())).build();
    }

    @Override
    public List<SubcategoryResponse> groupingBySubcategories() {
        return subcategoryRepository.groupingBySubcategories();
    }

    @Override
    public SimpleResponse updateSubcategory(Long categoryId,Long subcategoryId, SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId).
                orElseThrow(() -> new NotFoundException("Subcategory with id " + subcategoryId + "  doesn't exist"));
            log.error("Subcategory doesn't exist");
        subcategory.setName(subcategoryRequest.getName());
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new NotFoundException("Category with id " + categoryId + "  doesn't exist"));
        log.error("Category doesn't exist");
       subcategory.setCategory(category);
       subcategoryRepository.save(subcategory);
       log.info("Subcategory successfully updated");
        return SimpleResponse.builder().status(HttpStatus.CREATED).
                message(String.format("This is : %s : subcategory successfully updated",subcategory.getName())).build();
    }

    @Override
    public SubcategoryResponse getSubcategoryById( Long subcategoryId) {
      return   subcategoryRepository.getSubcategoryById(subcategoryId).
                orElseThrow(()->new NotFoundException("Subcategory with id " + subcategoryId + "  doesn't exist"));

    }

    @Override
    public String deleteSubcategory(Long subcategoryId) {
        boolean exists = subcategoryRepository.existsById(subcategoryId);
        if(!exists){
            throw new NotFoundException("Restaurant with id "+subcategoryId+" doesn't exist");
        }
         subcategoryRepository.deleteById(subcategoryId);
        log.info("Subcategory deleted");
        return "Subcategory with id "+subcategoryId+" is deleted";
    }
}
