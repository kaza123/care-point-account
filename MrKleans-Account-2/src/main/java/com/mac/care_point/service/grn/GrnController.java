/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.grn;

import com.mac.care_point.common.Constant;
import com.mac.care_point.service.grn.model.TGrn;
import com.mac.care_point.service.purchase_order.model.TPurchaseOrder;
import com.mac.care_point.service.purchase_order.model.TPurchaseOrderDetail;
import com.mac.care_point.zutil.SecurityUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kasun
 */
@CrossOrigin
@RestController
@RequestMapping("/api/care-point/transaction/grn")
public class GrnController {

    private final Integer branch = 2;
    private final String status_approved = "APPROVED";
    private final String status_pending = "PENDING";

    @Autowired
    private GrnService grnService;

    @RequestMapping(value = "/approve-purchasse-order", method = RequestMethod.GET)
    public List<TPurchaseOrder> getApprovedPurchaseOrder() {
        return grnService.getApprovedPurchaseOrder(branch, status_approved);
    }

    @RequestMapping(value = "/approve-purchase-order-item-list", method = RequestMethod.GET)
    public List<TPurchaseOrderDetail> getApprovedPurchaseOrderItemList() {
        return grnService.getApprovedPurchaseOrder();
    }

    @RequestMapping(value = "/pending-grn-list", method = RequestMethod.GET)
    public List<TGrn> getPendingGrnList() {
        return grnService.getPendingGrnList(branch, status_pending);
    }

    @RequestMapping(value = "/save-grn-recieve", method = RequestMethod.POST)
    public Integer saveGrnReceive(@RequestBody TGrn grn) {
        grn.setBranch(branch);
        if (null == grn.getDate()) {
            grn.setDate(new Date());
        }
        TGrn saveGrn = grnService.saveGrnRecieve(grn);
        return saveGrn.getNumber();
    }

    @RequestMapping(value = "/save-grn-approve", method = RequestMethod.POST)
    public Integer approveGrnReceive(@RequestBody TGrn grn) {
        grn.setStatus(status_approved);
        grn.setType("grn approve");
        grn.setBalanceAmount(grn.getGrandAmount());

        TGrn saveGrn = grnService.approveGrnRecieve(grn);
        return saveGrn.getNumber();
    }

    @RequestMapping(value = "/save-direct-grn", method = RequestMethod.POST)
    public Integer saveDirectGrn(@RequestBody TGrn grn) {
        grn.setStatus(status_approved);
        grn.setType("direct grn");
        grn.setBranch(branch);
        grn.setBalanceAmount(grn.getGrandAmount());

        return grnService.saveDirectGrn(grn).getNumber();
    }
    @RequestMapping(value = "/save-supplier-return", method = RequestMethod.POST)
    public Integer saveSupplierReturn(@RequestBody TGrn grn) {
        grn.setStatus(status_approved);
        grn.setType(Constant.SUPPLIER_RETURN);
        grn.setBranch(SecurityUtil.getCurrentUser().getBranch());
        grn.setBalanceAmount(grn.getGrandAmount());
        

        return grnService.saveSupplierReturn(grn).getNumber();
    }
    @RequestMapping(value = "/find-grn-by-number/{number}", method = RequestMethod.GET)
    public TGrn findGrnByNumber(@PathVariable("number") Integer number ) {
        return grnService.findGrnByNumber(number, SecurityUtil.getCurrentUser().getBranch(),Constant.SYSTEM_INTEGRATION_GRN,Constant.GRN);
    }
    @RequestMapping(value = "/find-grn-return-by-number/{number}", method = RequestMethod.GET)
    public TGrn findGrnReturnByNumber(@PathVariable("number") Integer number ) {
        return grnService.findGrnReturnByNumber(number, SecurityUtil.getCurrentUser().getBranch(),Constant.SUPPLIER_RETURN);
    }

}
