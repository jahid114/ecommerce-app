package com.ecommerce.api.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController()
@RequestMapping("/products")
@RequiredArgsConstructor( onConstructor_ = @Autowired )
public class ProductController {
    private final ProductService productService;

    @PostMapping()
    public ResponseEntity<ProductResponse> addProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productInfo") String productInfo
    ) throws IOException {
        return new ResponseEntity<>(
                this.productService.addProduct(file,productInfo),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/file/{imageName:.+}")
    public ResponseEntity<Resource> getProductImage(
            @PathVariable String imageName
    ) throws MalformedURLException {
        Resource file = productService.getProductImage(imageName);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProduct(
            @RequestBody GetAllProductRequest getAllProductRequest
    ){
        return ResponseEntity.ok( this.productService.getAllProduct( getAllProductRequest ) );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable String id){
        ProductResponse productResponse = this.productService
                .getProductById( Long.parseLong( id ) );
        return ResponseEntity.ok( productResponse );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable String id,
            @RequestBody ProductRequest productRequest
    ){
        ProductResponse updatedProduct = this.productService.updateProduct( Long.parseLong( id ), productRequest );
        return ResponseEntity.ok( updatedProduct );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id){
        this.productService.deleteProduct(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
