package com.jahid.ecommerce.api.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jahid.ecommerce.api.user.User;
import com.jahid.ecommerce.api.utility.EnumConstants;
import com.jahid.ecommerce.api.utility.NotFoundException;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    private final String ROOT_PATH = "src/main/resources/static/product_images";
    private final Path root = Paths.get( ROOT_PATH );

    ProductService( @Autowired ProductRepository productRepository,
                    @Autowired ModelMapper modelMapper ) throws IOException {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        Files.createDirectories( root );
    }

    public ProductDto addProduct(MultipartFile file , String productInfo) throws IOException {
        ProductDto productDto = new ObjectMapper().readValue(productInfo,ProductDto.class);
        Product product = this.modelMapper.map(productDto, Product.class);

        Path uploadPath =   Paths.get(ROOT_PATH +"/"+ UUID.randomUUID()+ ".png");
        Files.copy(file.getInputStream(),uploadPath);
        String url = MvcUriComponentsBuilder.fromMethodName(ProductController.class,
                "getProductImage", uploadPath.getFileName().toString()).
                build().toUriString();
        product.setProductImageUrl(uploadPath.getFileName().toString());
        product.setProductImageUrl(url);

        if(product.getProductCategory() == null) product.setProductCategory(EnumConstants.Category.UNKNOWN);
        return this.modelMapper.map(this.productRepository.save(product),ProductDto.class);
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
