package com.productmanager.catalog.controller;

import com.productmanager.catalog.model.CreateProductRequest;
import com.productmanager.catalog.model.Product;
import com.productmanager.catalog.model.UpdateStockRequest;
import com.productmanager.catalog.model.UpdateProductRequest;
import com.productmanager.catalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "Productos", description = "Gestión del catálogo de productos almacenado en memoria")
@RestController
@RequestMapping("/api/productos")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Listar productos", description = "Obtiene todos los productos registrados en el catálogo")
    @GetMapping
    public List<Product> findAll() {
        return productService.findAll();
    }

    @Operation(summary = "Consultar producto", description = "Obtiene la información de un producto a partir de su identificador")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @Operation(summary = "Crear producto", description = "Registra un producto con nombre, precio, existencias y categoría")
    @ApiResponse(responseCode = "201", description = "Producto creado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody CreateProductRequest request) {
        Product created = productService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @Operation(summary = "Editar producto", description = "Actualiza nombre, precio, existencias y categoría de un producto")
    @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
        return productService.update(id, request);
    }

    @Operation(summary = "Actualizar existencias", description = "Modifica únicamente la cantidad disponible de un producto")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @PutMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @Valid @RequestBody UpdateStockRequest request) {
        return productService.updateStock(id, request.stock());
    }

    @Operation(summary = "Eliminar producto", description = "Elimina del catálogo el producto asociado al identificador indicado")
    @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
