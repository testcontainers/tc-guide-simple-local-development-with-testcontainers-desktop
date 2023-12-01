package com.testcontainers.demo;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

interface ProductRepository extends JpaRepository<Product, Long> {
  Optional<Product> findByCode(String code);

  @Modifying
  @Query("update Product p set p.price = :price where p.code = :code")
  void updateProductPrice(
    @Param("code") String code,
    @Param("price") BigDecimal price
  );

  @Modifying
  @Query(
    value = """
    insert into products(code, name, price)
    values(:#{#p.code}, :#{#p.name}, :#{#p.price}) ON CONFLICT DO NOTHING
    """,
    nativeQuery = true
  )
  void upsert(@Param("p") Product product);

  @Modifying
  @Query(
    value = """
    update products
    set name = :name, updated_at = CURRENT_TIMESTAMP
    where code = :code
    """,
    nativeQuery = true
  )
  void updateName(String code, String name);
}
