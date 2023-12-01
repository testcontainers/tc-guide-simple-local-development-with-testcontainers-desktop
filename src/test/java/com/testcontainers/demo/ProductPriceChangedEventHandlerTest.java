package com.testcontainers.demo;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest(
  properties = { "spring.kafka.consumer.auto-offset-reset=earliest" }
)
@Import(ContainersConfig.class)
class ProductPriceChangedEventHandlerTest {

  @Autowired
  private KafkaTemplate<String, Object> kafkaTemplate;

  @Autowired
  private ProductRepository productRepository;

  private String productCode;

  @BeforeEach
  void setUp() {
    productCode = UUID.randomUUID().toString();
    Product product = new Product(
      null,
      productCode,
      "Product-" + productCode,
      BigDecimal.TEN
    );
    productRepository.save(product);
  }

  @Test
  void shouldHandleProductPriceChangedEvent() {
    ProductPriceChangedEvent event = new ProductPriceChangedEvent(
      productCode,
      new BigDecimal("14.50")
    );

    kafkaTemplate.send("product-price-changes", event.productCode(), event);

    await()
      .pollInterval(Duration.ofMillis(500))
      .atMost(10, SECONDS)
      .untilAsserted(() -> {
        Optional<Product> optionalProduct = productRepository.findByCode(
          productCode
        );
        assertThat(optionalProduct).isPresent();
        assertThat(optionalProduct.get().getCode()).isEqualTo(productCode);
        assertThat(optionalProduct.get().getPrice())
          .isEqualTo(new BigDecimal("14.50"));
      });
  }
}
