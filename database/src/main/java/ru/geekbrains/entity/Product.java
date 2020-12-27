package ru.geekbrains.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column
    private String name;

    @Positive
    @Column
    private BigDecimal price;

    @Column
    private String picture;

    @ManyToOne(targetEntity = Brand.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(targetEntity = Category.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {}

    public Product(Long id, @NotBlank String name, @Positive BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.price.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicture() {
        return picture;
    }

    public Brand getBrand() {
        return brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
