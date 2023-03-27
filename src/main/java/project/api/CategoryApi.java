package project.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.CategoryRequest;
import project.dto.response.CategoryResponse;
import project.dto.response.SimpleResponse;
import project.entity.Category;
import project.service.inter.CategoryService;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/categories")
@RestController
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<CategoryResponse>getAllCategory(){
      return categoryService.getAllCategory();
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest){
        return categoryService.saveCategory(categoryRequest);
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public CategoryResponse getByIdCategory(@PathVariable Long categoryId){
        return categoryService.getByIdCategory(categoryId);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateCategory(@PathVariable Long categoryId,
                                   @RequestBody CategoryRequest categoryRequest){
        return categoryService.updateCategory(categoryId,categoryRequest);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public String deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
