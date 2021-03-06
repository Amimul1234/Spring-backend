package com.shopKpr.service.admin_related;

import com.shopKpr.entity.admin_related.OwoProduct;
import com.shopKpr.exceptions.ProductNotFoundException;
import com.shopKpr.repository.adminRelated.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Service
@Slf4j
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

    @Transactional
    public OwoProduct updateProduct(OwoProduct product) {

        Optional<OwoProduct> optionalOwoProduct = productRepository.findById(product.getProductId());

        if(optionalOwoProduct.isPresent())
        {
            OwoProduct owoProduct = optionalOwoProduct.get();

            owoProduct.setProductName(product.getProductName());
            owoProduct.setProductPrice(product.getProductPrice());
            owoProduct.setProductDiscount(product.getProductDiscount());
            owoProduct.setProductQuantity(product.getProductQuantity());
            owoProduct.setProductDescription(product.getProductDescription());
            owoProduct.setProductCreationDate(product.getProductCreationDate());
            owoProduct.setProductCreationTime(product.getProductCreationTime());
            owoProduct.setProductImage(product.getProductImage());

            try
            {
                return productRepository.save(owoProduct);
            }catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }
        else
        {
            throw new ProductNotFoundException(product.getProductId());
        }
    }

    public List<OwoProduct> getAllProducts(int page) //This method is for getting all the products via
    {
        int pageSize = 10; //products per page

        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owoProductList = productRepository.findAll(pageable).getContent();

            for(OwoProduct owoProduct : owoProductList)
            {
                responseManipulator(owoProduct);
            }

            return owoProductList;
        }
        catch (Exception e)
        {
            log.error(e.getMessage());
            throw  new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteProduct(Long productId)
    {
        try
        {
            productRepository.deleteById(productId);
        }catch (Exception e)
        {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public OwoProduct getAProduct(Long productId)
    {

        Optional<OwoProduct> owoProductOptional = productRepository.findById(productId);

        if(owoProductOptional.isPresent())
        {
            OwoProduct owoProduct = owoProductOptional.get();

            owoProduct.setProductDescription(owoProduct.getProductDescription());

            return owoProduct;
        }
        else
        {
            throw new ProductNotFoundException(productId);
        }
    }

    public List<OwoProduct> searchProductAdmin(int page, String product_name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product" +
                        " Where MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) limit :offset , 30", OwoProduct.class);

        query.setParameter("offset", offset);
        query.setParameter("name", product_name+"*");

        try
        {
            Iterator iterator = query.getResultList().iterator();

            List<OwoProduct> result = new ArrayList<>();

            while (iterator.hasNext()) {
                OwoProduct owoProduct = (OwoProduct) iterator.next();
                responseManipulator(owoProduct);
                result.add(owoProduct);
            }

            return result;

        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void responseManipulator(OwoProduct owoProduct)
    {
        owoProduct.setProductDescription(null);
        owoProduct.setProductCategoryId(null);
        owoProduct.setProductSubCategoryId(null);
        owoProduct.setProductCreationTime(null);
        owoProduct.setProductCreationDate(null);
    }

    public List<OwoProduct> getProduct_by_categories(int page, List<Long> categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);

        try
        {
            List<OwoProduct> owo_productList = productRepository.findByCategories(categories, pageable).getContent();

            for(OwoProduct owoProduct : owo_productList)
            {
                responseManipulator(owoProduct);
            }

            return owo_productList;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public OwoProduct getProductById(Long productId) {
        Optional<OwoProduct> owoProductOptional = productRepository.findById(productId);

        if(owoProductOptional.isPresent())
        {
            return owoProductOptional.get();
        }
        else
        {
            log.error("Can not find product with id: "+productId);
            throw new ProductNotFoundException(productId);
        }
    }

    public String getABrandViaProduct(Long productId) {
        Optional<OwoProduct> owoProductOptional = productRepository.findById(productId);

        if(owoProductOptional.isPresent())
        {
            return owoProductOptional.get().getBrands().getBrandName();
        }
        else
        {
            throw new ProductNotFoundException(productId);
        }
    }

    /*
    public ResponseEntity searchProduct(int page, String[] categories, String name) {

        int offset = 30 * page; //Here page starts from 0

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM owo_product WHERE pro IN(:categories) and MATCH(product_name) AGAINST (:name IN BOOLEAN MODE) ORDER BY product_price ASC limit :offset , 30",
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

     */

    /*
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

    public OwoProduct updateProduct(OwoProduct product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(String product_id) {
        int id = Integer.parseInt(product_id);
        productRepository.delete(id);
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

     */
}
