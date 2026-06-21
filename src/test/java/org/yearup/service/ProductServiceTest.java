package org.yearup.service;

import org.junit.jupiter.api.Test;
import org.yearup.models.Product;
import org.yearup.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductServiceTest { @Test
public void search_withNoFilters_shouldReturnFeaturedAndNonFeaturedProducts()
{
    // arrange
    ProductRepository productRepository = mock(ProductRepository.class);
    ProductService productService = new ProductService(productRepository);

    List<Product> products = new ArrayList<Product>();
    products.add(new Product(1, "Smartphone", 499.99, 1, "Phone", "Black", 50, false, "smartphone.jpg"));
    products.add(new Product(3, "Headphones", 99.99, 1, "Headphones", "White", 100, true, "headphones.jpg"));

    when(productRepository.findAll()).thenReturn(products);

    // act
    List<Product> actual = productService.search(null, null, null, null);

    // assert
    assertEquals(2, actual.size(), "Search should return all products, not only featured products.");
    assertFalse(actual.get(0).isFeatured(), "This proves non-featured products are no longer removed by mistake.");
}
    @Test
    public void search_withPriceAndSubCategoryFilters_shouldReturnAllMatchingProducts()
    {
        // arrange
        ProductRepository productRepository = mock(ProductRepository.class);
        ProductService productService = new ProductService(productRepository);

        List<Product> products = new ArrayList<Product>();
        products.add(new Product(1, "Smartphone", 499.99, 1, "Phone", "Black", 50, false, "smartphone.jpg"));
        products.add(new Product(11, "Coffee Maker", 79.99, 3, "Coffee", "Black", 30, false, "coffee-maker.jpg"));
        products.add(new Product(3, "Headphones", 99.99, 1, "Headphones", "White", 100, true, "headphones.jpg"));

        when(productRepository.findAll()).thenReturn(products);

        // act
        List<Product> actual = productService.search(null, 50.00, 600.00, "Black");

        // assert
        assertEquals(2, actual.size(), "Both black products in the price range should be returned.");
    }


}