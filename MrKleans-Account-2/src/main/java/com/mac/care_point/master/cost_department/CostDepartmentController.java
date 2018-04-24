/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.master.cost_department;

import com.mac.care_point.master.cost_center.model.MCostCenter;
import com.mac.care_point.master.cost_department.model.MCostDepartment;
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
@RestController
@CrossOrigin
@RequestMapping("/api/care-point/master/cost-department")
public class CostDepartmentController {

    @Autowired
    private CostDepartmentService costDepartmentService;

    @RequestMapping(method = RequestMethod.GET)
    public List<MCostDepartment> findAll() {
        return costDepartmentService.findAll();
    }

    @RequestMapping(value = "/get-active-list", method = RequestMethod.GET)
    public List<MCostDepartment> getActiveList() {
        return costDepartmentService.getActiveList();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MCostDepartment insertDetail(@RequestBody MCostDepartment mCostDepartment) {
        return costDepartmentService.save(mCostDepartment);
    }

    @RequestMapping(value = "/delete/{indexNo}", method = RequestMethod.DELETE)
    public Integer deleteDetail(@PathVariable Integer indexNo) {
        costDepartmentService.delete(indexNo);
        return indexNo;
    }
}
