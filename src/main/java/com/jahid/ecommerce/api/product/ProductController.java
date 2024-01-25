package com.jahid.ecommerce.api.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    ProductController(@Autowired ProductService productService){
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        return new ResponseEntity<>(this.productService.addProduct(productDto), HttpStatus.CREATED);
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
