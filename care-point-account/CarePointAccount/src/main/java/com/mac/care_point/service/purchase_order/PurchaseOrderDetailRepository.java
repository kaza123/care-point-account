/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.purchase_order;

import com.mac.care_point.service.purchase_order.model.TPurchaseOrderDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface PurchaseOrderDetailRepository extends JpaRepository<TPurchaseOrderDetail, Integer>{

//    public List<TPurchaseOrderDetail> findByPurchaseOrderItemAndGrn(Integer purchaseOrderItem, Integer indexNo);
    
}
