package hello.imagine.groupbuying.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.imagine.groupbuying.entity.Product;
import hello.imagine.groupbuying.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ObjectMapper objectMapper) {
        this.productRepository = productRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @PostConstruct
    public void loadProductsFromJson() {
        try {
            // JSON 파일의 경로를 설정합니다.
            File jsonFile = new File("/Users/jeong-yejin/Python_space/products5.json");

            // JSON 파일을 읽어 Product 리스트로 변환합니다.
            List<Product> products = objectMapper.readValue(jsonFile, new TypeReference<List<Product>>() {});

            // Product 리스트를 데이터베이스에 저장합니다.
            productRepository.saveAll(products);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
