/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.supplier_payment.model;

/**
 *
 * @author 'Kasun Chamara'
 */
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author 'Kasun Chamara'
 */
public class DataMain implements Serializable {

    private Integer indexNo;
    private String transactionDate;
    private Integer number;
    private String currentDate;
    private String time;
    private int branch;
    private int currentBranch;
    private int user;
    private BigDecimal debit;
    private BigDecimal credit;
    private String formName;
    private String refNumber;
    private String type;
    private Integer typeIndexNo;
    private Integer deleteRefNo;
    private String description;
    private Integer accAccount;
    private String chequeDate;
    private Boolean bankReconciliation;
    private Integer reconcileAccount;
    private Integer reconcileGroup;
    private boolean isMain;
    private String accType;
    private String accTypeSub;
    private BigDecimal billTotal;
    private String searchCode;
    private Integer financialYear;
    private Integer costDepartment;
    private Integer costCenter;

    public DataMain() {
    }

    public DataMain(Integer indexNo, String transactionDate, Integer number, String currentDate, String time, int branch, int currentBranch, int user, BigDecimal debit, BigDecimal credit, String formName, String refNumber, String type, Integer typeIndexNo, Integer deleteRefNo, String description, Integer accAccount, String chequeDate, Boolean bankReconciliation, Integer reconcileAccount, Integer reconcileGroup, boolean isMain, String accType, String accTypeSub, BigDecimal billTotal, String searchCode, Integer financialYear, Integer costDepartment, Integer costCenter) {
        this.indexNo = indexNo;
        this.transactionDate = transactionDate;
        this.number = number;
        this.currentDate = currentDate;
        this.time = time;
        this.branch = branch;
        this.currentBranch = currentBranch;
        this.user = user;
        this.debit = debit;
        this.credit = credit;
        this.formName = formName;
        this.refNumber = refNumber;
        this.type = type;
        this.typeIndexNo = typeIndexNo;
        this.deleteRefNo = deleteRefNo;
        this.description = description;
        this.accAccount = accAccount;
        this.chequeDate = chequeDate;
        this.bankReconciliation = bankReconciliation;
        this.reconcileAccount = reconcileAccount;
        this.reconcileGroup = reconcileGroup;
        this.isMain = isMain;
        this.accType = accType;
        this.accTypeSub = accTypeSub;
        this.billTotal = billTotal;
        this.searchCode = searchCode;
        this.financialYear = financialYear;
        this.costDepartment = costDepartment;
        this.costCenter = costCenter;
    }

    public Integer getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(Integer financialYear) {
        this.financialYear = financialYear;
    }

    public Integer getCostDepartment() {
        return costDepartment;
    }

    public void setCostDepartment(Integer costDepartment) {
        this.costDepartment = costDepartment;
    }

    public Integer getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(Integer costCenter) {
        this.costCenter = costCenter;
    }

    

    public String getAccTypeSub() {
        return accTypeSub;
    }

    public void setAccTypeSub(String accTypeSub) {
        this.accTypeSub = accTypeSub;
    }

    public String getSearchCode() {
        return searchCode;
    }

    public void setSearchCode(String searchCode) {
        this.searchCode = searchCode;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public boolean getIsMain() {
        return isMain;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }

    public Integer getReconcileAccount() {
        return reconcileAccount;
    }

    public void setReconcileAccount(Integer reconcileAccount) {
        this.reconcileAccount = reconcileAccount;
    }

    public Integer getReconcileGroup() {
        return reconcileGroup;
    }

    public void setReconcileGroup(Integer reconcileGroup) {
        this.reconcileGroup = reconcileGroup;
    }

    public Integer getIndexNo() {
        return indexNo;
    }

    public void setIndexNo(Integer indexNo) {
        this.indexNo = indexNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public int getCurrentBranch() {
        return currentBranch;
    }

    public void setCurrentBranch(int currentBranch) {
        this.currentBranch = currentBranch;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(String refNumber) {
        this.refNumber = refNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTypeIndexNo() {
        return typeIndexNo;
    }

    public void setTypeIndexNo(Integer typeIndexNo) {
        this.typeIndexNo = typeIndexNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAccAccount() {
        return accAccount;
    }

    public void setAccAccount(Integer accAccount) {
        this.accAccount = accAccount;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public Boolean getBankReconciliation() {
        return bankReconciliation;
    }

    public void setBankReconciliation(Boolean bankReconciliation) {
        this.bankReconciliation = bankReconciliation;
    }

    public Integer getDeleteRefNo() {
        return deleteRefNo;
    }

    public void setDeleteRefNo(Integer deleteRefNo) {
        this.deleteRefNo = deleteRefNo;
    }

    public BigDecimal getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(BigDecimal billTotal) {
        this.billTotal = billTotal;
    }

}
