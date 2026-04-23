package com.TranBaoLong2ws.springboot.E_commerce.Service;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.Product;
import com.TranBaoLong2ws.springboot.E_commerce.Entity.ProductImages;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.ProductImageRepository;
import com.TranBaoLong2ws.springboot.E_commerce.Repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final String UPLOAD_DIR = "uploads/";

    public ProductImageServiceImpl(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }


    @Override
    public ProductImages addImage(Long productId, MultipartFile file) throws IOException {
        // check product tồn tại

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("product not found"));

        // 2. Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        if (!file.getContentType().startsWith("image/")) {
            throw new RuntimeException("File must be an image");
        }

        // 3. Tạo tên file
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // 4. Tạo thư mục nếu chưa có
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 5. Lưu file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 6. Lưu DB
        ProductImages productImages = new ProductImages();
        productImages.setProduct(product);
        productImages.setUrl("/uploads/" + fileName);

        return productImageRepository.save(productImages);
    }
}
