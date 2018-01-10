/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_item;

import com.mac.care_point.service.job_item.model.TJobItemCheck;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author kasun
 */
public interface TJobItemCheckRepository extends JpaRepository< TJobItemCheck, Integer> {

    public List<TJobItemCheck> findByJobItem(Integer jobItem);

    public List<TJobItemCheck> findByJobCardAndStatus(Integer jobCard, String status);

}
