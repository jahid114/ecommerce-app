package com.jahid.ecommerce.api.product;

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
public class ProductController {
    private final ProductService productService;

    ProductController(@Autowired ProductService productService){
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDto> addProduct(@RequestParam("file") MultipartFile file, @RequestParam("productInfo") String productInfo) throws IOException {
        return new ResponseEntity<>(this.productService.addProduct(file,productInfo), HttpStatus.CREATED);
    }

    // Todo Need to fix the getProductImage method
    @GetMapping("/file/{imageName:.+}")
    public ResponseEntity<Resource> getProductImage(@PathVariable String imageName) throws MalformedURLException {
        Resource file = productService.getProductImage(imageName);
        return ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\"").
                body(file);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProduct(){
        return ResponseEntity.ok(this.productService.getAllProduct());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable String id){
        ProductDto productDto = this.productService.getProductById(Long.parseLong(id));
        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String id, @RequestBody ProductDto productDto){
        ProductDto updatedProduct = this.productService.updateProduct(Long.parseLong(id),productDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id){
        this.productService.deleteProduct(Long.parseLong(id));
        return ResponseEntity.noContent().build();
    }
}
