package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.OwoProduct;
import com.owo.OwoDokan.repository.adminRelated.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public OwoProduct saveProduct(OwoProduct product)
    {
        return productRepository.save(product);
    }


    /*
    public ResponseEntity getAllProducts(int page) //This method is for getting all the products via
    {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findAll(pageable).getContent();
            return maniPlateResponse(owo_productList);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProduct_by_category(int page, String category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByproduct_category(category, pageable).getContent();

            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProduct_by_categoryDesc(int page, String category) {

        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByproduct_categoryDesc(category, pageable).getContent();
            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    private ResponseEntity maniPlateResponse(List<OwoProduct> owo_productList) {
        for(OwoProduct owo_product : owo_productList)
        {
            owo_product.setProductDescription(null);
            owo_product.setProductCreationDate(null);
            owo_product.setProductCreationTime(null);
            owo_product.setProductCategoryId(null);
            owo_product.setProductSubCategoryId(null);
            owo_product.setBrands(null);
        }

        return new ResponseEntity(owo_productList, HttpStatus.OK);
    }

    public ResponseEntity getProduct_by_categories(int page, List<String> categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByCategories(categories, pageable).getContent();

            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProduct_by_categoriesDesc(int page, List<String> categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByCategoriesDesc(categories, pageable).getContent();

            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProduct_by_sub_category(int page, String sub_category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByproduct_sub_category(sub_category, pageable).getContent();

            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProduct_by_sub_categoryDesc(int page, String sub_category) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByproduct_sub_categoryDesc(sub_category, pageable).getContent();

            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProductById(long id) {
        try
        {
            return new ResponseEntity(productRepository.findByProduct_Id(id), HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public OwoProduct updateProduct(OwoProduct product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String product_id) {
        int id = Integer.parseInt(product_id);
        productRepository.delete(id);
    }

    public ResponseEntity searchProduct(int page, String[] categories, String name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product WHERE product_category IN(:categories) and MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) ORDER BY product_price ASC limit :offset , 30",
                OwoProduct.class
        );

        List<String> abcd = Arrays.asList(categories);

        query.setParameter("categories", abcd);
        query.setParameter("name", name+"*");
        query.setParameter("offset", offset);

        try
        {
            Iterator iterator = query.getResultList().iterator();
            List<OwoProduct> result = new ArrayList<>();

            while (iterator.hasNext()) {
                result.add((OwoProduct) iterator.next());
            }

            return maniPlateResponse(result);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity searchProductDesc(int page, String[] categories, String name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product" +
                        " WHERE product_category IN(:categories) and MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) ORDER BY product_price DESC limit :offset , 30",
                OwoProduct.class
        );

        List<String> abcd = Arrays.asList(categories);

        query.setParameter("offset", offset);
        query.setParameter("categories", abcd);
        query.setParameter("name", name+"*");

        try
        {
            Iterator iterator = query.getResultList().iterator();

            List<OwoProduct> result = new ArrayList<>();

            while (iterator.hasNext()) {
                result.add((OwoProduct) iterator.next());
            }

            return maniPlateResponse(result);

        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProductByBrand(int page, String product_brand, String[] categories) {

        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        List<String> product_categories = Arrays.asList(categories);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findProductByBrand(pageable, product_brand, product_categories).getContent();
            return maniPlateResponse(owo_productList);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity getProductByBrandDesc(int page, String product_brand, String[] categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        List<String> product_categories = Arrays.asList(categories);

        try
        {
            List<OwoProduct> owo_productList =  productRepository.findProductByBrandDesc(pageable, product_brand, product_categories).getContent();
            return maniPlateResponse(owo_productList);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity searchProductAdmin(int page, String product_name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product" +
                        " Where MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) limit :offset , 30",
                OwoProduct.class
        );

        query.setParameter("offset", offset);
        query.setParameter("name", product_name+"*");

        try
        {
            Iterator iterator = query.getResultList().iterator();

            List<OwoProduct> result = new ArrayList<>();

            while (iterator.hasNext()) {
                result.add((OwoProduct) iterator.next());
            }

            return maniPlateResponse(result);
        }catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

     */
}
