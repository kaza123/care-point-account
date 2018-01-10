/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.brand;

import com.mac.care_point.master.brand.model.MBrand;
import com.mac.care_point.system.exception.DuplicateEntityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author kasun
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public List<MBrand> findAllBrand() {
        return brandRepository.findAll();
    }

    private MBrand findByName(String name) {
        List<MBrand> brandList = brandRepository.findByName(name);
        if (brandList.isEmpty()) {
            return null;
        }
        return brandList.get(0);
    }

    public MBrand saveBrand(MBrand mBrand) {
        MBrand findByName = findByName(mBrand.getName());
        if (findByName == null) {
            return brandRepository.save(mBrand);
        }else{
            if (findByName.getIndexNo().equals(mBrand.getIndexNo())) {
                return mBrand;
            }
            throw new DuplicateEntityException("Duplicate name" );
        }
    }

    public void deleteBrand(Integer indexNo) {
        try {    
            brandRepository.delete(indexNo);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete this brand because there are details in other transaction");
        }
    }

}
