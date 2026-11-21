package com.productmanager.catalog.model;

public record Product(Long id, String name, double price, int stock, String category) {
}
