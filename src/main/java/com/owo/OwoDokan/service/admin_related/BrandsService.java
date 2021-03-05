package com.owo.OwoDokan.service.admin_related;

import com.owo.OwoDokan.entity.admin_related.Brands;
import com.owo.OwoDokan.entity.admin_related.category.CategoryEntity;
import com.owo.OwoDokan.entity.admin_related.category.SubCategoryEntity;
import com.owo.OwoDokan.exceptions.BrandListNotAvailable;
import com.owo.OwoDokan.exceptions.BrandsNotFoundException;
import com.owo.OwoDokan.exceptions.CategoryNotFoundException;
import com.owo.OwoDokan.exceptions.SubCategoryNotFound;
import com.owo.OwoDokan.repository.adminRelated.BrandsRepository;
import com.owo.OwoDokan.repository.adminRelated.category_repo.CategoryRepo;
import com.owo.OwoDokan.repository.adminRelated.category_repo.SubCategoryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BrandsService {

    private final BrandsRepository brandsRepository;
    private final SubCategoryRepo subCategoryRepo;
    private final CategoryRepo categoryRepo;

    public BrandsService(BrandsRepository brandsRepository, SubCategoryRepo subCategoryRepo, CategoryRepo categoryRepo) {
        this.brandsRepository = brandsRepository;
        this.subCategoryRepo = subCategoryRepo;
        this.categoryRepo = categoryRepo;
    }

    @Transactional
    public void createBrand(Brands brands)
    {

        Optional<SubCategoryEntity> subCategoryEntityOptional = subCategoryRepo.findById(brands.getSubCategoryEntity().getSub_category_id());

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

    public List<Brands> getBrandsViaCategory(int number, List<Long> categoryIds)
    {

        Optional<CategoryEntity> categoryEntityOptional = categoryRepo.findById(categoryIds.get(number-1));

        List<Brands> brandsList = new ArrayList<>();

        if(categoryEntityOptional.isPresent())
        {
            List<SubCategoryEntity> subCategoryEntityList = categoryEntityOptional.get().getSubCategoryEntities();

            for(SubCategoryEntity subCategoryEntity : subCategoryEntityList)
            {
                brandsList.addAll(subCategoryEntity.getBrandsList());
            }

            return brandsList;
        }
        else
        {
            throw new CategoryNotFoundException(categoryIds.get(number));
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
    public String deleteBrand(Long subCategoryId, Long brands)
    {

        Optional<SubCategoryEntity> subCategoryEntityOptional = subCategoryRepo.findById(subCategoryId);

        if(subCategoryEntityOptional.isPresent())
        {
            SubCategoryEntity subCategoryEntity = subCategoryEntityOptional.get();

            List<Brands> brandsList = subCategoryEntity.getBrandsList();

            if(brandsList.size() > 0)
            {
                for(Brands brands1 : brandsList)
                {
                    if(brands1.getBrandId().equals(brands))
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
