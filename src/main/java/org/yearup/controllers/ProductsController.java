package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Product;
import org.yearup.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("products")
@CrossOrigin
public class ProductsController
{
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }
    // GET /products — search products with optional filters.
    @GetMapping("")
    @PreAuthorize("permitAll()")
    public List<Product> search(@RequestParam(name="cat", required = false) Integer categoryId,
                                @RequestParam(name="minPrice", required = false) Double minPrice,
                                @RequestParam(name="maxPrice", required = false) Double maxPrice,
                                @RequestParam(name="subCategory", required = false) String subCategory)
    {
        return productService.search(categoryId, minPrice, maxPrice, subCategory);
    }
    // GET /products/{id} returns a single product by its id
    @GetMapping("{id}")
    @PreAuthorize("permitAll()")
    public Product getById(@PathVariable int id)
    {
        Product product = productService.getById(id);

        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return product;
    }
    // POST /products admin only, inserts a new product and returns it with 201 Created
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Product> addProduct(@RequestBody Product product)
    {
        Product saved = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    // PUT /products/{id} admin only, updates an existing product.
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product)
    {
        if (productService.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return productService.update(id, product);
    }
    // DELETE /products/{id} — admin only, removes the product from the database.
    // Returns 404 if it doesn't exist, 204 No Content on success.
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id)
    {
        if (productService.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
