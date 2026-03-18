package com.ecommerce.product_api.service;

import com.ecommerce.product_api.dto.ProductRequestDTO;
import com.ecommerce.product_api.dto.ProductResponseDTO;
import com.ecommerce.product_api.entity.Product;
import com.ecommerce.product_api.exception.ResourceNotFoundException;
import com.ecommerce.product_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // Convert Entity → ResponseDTO
    private ProductResponseDTO toResponseDTO(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());
        return dto;
    }

    // Convert RequestDTO → Entity
    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setQuantity(dto.getQuantity());
        return product;
    }

    // Create
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = toEntity(requestDTO);
        Product saved = productRepository.save(product);
        return toResponseDTO(saved);
    }

    // Get All
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Get One
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id));
        return toResponseDTO(product);
    }

    // Update
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id));

        existing.setName(requestDTO.getName());
        existing.setDescription(requestDTO.getDescription());
        existing.setPrice(requestDTO.getPrice());
        existing.setQuantity(requestDTO.getQuantity());

        return toResponseDTO(productRepository.save(existing));
    }

    // Delete
    public void deleteProduct(Long id) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + id));
        productRepository.deleteById(existing.getId());
    }
}



//
//    // Create a product
//    public Product createProduct(Product product) {
//        return productRepository.save(product);
//    }
//
//    // Get all products
//    public List<Product> getAllProducts() {
//        return productRepository.findAll();
//    }
//
//    // Get one product by id
//    public Product getProductById(Long id) {
//        return productRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
//    }
//
//    // Update a product
//    public Product updateProduct(Long id, Product updatedProduct) {
//        Product existingProduct = getProductById(id);
//
//        existingProduct.setName(updatedProduct.getName());
//        existingProduct.setDescription(updatedProduct.getDescription());
//        existingProduct.setPrice(updatedProduct.getPrice());
//        existingProduct.setQuantity(updatedProduct.getQuantity());
//
//        return productRepository.save(existingProduct);
//    }
//
//    // Delete a product
//    public void deleteProduct(Long id) {
//        Product existingProduct = getProductById(id);
//        productRepository.deleteById(existingProduct.getId());
//    }









