package com.nospostumos.loja.service;

import com.nospostumos.loja.dto.ProductCreateRequest;
import com.nospostumos.loja.dto.ProductMeasurementRequest;
import com.nospostumos.loja.dto.ProductMeasurementResponse;
import com.nospostumos.loja.dto.ProductResponse;
import com.nospostumos.loja.dto.ProductUpdateRequest;
import com.nospostumos.loja.entity.Product;
import com.nospostumos.loja.entity.ProductMeasurement;
import com.nospostumos.loja.exception.ProductNotFoundException;
import com.nospostumos.loja.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductResponse create(ProductCreateRequest request) {
        validateProductData(request.name(), request.description(), request.price());

        Product product = new Product();
        updateProductData(product, request.name(), request.description(), request.price(), request.imageUrl());
        replaceMeasurements(product, request.measurements());

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public List<ProductResponse> findActive() {
        return productRepository.findAllByActiveTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse findById(Long id) {
        return toResponse(getProduct(id));
    }

    @Transactional
    public ProductResponse findPublicById(Long id) {
        return toResponse(productRepository.findByIdAndActiveTrue(Objects.requireNonNull(id, "id não pode ser nulo"))
                .orElseThrow(() -> new ProductNotFoundException(id)));
    }

    @Transactional
    public ProductResponse update(Long id, ProductUpdateRequest request) {
        validateProductData(request.name(), request.description(), request.price());

        Product product = getProduct(id);
        updateProductData(product, request.name(), request.description(), request.price(), request.imageUrl());
        product.setActive(request.active());
        replaceMeasurements(product, request.measurements());

        return toResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponse deactivate(Long id) {
        Product product = getProduct(id);
        product.setActive(false);
        return toResponse(productRepository.save(product));
    }

    private Product getProduct(Long id) {
        return productRepository.findById(Objects.requireNonNull(id, "id não pode ser nulo"))
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    private void validateProductData(String name, String description, BigDecimal price) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome do produto é obrigatório");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Descrição do produto é obrigatória");
        }
        if (price == null || price.signum() <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser maior que zero");
        }
    }

    private void updateProductData(
            Product product,
            String name,
            String description,
            BigDecimal price,
            String imageUrl
    ) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(imageUrl);
    }

    private void replaceMeasurements(Product product, List<ProductMeasurementRequest> requests) {
        product.getMeasurements().clear();
        List<ProductMeasurementRequest> measurements =
                requests == null ? Collections.emptyList() : requests;
        for (ProductMeasurementRequest request : measurements) {
            product.addMeasurement(new ProductMeasurement(
                    request.size(),
                    request.bust(),
                    request.waist(),
                    request.length()
            ));
        }
    }

    private ProductResponse toResponse(Product product) {
        List<ProductMeasurementResponse> measurements = product.getMeasurements().stream()
                .map(measurement -> new ProductMeasurementResponse(
                        measurement.getSize(),
                        measurement.getBust(),
                        measurement.getWaist(),
                        measurement.getLength()
                ))
                .toList();

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                measurements,
                product.isActive()
        );
    }
}
