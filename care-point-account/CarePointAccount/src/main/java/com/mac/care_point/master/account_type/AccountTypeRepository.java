/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.account_type;

import com.mac.care_point.master.account_type.model.MAccAccountType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface AccountTypeRepository extends JpaRepository<MAccAccountType, Integer>{
    
}
