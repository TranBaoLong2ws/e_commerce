package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Category;
import com.TranBaoLong2ws.springboot.E_commerce.Reponse.CategoryReponse;
import com.TranBaoLong2ws.springboot.E_commerce.Request.CategoryCreateResquest;
import com.TranBaoLong2ws.springboot.E_commerce.Request.CategoryRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface CategoryService {

   public Page<CategoryReponse> getAllCategory(int page, int size);

    public Page<CategoryReponse> getCategoryByName(String name, int page, int size);

    public void CreateCategory(CategoryCreateResquest resquest);

    public void UpdateCategory(CategoryRequest categoryRequest);

    public void DeleteCategory(long id);


}
