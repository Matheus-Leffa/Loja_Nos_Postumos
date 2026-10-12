package com.nospostumos.loja.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super("Produto não encontrado: " + id);
    }
}
