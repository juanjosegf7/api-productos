package com.productmanager.catalog.service;

import com.productmanager.catalog.exception.ProductNotFoundException;
import com.productmanager.catalog.model.CreateProductRequest;
import com.productmanager.catalog.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public synchronized List<Product> findAll() {
        return List.copyOf(products);
    }

    public synchronized Product findById(Long id) {
        return products.stream().filter(product -> product.id().equals(id))
                .findFirst().orElseThrow(() -> new ProductNotFoundException(id));
    }

    public synchronized Product create(CreateProductRequest request) {
        Product product = new Product(nextId.getAndIncrement(), request.name().trim(),
                request.price(), request.stock(), request.category().trim());
        products.add(product);
        return product;
    }

    public synchronized Product updateStock(Long id, int stock) {
        for (int index = 0; index < products.size(); index++) {
            Product product = products.get(index);
            if (product.id().equals(id)) {
                Product updated = new Product(product.id(), product.name(), product.price(), stock, product.category());
                products.set(index, updated);
                return updated;
            }
        }
        throw new ProductNotFoundException(id);
    }

    public synchronized void delete(Long id) {
        boolean removed = products.removeIf(product -> product.id().equals(id));
        if (!removed) {
            throw new ProductNotFoundException(id);
        }
    }
}
