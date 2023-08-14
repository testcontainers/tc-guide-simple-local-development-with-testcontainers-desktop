package com.testcontainers.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
    "spring.test.database.replace=none",
    "spring.datasource.url=jdbc:tc:postgresql:15.2-alpine:///db"
})
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    @Test
    void shouldCreateProductIfNotExist() {
        String code = UUID.randomUUID().toString();
        Product product = new Product(null, code, "test product", BigDecimal.TEN);
        repository.upsert(product);
    }

    @Test
    void shouldUpdateProductTitle() {
        String code = "P101";

        repository.updateName(code, "Updated Product Title");

        Optional<Product> productOptional = repository.findByCode(code);
        assertThat(productOptional).isPresent();
        assertThat(productOptional.get().getName()).isEqualTo("Updated Product Title");
    }
}