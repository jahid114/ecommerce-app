package com.jahid.ecommerce.api.product;

import com.jahid.ecommerce.api.user.User;
import com.jahid.ecommerce.api.utility.EnumConstants;
import com.jahid.ecommerce.api.utility.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    ProductService(@Autowired ProductRepository productRepository, @Autowired ModelMapper modelMapper){
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public ProductDto addProduct(ProductDto productDto){
        Product product = this.modelMapper.map(productDto, Product.class);
        if(product.getProductCategory() == null) product.setProductCategory(EnumConstants.Category.UNKNOWN);
        return this.modelMapper.map(this.productRepository.save(product),ProductDto.class);
    }

    public List<ProductDto> getAllProduct(){
        List<Product> products = this.productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product: products ){
            productDtos.add(this.modelMapper.map(product,ProductDto.class));
        }
        return productDtos;
    }

    public ProductDto getProductById(Long id){
        Product product = this.productRepository.findById(id).orElseThrow(()-> new NotFoundException(id,Product.class.getSimpleName()));
        return this.modelMapper.map(product,ProductDto.class);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto){
        Product productToUpdate = this.productRepository.findById(id).orElseThrow(()-> new NotFoundException(id,Product.class.getSimpleName()));
        if(productDto.getProductName() != null) productToUpdate.setProductName(productDto.getProductName());
        if(productDto.getProductDetails() != null) productToUpdate.setProductDetails(productDto.getProductDetails());
        if(productDto.getPrice() != 0) productToUpdate.setPrice(productDto.getPrice());
        if(productDto.getInStock() != 0) productToUpdate.setInStock(productDto.getInStock());
        if(productDto.getSku()!= null) productToUpdate.setSku(productDto.getSku());
        if(productDto.getProductCategory()!=null) productToUpdate.setProductCategory(productDto.getProductCategory());
        Product updatedProduct = this.productRepository.save(productToUpdate);
        return this.modelMapper.map(updatedProduct, ProductDto.class);
    }
    public void deleteProduct(Long id){
        Product existedUser = this.productRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id,User.class.getSimpleName()));
        this.productRepository.deleteById(id);
    }
}
