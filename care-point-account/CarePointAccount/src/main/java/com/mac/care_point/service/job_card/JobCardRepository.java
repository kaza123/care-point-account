/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.job_card;

import com.mac.care_point.service.item_sale.model.JobCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author kasun
 */
public interface JobCardRepository extends JpaRepository<JobCard, Integer> {

    public List<JobCard> findByBay(Integer indexNo);

    public List<JobCard> findByBranchAndStatusAndInvoiceOrderByIndexNoDesc(Integer branch, String status, Boolean invoice);

    public List<JobCard> findByBranchAndStatusAndDefaultFinalCheckOrderByIndexNoDesc(Integer branch, String status, Boolean defaultInvoice);

    public List<JobCard> findByBranchAndStatusAndInvoiceAndDefaultFinalCheckOrderByIndexNoDesc(Integer branch, String status, Boolean invoice, Boolean defaultInvoice);

    @Query(value = "SELECT MAX(number)FROM t_job_card WHERE branch=:branch", nativeQuery = true)
    public Integer getMaximumNumberByBranch(@Param("branch") Integer branch);

    public List<JobCard> findByStatusNotIn(String FINISHED_STATUS);

    @Query(value = "select t_job_card.price_category from t_job_card where t_job_card.vehicle=:vehicle LIMIT 1", nativeQuery = true)
    public Integer getPriceCategory(@Param("vehicle") Integer vehicle);

    public List<JobCard> findByBranchAndStatusNotIn(Integer branch, String FINISHED_STATUS);

    public List<JobCard> findByVehicleAndStatus(Integer indexNo, String status);

    public List<JobCard> findJobCardByClient(Integer indexNo);

    @Query(value = "select * from t_job_card job where job.vehicle =:vehicleIndexNo order by job.date desc", nativeQuery = true)
    public List<JobCard> findJobHistory(@Param("vehicleIndexNo") Integer vehicleIndexNo);

    @Query(value = "select\n"
            + "  price_category_details.normal_price,\n"
            + "  price_category_details.register_price\n"
            + "from\n"
            + "  m_item item\n"
            + "inner join\n"
            + "  m_price_category_details price_category_details\n"
            + "on\n"
            + "  item.index_no = price_category_details.item\n"
            + "where\n"
            + "  item.index_no = :item \n"
            + "and\n"
            + "  price_category_details.price_category = :priceCategory", nativeQuery = true)
    public List<Object[]> getItemPriceDetails(@Param("item") Integer item, @Param("priceCategory") Integer priceCategory);
}
