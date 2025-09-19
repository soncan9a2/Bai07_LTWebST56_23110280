package vn.iotstar.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.Entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByProductNameContaining(String name);
    Page<ProductEntity> findByProductNameContaining(String name, Pageable pageable);
    Optional<ProductEntity> findByProductName(String name);
    Optional<ProductEntity> findByCreateDate(Date createAt);
}


