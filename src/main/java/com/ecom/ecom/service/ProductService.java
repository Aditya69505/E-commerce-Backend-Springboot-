package com.ecom.ecom.service;

import com.ecom.ecom.dto.ProductRequest;
import com.ecom.ecom.dto.ProductResponse;
import com.ecom.ecom.model.Product;
import com.ecom.ecom.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    public ProductResponse createProduct(ProductRequest productRequest) {

        Product product=new Product();

        updateProductFromRequest(product,productRequest);

        Product savedProduct= productRepository.save(product);

        return mapToProductResponse(savedProduct);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(existingUser -> {
                    updateProductFromRequest(existingUser, productRequest);
                   Product savedProduct= productRepository.save(existingUser);
                   return mapToProductResponse(savedProduct);


                }).orElseThrow(()->new RuntimeException("Product not fount"));

    }


    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse response=new ProductResponse();
        response.setId(savedProduct.getId());
        response.setName(savedProduct.getName());
        response.setDescription(savedProduct.getDescription());
        response.setCategory(savedProduct.getCategory());
        response.setActive(savedProduct.getActive());
        response.setImageUrl(savedProduct.getImageUrl());
        response.setPrice(savedProduct.getPrice());
        response.setStockQuantity(savedProduct.getStockQuantity());
 
          return response;
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {

       product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setCategory(productRequest.getCategory());

        product.setImageUrl(productRequest.getImageUrl());
       product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());


    }


    public Optional<ProductResponse> fetchProduct(Long id) {
      return  productRepository.findById(id)
                .map(this::mapToProductResponse);
    }


    public List<ProductResponse> getAllProduct() {
        return productRepository.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id){
        Product product=productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        product.setActive(false);
        productRepository.save(product);
    }

    public @Nullable List<ProductResponse> searchProducts(String keyword) {
        return productRepository.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}
