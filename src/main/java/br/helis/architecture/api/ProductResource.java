package br.helis.architecture.api;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.fluentvalidator.context.Error;
import br.com.fluentvalidator.context.ValidationResult;
import br.helis.architecture.exceptions.ProductNotFoundException;
import br.helis.architecture.model.Product;
import br.helis.architecture.service.ProductServiceImpl;
import br.helis.architecture.validations.ProductValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/products")
@RestController
@Tag(name = "Product API", description = "Operations related to products")
public class ProductResource {

    private ProductServiceImpl productService;

    public ProductResource(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Find all products", description = "Returns all products")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
    })
    public ResponseEntity<List<Product>> get() {
        List<Product> result = productService.findAll();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Find product by ID", description = "Returns a product given its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"), 
        @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable @Parameter(name = "id", description = "Product id", example = "1") Long id) {
        try {
            Product product = productService.findById(id);
            return ResponseEntity.ok(product);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Create a new product", description = "Create a new product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully created"), 
    })
    @PostMapping
    public ResponseEntity<Collection<Error>> post(@RequestBody Product product){
        ValidationResult result = new ProductValidator().validate(product);
        if(!result.isValid()) {
            return ResponseEntity.badRequest().body(result.getErrors());
        }
        productService.save(product);
        URI location = ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(product.getId())
                        .toUri();
        return ResponseEntity.created(location).build();
    }
    
    @Operation(summary = "Update an existing product", description = "Update an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully updated"), 
        @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> put(@PathVariable @Parameter(name = "id", description = "Product id", example = "1") Long id, @RequestBody Product entity) {
        try {
            if (id.equals(entity.getId())) {
                throw new IllegalArgumentException("id conflicts with product id body");
            }
            productService.update(entity);
            return ResponseEntity.noContent().build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Delete an existing product", description = "Delete an existing product")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Successfully deleted"), 
        @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Parameter(name = "id", description = "Product id", example = "1") Long id) {
        try {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
