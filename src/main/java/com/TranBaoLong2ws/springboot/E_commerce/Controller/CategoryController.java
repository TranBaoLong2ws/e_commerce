package com.TranBaoLong2ws.springboot.E_commerce.Controller;


import com.TranBaoLong2ws.springboot.E_commerce.Reponse.CategoryReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Request.CategoryCreateResquest;
import com.TranBaoLong2ws.springboot.E_commerce.Request.CategoryRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category REST API", description = "Category manager")
@RestController
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all category", description = "Fetch all category from signed in user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<CategoryReponse> getCategory(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size){
        return categoryService.getAllCategory(page,size);
    }


    @Operation(summary = "Search category", description = "Search category by name")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Page<CategoryReponse> CategoryByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        return categoryService.getCategoryByName(name,page,size);
    }


    @Operation(summary = "Create Category", description = "Create Category for the signed in user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createCategory(@Valid @RequestBody CategoryCreateResquest resquest){

        categoryService.CreateCategory(resquest);

        return "Add Success";
    }

    @Operation(summary = "Update Category", description = "Update Category for the signed in user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
   public void UpdateCategory( @Valid @RequestBody CategoryRequest resquest) throws Exception{
        categoryService.UpdateCategory(resquest);
    }


    @Operation(summary = "Delete Category", description = "Delete category by id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteCategory(@Valid @PathVariable long id){
        categoryService.DeleteCategory(id);
    }

}
