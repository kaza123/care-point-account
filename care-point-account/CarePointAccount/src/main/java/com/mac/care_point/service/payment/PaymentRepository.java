/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.payment;

import com.mac.care_point.service.item_sale.model.TPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author kasun
 */
public interface PaymentRepository extends JpaRepository<TPayment, Integer> {

    @Query(value = "SELECT MAX(number)FROM t_payment", nativeQuery = true)
    public Integer getMaximumNumberByBranch();
}
