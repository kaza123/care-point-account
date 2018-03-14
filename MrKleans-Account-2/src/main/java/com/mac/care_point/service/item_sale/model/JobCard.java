/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.service.item_sale.model;

import com.mac.care_point.service.job_item.model.TJobItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kasun
 */
@Entity
@Table(name = "t_job_card")
@XmlRootElement
public class JobCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "index_no")
    private Integer indexNo;

    @Column(name = "number")
    private Integer number;

    @Column(name = "branch")
    private Integer branch;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "transaction")
    private Integer transaction;

    @Column(name = "price_category")
    private Integer priceCategory;

    @Column(name = "in_time")
    private String inTime;

    @Column(name = "out_time")
    private String outTime;

    @Column(name = "in_mileage")
    private Integer inMileage;

    @Column(name = "next_mileage")
    private Integer nextMileage;

    @Size(max = 25)
    @Column(name = "status")
    private String status;

    @Column(name = "bay")
    private Integer bay;
   
    @Column(name = "form_name")
    private String formName;

    @Column(name = "client")
    private Integer client;

    @Column(name = "vehicle")
    private Integer vehicle;

    @Column(name = "service_chagers")
    private Boolean serviceChagers;

    @Column(name = "vehicle_images")
    private Boolean vehicleImages;

    @Column(name = "final_check")
    private Boolean finalCheck;

    @Column(name = "attenctions")
    private Boolean attenctions;

    @Column(name = "invoice")
    private Boolean invoice;

    @Column(name = "default_final_check")
    private Boolean defaultFinalCheck;

    @Column(name = "rate")
    private Integer rate;
    
    @Column(name = "rate_reason")
    private String rateReason;
    
    @Column(name = "carepet_original")
    private Integer carepetOriginal;
    
    @Column(name = "carepet_other")
    private Integer carepetOther;
    
    @Column(name = "carepet_3m")
    private Integer carepet3M;
    
    @Column(name = "driver_name")
    private String driverName;

    @Column(name = "driver_mobile")
    private String driverMobile;
    
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobCard", fetch = FetchType.EAGER)
    private List<TJobItem> tJobItemList;

    public JobCard() {
    }

    public JobCard(Integer indexNo, Integer number, Integer branch, Date date, Integer transaction, Integer priceCategory, String inTime, String outTime, Integer inMileage, Integer nextMileage, String status, Integer bay, String formName, Integer client, Integer vehicle, Boolean serviceChagers, Boolean vehicleImages, Boolean finalCheck, Boolean attenctions, Boolean invoice, Boolean defaultFinalCheck, Integer rate, String rateReason, Integer carepetOriginal, Integer carepetOther, Integer carepet3M, String driverName, String driverMobile, List<TJobItem> tJobItemList) {
        this.indexNo = indexNo;
        this.number = number;
        this.branch = branch;
        this.date = date;
        this.transaction = transaction;
        this.priceCategory = priceCategory;
        this.inTime = inTime;
        this.outTime = outTime;
        this.inMileage = inMileage;
        this.nextMileage = nextMileage;
        this.status = status;
        this.bay = bay;
        this.formName = formName;
        this.client = client;
        this.vehicle = vehicle;
        this.serviceChagers = serviceChagers;
        this.vehicleImages = vehicleImages;
        this.finalCheck = finalCheck;
        this.attenctions = attenctions;
        this.invoice = invoice;
        this.defaultFinalCheck = defaultFinalCheck;
        this.rate = rate;
        this.rateReason = rateReason;
        this.carepetOriginal = carepetOriginal;
        this.carepetOther = carepetOther;
        this.carepet3M = carepet3M;
        this.driverName = driverName;
        this.driverMobile = driverMobile;
        this.tJobItemList = tJobItemList;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public JobCard(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getBranch() {
        return branch;
    }

    public void setBranch(Integer branch) {
        this.branch = branch;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTransaction() {
        return transaction;
    }

    public void setTransaction(Integer transaction) {
        this.transaction = transaction;
    }

    public Integer getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(Integer priceCategory) {
        this.priceCategory = priceCategory;
    }

    public Integer getInMileage() {
        return inMileage;
    }

    public void setInMileage(Integer inMileage) {
        this.inMileage = inMileage;
    }

    public Integer getNextMileage() {
        return nextMileage;
    }

    public void setNextMileage(Integer nextMileage) {
        this.nextMileage = nextMileage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBay() {
        return bay;
    }

    public void setBay(Integer bay) {
        this.bay = bay;
    }

    public List<TJobItem> gettJobItemList() {
        return tJobItemList;
    }

    public void settJobItemList(List<TJobItem> tJobItemList) {
        this.tJobItemList = tJobItemList;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getVehicle() {
        return vehicle;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getServiceChagers() {
        return serviceChagers;
    }

    public void setServiceChagers(Boolean serviceChagers) {
        this.serviceChagers = serviceChagers;
    }

    public Boolean getFinalCheck() {
        return finalCheck;
    }

    public void setFinalCheck(Boolean finalCheck) {
        this.finalCheck = finalCheck;
    }

    public Boolean getAttenctions() {
        return attenctions;
    }

    public void setAttenctions(Boolean attenctions) {
        this.attenctions = attenctions;
    }

    public Boolean getInvoice() {
        return invoice;
    }

    public void setInvoice(Boolean invoice) {
        this.invoice = invoice;
    }

    public Boolean getVehicleImages() {
        return vehicleImages;
    }

    public void setVehicleImages(Boolean vehicleImages) {
        this.vehicleImages = vehicleImages;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public Boolean getDefaultFinalCheck() {
        return defaultFinalCheck;
    }

    public void setDefaultFinalCheck(Boolean defaultFinalCheck) {
        this.defaultFinalCheck = defaultFinalCheck;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getRateReason() {
        return rateReason;
    }

    public void setRateReason(String rateReason) {
        this.rateReason = rateReason;
    }

    public Integer getCarepetOriginal() {
        return carepetOriginal;
    }

    public void setCarepetOriginal(Integer carepetOriginal) {
        this.carepetOriginal = carepetOriginal;
    }

    public Integer getCarepetOther() {
        return carepetOther;
    }

    public void setCarepetOther(Integer carepetOther) {
        this.carepetOther = carepetOther;
    } 

    public Integer getCarepet3M() {
        return carepet3M;
    }

    public void setCarepet3M(Integer carepet3M) {
        this.carepet3M = carepet3M;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

}
