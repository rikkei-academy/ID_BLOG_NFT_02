package ra.model.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.model.entity.Product;
import ra.model.repository.ProductRespository;
import ra.model.service.ProductService;

import java.util.List;
@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRespository productRespository;
    @Override
    public List<Product> findAll() {
        return productRespository.findAll();
    }

    @Override
    public Product findById(int productId) {
        return productRespository.findById(productId).get();
    }

    @Override
    public Product saveOfUpdate(Product product) {
        return productRespository.save(product);
    }

    @Override
    public void delete(int productId) {
        productRespository.deleteById(productId);

    }

    @Override
    public List<Product> searchByName(String productName) {
        return productRespository.findByProductNameContaining(productName);
    }

    @Override
    public List<Product> sortByProductPrice(String direction) {
        if(direction.equals("asc")){
            return productRespository.findAll(Sort.by("productPrice").ascending());
        }else {
            return productRespository.findAll(Sort.by("productPrice").descending());
        }
    }

    @Override
    public Page<Product> sortByNameAndPagination(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Product> pagging(Pageable pageable) {
        return productRespository.findAll(pageable);
    }
}
