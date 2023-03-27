package project.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.dto.request.SubcategoryRequest;
import project.dto.response.SimpleResponse;
import project.dto.response.SubcategoryResponse;
import project.entity.Subcategory;
import project.service.inter.SubcategoryService;

import java.util.List;

@RequestMapping("/api/subcategories")
@RestController
public class SubcategoryApi {
    private final SubcategoryService subcategoryService;

    public SubcategoryApi(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public List<SubcategoryResponse>getAllSubcategory(){
        return subcategoryService.getAllSubcategories();
    }

    @PostMapping("/{categoryId}/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse saveSubcategory (@PathVariable Long categoryId,
                                           @PathVariable  Long subcategoryId,
                                           @RequestBody SubcategoryRequest subcategoryRequest){
        return subcategoryService.saveSubcategory(categoryId,subcategoryId,subcategoryRequest);

    }
    @GetMapping("/grouping")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<SubcategoryResponse>groupingBySubcategories(){
            return subcategoryService.groupingBySubcategories();
    }
    @PutMapping("/{categoryId}/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SimpleResponse updateSubcategory(@PathVariable Long categoryId,
                                                 @PathVariable Long subcategoryId,
                                                 @RequestBody SubcategoryRequest subcategoryRequest){
        return subcategoryService.updateSubcategory(categoryId,subcategoryId,subcategoryRequest);
    }

    @GetMapping("/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF')")
    public SubcategoryResponse getSubcategoryById(
                                                  @PathVariable Long subcategoryId){
        return subcategoryService.getSubcategoryById(subcategoryId);
    }
    @DeleteMapping("/{subcategoryId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','CHEF','WAITER')")
    public String deleteSubcategory (@PathVariable Long subcategoryId){
        return subcategoryService.deleteSubcategory(subcategoryId);
    }


}
