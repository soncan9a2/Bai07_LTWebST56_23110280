package vn.iotstar.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import vn.iotstar.Entity.ProductEntity;

public interface IProductService {
    List<ProductEntity> findAll();
    Page<ProductEntity> findAll(Pageable pageable);
    List<ProductEntity> findAll(Sort sort);
    Optional<ProductEntity> findById(Long id);
    <S extends ProductEntity> S save(S entity);
    void deleteById(Long id);
    void delete(ProductEntity entity);
    <S extends ProductEntity> Optional<S> findOne(Example<S> example);
    List<ProductEntity> findByProductNameContaining(String name);
    Page<ProductEntity> findByProductNameContaining(String name, Pageable pageable);
}


