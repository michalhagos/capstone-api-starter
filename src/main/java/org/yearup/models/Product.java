package org.yearup.models;

import jakarta.persistence.*;
// Represents a product available for purchase in the store.
@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;
    // Links this product to its category
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "description")
    private String description;
    // Used for filtering within a category
    @Column(name = "subcategory")
    private String subCategory;
    // How many units are currently available
    @Column(name = "stock")
    private int stock;
    // Featured products can be highlighted on the storefront
    @Column(name = "featured")
    private boolean isFeatured;

    @Column(name = "image_url")
    private String imageUrl;
    // Default constructor required by JPA
    public Product() {
    }
    // Convenience constructor for building a fully populated product in one line
    public Product(int productId, String name, double price, int categoryId, String description, String subCategory, int stock, boolean isFeatured, String imageUrl)
    {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.description = description;
        this.subCategory = subCategory;
        this.stock = stock;
        this.isFeatured = isFeatured;
        this.imageUrl = imageUrl;
    }

    public int getProductId()
    {
        return productId;
    }

    public void setProductId(int productId)
    {
        this.productId = productId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public int getCategoryId()
    {
        return categoryId;
    }

    public void setCategoryId(int categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getSubCategory()
    {
        return subCategory;
    }

    public void setSubCategory(String subCategory)
    {
        this.subCategory = subCategory;
    }

    public int getStock()
    {
        return stock;
    }

    public void setStock(int stock)
    {
        this.stock = stock;
    }

    public boolean isFeatured()
    {
        return isFeatured;
    }

    public void setFeatured(boolean featured)
    {
        isFeatured = featured;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}
