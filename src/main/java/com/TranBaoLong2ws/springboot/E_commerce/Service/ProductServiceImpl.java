package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Category;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.Product;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.ProductReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.CategoryRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.ProductRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Request.ProductRequest;
import com.TranBaoLong2ws.springboot.E_commerce.Utils.FinAuthenticatedUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Optional;


@Service
public class ProductServiceImpl implements  ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<ProductReponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productsPage = productRepository.findAll(pageable);

        return productsPage.map(this::convertoProductReponse);
    }


    @Override
    @Transactional
    public ProductReponse getById(long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return convertoProductReponse(product);
    }

    @Override
    @Transactional
    public void createProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.getIdCategory())
                .orElseThrow(()-> new RuntimeException("category not found"));

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStock(productRequest.getStock());
        product.setCategory(category);
        productRepository.save(product);

    }

    @Override
    public void deleteProduct(long id) {
        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        }

        productRepository.delete(product.get());
    }

    @Override
    public Page<ProductReponse> searchProduct(String name, BigDecimal minPrice, BigDecimal maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Product> products = productRepository.searchProducts(name,minPrice,maxPrice,pageable);

        if (products.isEmpty()){
            throw new RuntimeException("Product not found");
        }

        return products.map(this::convertoProductReponse);
    }

    @Override
    @Transactional
    public void updateProduct(long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.getName() != null){
            product.setName(request.getName());
        }

        if (request.getDescription() != null){
            product.setDescription(request.getDescription());
        }

        if (request.getPrice() != null){
            product.setPrice(request.getPrice());
        }

        if (request.getStock() != 0){
            product.setStock(request.getStock());
        }

        if (request.getIdCategory() != 0){
            Category category = categoryRepository.findById(request.getIdCategory())
                    .orElseThrow(() -> new RuntimeException("category not found"));

            product.setCategory(category);
        }

        productRepository.save(product);
    }


    private ProductReponse convertoProductReponse(Product product){
        return  new ProductReponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getDescription(),
                product.getCreatedAt()
        );
    }
}
