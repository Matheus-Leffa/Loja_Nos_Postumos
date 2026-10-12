package com.nospostumos.loja.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

@Entity
@Table(
        name = "product_measurements",
        uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "size"})
)
public class ProductMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Size size;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal bust;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal waist;

    @DecimalMin(value = "0.0", inclusive = false)
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal length;

    protected ProductMeasurement() {
    }

    public ProductMeasurement(Size size, BigDecimal bust, BigDecimal waist, BigDecimal length) {
        this.size = size;
        this.bust = bust;
        this.waist = waist;
        this.length = length;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public BigDecimal getBust() {
        return bust;
    }

    public void setBust(BigDecimal bust) {
        this.bust = bust;
    }

    public BigDecimal getWaist() {
        return waist;
    }

    public void setWaist(BigDecimal waist) {
        this.waist = waist;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }
}
