package com.TranBaoLong2ws.springboot.E_commerce.Controller;

import com.TranBaoLong2ws.springboot.E_commerce.Reponse.ProductReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Request.ProductRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@Tag(name = "Product REST API" , description = "Product management operations")
@RestController
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create product", description = "Create product for the signed in user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public String createProduct(@Valid @RequestBody ProductRequest request){
        productService.createProduct(request);
        return "Success";
    }

    @Operation(summary = "Get all product", description = "Fetch all product from signed in user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ProductReponse> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
       return productService.getAll(page, size);
    }

    @Operation(summary = "Get product by id", description = "Search product by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProductReponse getProductById(@PathVariable long id){
        return productService.getById(id);
    }

    @Operation(summary = "Delete product", description = "Delete product by id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable @Min(1) long id){
        productService.deleteProduct(id);
    }

    @Operation(summary = "Search product", description = "Search product by name")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/search")
    public Page<ProductReponse> searchProductByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam BigDecimal minPrice,
            @RequestParam BigDecimal maxPrice
            ){
        return productService.searchProduct(name, minPrice, maxPrice, page, size);
    }


    @Operation(summary = "Edit product", description = "Update product")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public void updateProduct(@PathVariable long id, @RequestBody  ProductRequest request){
        productService.updateProduct(id, request);
    }
}
