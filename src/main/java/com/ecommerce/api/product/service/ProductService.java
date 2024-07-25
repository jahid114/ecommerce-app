package com.ecommerce.api.product.service;

import com.ecommerce.api.product.controller.ProductController;
import com.ecommerce.api.product.model.Product;
import com.ecommerce.api.product.request.GetAllProductRequest;
import com.ecommerce.api.product.request.ProductRequest;
import com.ecommerce.api.product.response.ProductResponse;
import com.ecommerce.api.utility.EnumConstants;
import com.ecommerce.api.utility.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ecommerce.api.user.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDao productDao;
    private final ModelMapper modelMapper;

    private final String ROOT_PATH = "src/main/resources/static/product_images";
    private final Path root = Paths.get( ROOT_PATH );


    public ProductResponse addProduct(MultipartFile file , String productInfo )
            throws IOException {
        ProductRequest productRequest = new ObjectMapper()
                .readValue( productInfo, ProductRequest.class );
        Product product = this.modelMapper.map( productRequest, Product.class );

        Path uploadPath =   Paths.get( ROOT_PATH +"/"+ UUID.randomUUID()+ ".png" );
        Files.copy( file.getInputStream( ),uploadPath );
        String url = MvcUriComponentsBuilder
                .fromMethodName(
                        ProductController.class,
                        "getProductImage",
                        uploadPath.getFileName().toString()
                )
                .build().toUriString();
        product.setProductImageUrl( uploadPath.getFileName().toString() );
        product.setProductImageUrl( url );

        if( product.getProductCategory() == null )
            product.setProductCategory(EnumConstants.Category.UNKNOWN);
        return this.modelMapper.map(
                this.productRepository.save(product),
                ProductResponse.class
        );
    }

    public Resource getProductImage(String imageName) throws MalformedURLException {
        Path file = root.resolve(imageName);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    public List<ProductResponse> getAllProduct(GetAllProductRequest getAllProductRequest){
        List<Product> products = productDao.findAllByCriteria(getAllProductRequest);
        List<ProductResponse> productResponses = new ArrayList<>();
        for(Product product: products ){
            productResponses.add(this.modelMapper.map(product, ProductResponse.class));
        }
        return productResponses;
    }

    public ProductResponse getProductById(Long id){
        Product product = this.productRepository.findById(id).orElseThrow(()-> new NotFoundException(id,Product.class.getSimpleName()));
        return this.modelMapper.map(product, ProductResponse.class);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest){
        Product productToUpdate = this.productRepository.findById(id).orElseThrow(()-> new NotFoundException(id,Product.class.getSimpleName()));
        if(productRequest.getProductName() != null) productToUpdate.setProductName(productRequest.getProductName());
        if(productRequest.getProductDetails() != null) productToUpdate.setProductDetails(productRequest.getProductDetails());
        if(productRequest.getPrice() != 0) productToUpdate.setPrice(productRequest.getPrice());
        if(productRequest.getInStock() != 0) productToUpdate.setInStock(productRequest.getInStock());
        if(productRequest.getSku()!= null) productToUpdate.setSku(productRequest.getSku());
        if(productRequest.getProductCategory()!=null) productToUpdate.setProductCategory(productRequest.getProductCategory());
        Product updatedProduct = this.productRepository.save(productToUpdate);
        return this.modelMapper.map(updatedProduct, ProductResponse.class);
    }
    public void deleteProduct(Long id){
        Product existedUser = this.productRepository.findById(id)
                .orElseThrow(()->new NotFoundException(id,User.class.getSimpleName()));
        this.productRepository.deleteById(id);
    }
}
