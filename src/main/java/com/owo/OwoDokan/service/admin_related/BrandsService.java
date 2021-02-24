package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.Brands;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import com.owo.OwoDokan.exceptions.BrandListNotAvailable;
import com.owo.OwoDokan.exceptions.BrandsNotFoundException;
import com.owo.OwoDokan.exceptions.SubCategoryNotFound;
import com.owo.OwoDokan.repository.adminRelated.BrandsRepository;
import com.owo.OwoDokan.repository.adminRelated.category_repo.SubCategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandsService {

    private final BrandsRepository brandsRepository;
    private final SubCategoryRepo subCategoryRepo;

    public BrandsService(BrandsRepository brandsRepository, SubCategoryRepo subCategoryRepo) {
        this.brandsRepository = brandsRepository;
        this.subCategoryRepo = subCategoryRepo;
    }

    @Transactional
    public void createBrand(Brands brands) {

        Optional<SubCategoryEntity> subCategoryEntityOptional = subCategoryRepo.findById(brands.getSubCategoryEntity().
                getSub_category_id());

        if(subCategoryEntityOptional.isPresent())
        {
            SubCategoryEntity subCategoryEntity = subCategoryEntityOptional.get();
            subCategoryEntity.getBrandsList().add(brands);
        }
        else
        {
            log.error("Sub category not found");
            throw new BrandsNotFoundException(brands.getSubCategoryEntity().getSub_category_id());
        }
    }

    public ResponseEntity getBrandsViaCategory(int page, List<String>product_categories) {
        int pageSize = 10; //products per page
        org.springframework.data.domain.Pageable pageable = PageRequest.of(page, pageSize);
        try
        {
            return new ResponseEntity(brandsRepository.findBrandViaCategories(product_categories, pageable).getContent(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity(HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public List<Brands> getAllBrands(Long subCategoryId)
    {

        Optional<SubCategoryEntity> subCategoryEntityOptional = subCategoryRepo.findById(subCategoryId);

        if(subCategoryEntityOptional.isPresent())
        {
            List<Brands> brandsList = subCategoryEntityOptional.get().getBrandsList();

            if(brandsList.size()>0)
            {
                return brandsList;
            }
            else
            {
                throw new BrandListNotAvailable();
            }
        }
        else
        {
            log.error("Sub Category with id: "+subCategoryId+"not found");
            throw new SubCategoryNotFound(subCategoryId);
        }
    }

    @Transactional
    public String updateBrand(Long subCategoryId, Brands brands)
    {

        Optional<SubCategoryEntity> subCategoryEntityOptional = subCategoryRepo.findById(subCategoryId);

        if(subCategoryEntityOptional.isPresent())
        {
            SubCategoryEntity subCategoryEntity = subCategoryEntityOptional.get();

            List<Brands> brandsList = subCategoryEntity.getBrandsList();

            Brands brands2 = null;

            if(brandsList.size() > 0)
            {
                for(Brands brands1 : brandsList)
                {
                    if(brands1.getBrandId().equals(brands.getBrandId()))
                    {
                        brands1.setBrandName(brands.getBrandName());
                        brands1.setBrandImage(brands.getBrandImage());
                        brands2 = brands1;
                        break;
                    }
                }

                if(brands2 != null)
                {
                    brandsRepository.save(brands2);
                    return "Brands Updated Successfully";
                }
                else
                {
                    throw new BrandsNotFoundException(subCategoryId);
                }

            }
            else
            {
                throw new BrandsNotFoundException(subCategoryId);
            }

        }
        else
        {
            log.error("Sub Category with id: "+subCategoryId+"not found");
            throw new SubCategoryNotFound(subCategoryId);
        }

    }

    @Transactional
    public String deleteBrand(Long subCategoryId, Brands brands) {

        Optional<SubCategoryEntity> subCategoryEntityOptional = subCategoryRepo.findById(subCategoryId);

        if(subCategoryEntityOptional.isPresent())
        {
            SubCategoryEntity subCategoryEntity = subCategoryEntityOptional.get();

            List<Brands> brandsList = subCategoryEntity.getBrandsList();

            if(brandsList.size() > 0)
            {
                for(Brands brands1 : brandsList)
                {
                    if(brands1.getBrandId().equals(brands.getBrandId()))
                    {
                        try
                        {
                            subCategoryEntity.getBrandsList().remove(brands1);
                            return "Brand deleted successfully";
                        }catch (Exception e)
                        {
                            log.error(e.getMessage());
                            throw new RuntimeException(e);
                        }

                    }
                }
            }
            throw new BrandsNotFoundException(subCategoryId);
        }
        else
        {
            log.error("Sub Category with id: "+subCategoryId+"not found");
            throw new SubCategoryNotFound(subCategoryId);
        }
    }
}
