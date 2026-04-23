package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Category;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.CategoryReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.CategoryRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Request.CategoryCreateResquest;
import com.TranBaoLong2ws.springboot.E_commerce.Request.CategoryRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@Service
public class CategoryServiceImpl implements  CategoryService{

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public Page<CategoryReponse> getAllCategory(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.map(this::convertCategoryToCategoryReponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CategoryReponse> getCategoryByName(String name, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryList =  categoryRepository.findByNameContaining(name,pageable);

        if (categoryList.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        return categoryList.map(this::convertCategoryToCategoryReponse);
    }

    @Override
    @Transactional
    public void CreateCategory(CategoryCreateResquest resquest) {

        boolean exitss = categoryRepository.findByName(resquest.getCategoryName())
                .stream().anyMatch(c->c.getName().equalsIgnoreCase(resquest.getCategoryName()));

        if (exitss) {
            throw new RuntimeException("Category already exists");
        }

        Category category = new Category();
        category.setName(resquest.getCategoryName());
        category.setDescription(resquest.getCategoryDescription());
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void UpdateCategory(CategoryRequest categoryRequest) {

        Category category = categoryRepository.findById(categoryRequest.getId())
                .orElseThrow(()->new RuntimeException("Category not found"));

        boolean exists = categoryRepository
                .findByName(categoryRequest.getCategoryName())
                .stream()
                .anyMatch(c -> c.getId() != category.getId());

        if (exists) {
            throw new RuntimeException("Category already exists");
        }

        if (categoryRequest.getCategoryName() != null) {
            category.setName(categoryRequest.getCategoryName());
        }

        if (categoryRequest.getDescription() != null) {
            category.setDescription(categoryRequest.getDescription());
        }

        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void DeleteCategory(long id) {

        Optional<Category> category = categoryRepository.findById(id);

        if (!category.isPresent()) {
            throw new RuntimeException("Category not found");
        }

        categoryRepository.deleteById(id);
    }

    private CategoryReponse convertCategoryToCategoryReponse(Category category) {
        return  new CategoryReponse(
                category.getName(),
                category.getDescription()
        );
    }
}
