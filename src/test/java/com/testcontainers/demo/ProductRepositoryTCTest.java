package com.testcontainers.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest(properties = { "spring.test.database.replace=none" })
@Testcontainers
class ProductRepositoryTCTest {

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
    "postgres:16-alpine"
  );

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
    assertThat(productOptional.get().getName())
      .isEqualTo("Updated Product Title");
  }
}
