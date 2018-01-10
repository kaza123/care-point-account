/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.grn;

import com.mac.care_point.service.grn.model.MStore;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface StoreRepository extends JpaRepository<MStore, Integer>{

    public List<MStore> findByBranchAndType(int branch, String MAIN_STOCK);

    public MStore findFirst1ByOrderByNumberDesc();
    
    
}
