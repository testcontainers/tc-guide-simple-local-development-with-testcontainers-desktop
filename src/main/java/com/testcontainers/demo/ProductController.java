package com.testcontainers.demo;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
class ProductController {
    private final ProductRepository repo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductController(ProductRepository repo,
                             KafkaTemplate<String, Object> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/api/products")
    Product save(@RequestBody Product product) {
        return repo.save(product);
    }

    @PutMapping("/api/products/{code}")
    void updateProductName(@PathVariable String code, @RequestBody Product product) {
        if (product.getName() != null) {
            repo.updateName(code, product.getName());
        }
        if(product.getPrice() != null) {
            ProductPriceChangedEvent event =
                    new ProductPriceChangedEvent(code, product.getPrice()
            );
            kafkaTemplate.send("product-price-changes", event.productCode(), event);
        }
    }
}
