package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.Owo_product;
import com.owo.OwoDokan.repository.admin_related.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Service
public class ProductService {

    @PersistenceContext
    EntityManager entityManager;


    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Owo_product saveProduct(Owo_product product)
    {
        return productRepository.save(product);
    }

    public Page<Owo_product> getAllProducts(int page) //This method is for getting all the products via
    {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findAll(pageable);
    }

    public Page<Owo_product> getProduct_by_category(int page, String category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByproduct_category(category, pageable);
    }

    public Page<Owo_product> getProduct_by_categoryDesc(int page, String category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByproduct_categoryDesc(category, pageable);
    }

    public Page<Owo_product> getProduct_by_categories(int page, List<String> categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByCategories(categories, pageable);
    }

    public Page<Owo_product> getProduct_by_categoriesDesc(int page, List<String> categories) {
        int pageSize = 20; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByCategoriesDesc(categories, pageable);
    }

    public Page<Owo_product> getProduct_by_sub_category(int page, String sub_category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByproduct_sub_category(sub_category, pageable);
    }

    public Page<Owo_product> getProduct_by_sub_categoryDesc(int page, String sub_category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        return productRepository.findByproduct_sub_categoryDesc(sub_category, pageable);
    }

    public Owo_product getProductById(long id) {
        return productRepository.findByProduct_Id(id);
    }

    public Owo_product updateProduct(Owo_product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String product_id) {
        int id = Integer.parseInt(product_id);
        productRepository.delete(id);
    }

    public List<Owo_product> searchProduct(int page, String[] categories, String name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product WHERE product_category IN(:categories) and MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) ORDER BY product_price ASC limit :offset , 30",
                Owo_product.class
        );

        List<String> abcd = Arrays.asList(categories);

        query.setParameter("categories", abcd);
        query.setParameter("name", name+"*");
        query.setParameter("offset", offset);

        Iterator iterator = query.getResultList().iterator();

        List<Owo_product> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add((Owo_product) iterator.next());
        }

        return result;
    }

    public List<Owo_product> searchProductDesc(int page, String[] categories, String name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product" +
                        " WHERE product_category IN(:categories) and MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) ORDER BY product_price DESC limit :offset , 30",
                Owo_product.class
        );

        List<String> abcd = Arrays.asList(categories);

        query.setParameter("offset", offset);
        query.setParameter("categories", abcd);
        query.setParameter("name", name+"*");

        Iterator iterator = query.getResultList().iterator();

        List<Owo_product> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add((Owo_product) iterator.next());
        }

        return result;
    }

    public Page<Owo_product> getProductByBrand(int page, String product_brand, String[] categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        List<String> product_categories = Arrays.asList(categories);
        return productRepository.findProductByBrand(pageable, product_brand, product_categories);
    }

    public Page<Owo_product> getProductByBrandDesc(int page, String product_brand, String[] categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        List<String> product_categories = Arrays.asList(categories);
        return productRepository.findProductByBrandDesc(pageable, product_brand, product_categories);
    }

    public List<Owo_product> searchProductAdmin(int page, String product_name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product" +
                        " Where MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) limit :offset , 30",
                Owo_product.class
        );

        query.setParameter("offset", offset);
        query.setParameter("name", product_name+"*");

        Iterator iterator = query.getResultList().iterator();

        List<Owo_product> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add((Owo_product) iterator.next());
        }

        return result;
    }
}
