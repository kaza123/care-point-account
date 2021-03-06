/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mac.care_point.transaction.fund_transfer;

import com.mac.care_point.account_setting.account_setting.AccountSettingRepository;
import com.mac.care_point.account_setting.account_setting.model.MAccSetting;
import com.mac.care_point.common.Constant;
import com.mac.care_point.master.account.AccAccountRepository;
import com.mac.care_point.master.account.model.MAccAccount;
import com.mac.care_point.master.branch.BranchRepository;
import com.mac.care_point.master.branch.model.MBranch;
import com.mac.care_point.transaction.account_ledger.JournalRepository;
import com.mac.care_point.transaction.account_ledger.model.TAccLedger;
import com.mac.care_point.transaction.fund_transfer.model.FundTransferMix;
import com.mac.care_point.zutil.SecurityUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 'Kasun Chamara'
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FundTransferService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private AccountSettingRepository accountSettingRepository;

    @Autowired
    private AccAccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    TAccLedger saveFundTransfer(FundTransferMix fundTransferMix) {
        int number = journalRepository.getNumber(SecurityUtil.getCurrentUser().getBranch(), Constant.FUND_TRANSFER);
        int deleteNumber = journalRepository.getDeleteNumber();
        MAccSetting unrealizedIssued = accountSettingRepository.findByName(Constant.UNREALIZED_ISSUED);
        MAccSetting unrealizedReceived = accountSettingRepository.findByName(Constant.UNREALIZED_RECEIVED);
        String searchCode = getSearchCode(Constant.CODE_FUND_TRANSFER, SecurityUtil.getCurrentUser().getBranch(), number);
        boolean isCheque=false;
        isCheque = fundTransferMix.getData().getIsCheque();
        fundTransferMix.getData().setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
        fundTransferMix.getData().setNumber(number);
        fundTransferMix.getData().setDeleteRefNo(deleteNumber);
        fundTransferMix.getData().setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        fundTransferMix.getData().setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
        fundTransferMix.getData().setUser(SecurityUtil.getCurrentUser().getBranch());
        fundTransferMix.getData().setIsMain(true);
        fundTransferMix.getData().setIsEdit(0);
        fundTransferMix.getData().setSearchCode(searchCode);
        fundTransferMix.getData().setType(Constant.FUND_TRANSFER);
        TAccLedger save = journalRepository.save(fundTransferMix.getData());
        save.setIsEdit(0);

        MAccAccount account = accountRepository.findOne(fundTransferMix.getData().getAccAccount());
        if ("BANK".equals(account.getAccType())) {
            save.setReconcileAccount(fundTransferMix.getData().getAccAccount());
            save.setAccAccount(unrealizedIssued.getAccAccount());
            save.setReconcileGroup(save.getIndexNo());
            save.setBankReconciliation(true);
            save=journalRepository.save(save);
        }
        if ("CHEQUE".equals(account.getAccType())) {
            save.setReconcileAccount(fundTransferMix.getData().getAccAccount());
            save.setReconcileGroup(save.getIndexNo());
            save.setBankReconciliation(true);
            journalRepository.save(save);
        }
        int size=0;
        for (TAccLedger tAccLedger : fundTransferMix.getDataList()) {
            tAccLedger.setNumber(number);
            tAccLedger.setDeleteRefNo(deleteNumber);
            tAccLedger.setCurrentBranch(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setCurrentDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            tAccLedger.setTime(new SimpleDateFormat("kk:mm:ss").format(new Date()));
            tAccLedger.setUser(SecurityUtil.getCurrentUser().getBranch());
            tAccLedger.setIsMain(false);
            tAccLedger.setIsEdit(0);
            tAccLedger.setSearchCode(searchCode);
            tAccLedger.setIsCheque(false);
            tAccLedger.setType(Constant.FUND_TRANSFER);
            if (isCheque) {
                tAccLedger.setIsCheque(true);
            }
            tAccLedger.setTransactionDate(fundTransferMix.getData().getTransactionDate());
            TAccLedger save1 = journalRepository.save(tAccLedger);
            save1.setIsEdit(0);
            
            if ("BANK".equals(account.getAccType())) {
                save1.setReconcileAccount(tAccLedger.getAccAccount());
                save1.setAccAccount(unrealizedReceived.getAccAccount());
                save1.setReconcileGroup(save1.getIndexNo());
                save1.setBankReconciliation(true);
                journalRepository.save(save1);
            }
            if ("CHEQUE".equals(account.getAccType())) {
                save1.setRefNumber(fundTransferMix.getData().getRefNumber());
                journalRepository.save(save1);
            }
            size++;
        }
        
        if (fundTransferMix.getDataList().size()==size) {
            return save;
            
        }
        throw new RuntimeException("Fund Transfer Save Fail !");
    }

    private String getSearchCode(String code, Integer branch, int number) {
        MBranch branchModel = branchRepository.findOne(branch);
        String branchCode = branchModel.getBranchCode();
        return code + "/" + branchCode + "/" + number;
    }

    public List<TAccLedger> findFundTransferByNumberAndBranch(Integer number, Integer branch) {
        return journalRepository.findByNumberAndBranchAndType(number, branch, Constant.FUND_TRANSFER);
    }

}
