package com.TranBaoLong2ws.springboot.E_commerce.Controller;

import com.TranBaoLong2ws.springboot.E_commerce.Entity.ProductImages;
import com.TranBaoLong2ws.springboot.E_commerce.Service.ProductImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Image Product REST API", description = "Images manager")
@RestController
@RequestMapping("api/product")
public class ImageProductController {


    private final ProductImageService productImageService;

    public ImageProductController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @Operation(summary = "Add to Imagegs", description = "Add image for product")
    @PostMapping("/{productId}/images")
    public ResponseEntity<ProductImages> uploadImage(
            @PathVariable Long productId,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        ProductImages image = productImageService.addImage(productId, file);

        return ResponseEntity.status(HttpStatus.CREATED).body(image);
    }
}
