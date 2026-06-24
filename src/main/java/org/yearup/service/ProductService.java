package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository)
    {
        this.productRepository = productRepository;
    }
    // Search products with optional filters.
    // If a categoryId is provided, we start with just that category's products.
    // Otherwise, we load everything and filter down from there.
    public List<Product> search(Integer categoryId, Double minPrice, Double maxPrice, String subCategory)
    {
        List<Product> products = categoryId != null
                ? productRepository.findByCategoryId(categoryId)
                : productRepository.findAll();
    // Each filter only applies if the parameter was actually provided
        return products.stream()
                       .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                       .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                       .filter(p -> subCategory == null || subCategory.equalsIgnoreCase(p.getSubCategory()))
                       .collect(Collectors.toList());
    }
    // Returns all products belonging to a specific category
    public List<Product> listByCategoryId(int categoryId)
    {
        return productRepository.findByCategoryId(categoryId);
    }

    // Returns a single product by id, or null if it doesn't exist
    public Product getById(int productId)
    {
        return productRepository.findById(productId).orElse(null);
    }
    // Inserts a new product. We reset the id to 0 so the database
    // generates a fresh id instead of reusing whatever was in the request body.
    public Product create(Product product)
    {
        product.setProductId(0);
        return productRepository.save(product);
    }
    // Updates every editable field on an existing product and saves it.
    // We load the existing record first so JPA knows to UPDATE rather than INSERT
    public Product update(int productId, Product product)
    {
        Product existing = productRepository.findById(productId).orElseThrow();
        existing.setName(product.getName());
        existing.setPrice(product.getPrice());
        existing.setCategoryId(product.getCategoryId());
        existing.setDescription(product.getDescription());
        existing.setSubCategory(product.getSubCategory());
        // New code: copy stock before saving, so PUT /products/{id} persists every editable field.
        existing.setStock(product.getStock());
        existing.setFeatured(product.isFeatured());
        existing.setImageUrl(product.getImageUrl());
        return productRepository.save(existing);
    }
    // Removes the product from the database permanently
    public void delete(int productId)
    {
        productRepository.deleteById(productId);
    }
}
