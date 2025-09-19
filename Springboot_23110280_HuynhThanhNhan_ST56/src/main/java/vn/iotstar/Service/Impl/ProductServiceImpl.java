package vn.iotstar.Service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.Entity.ProductEntity;
import vn.iotstar.Repository.ProductRepository;
import vn.iotstar.Service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public <S extends ProductEntity> S save(S entity) {
        if (entity.getProductId() == null) {
            return productRepository.save(entity);
        } else {
            Optional<ProductEntity> existing = findById(entity.getProductId());
            if (existing.isPresent()) {
                ProductEntity toUpdate = existing.get();
                BeanUtils.copyProperties(entity, toUpdate, "productId", "createDate");
                return (S) productRepository.save(toUpdate);
            }
            return productRepository.save(entity);
        }
    }

    @Override
    public Optional<ProductEntity> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<ProductEntity> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductEntity> findAll(Sort sort) {
        return productRepository.findAll(sort);
    }

    @Override
    public <S extends ProductEntity> Optional<S> findOne(Example<S> example) {
        return productRepository.findOne(example);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void delete(ProductEntity entity) {
        productRepository.delete(entity);
    }

    @Override
    public List<ProductEntity> findByProductNameContaining(String name) {
        return productRepository.findByProductNameContaining(name);
    }

    @Override
    public Page<ProductEntity> findByProductNameContaining(String name, Pageable pageable) {
        return productRepository.findByProductNameContaining(name, pageable);
    }
}


